package com.tyymt.wxplatform.service.portal.user.facade;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyymt.wxplatform.service.portal.user.common.error.Error;
import com.tyymt.wxplatform.service.portal.user.common.model.SysRole;
import com.tyymt.wxplatform.service.portal.user.common.model.SysSystem;
import com.tyymt.wxplatform.service.portal.user.common.service.SysRoleService;
import com.tyymt.wxplatform.service.portal.user.common.service.SysSystemService;
import com.tyymt.wxplatform.service.portal.user.sdk.http.req.reader.PageReqDto;
import com.tyymt.wxplatform.service.portal.user.sdk.http.resp.system.SystemRoleRespDto;
import lombok.RequiredArgsConstructor;
import net.bestjoy.cloud.core.error.BusinessAssert;
import net.bestjoy.cloud.core.error.BusinessException;
import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;
import net.bestjoy.cloud.oss.core.BestOSS;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @author huangfei
 * @create 2020-10-14 15:58
 */
@Service
@RequiredArgsConstructor
public class SysSystemFacade {

    private final SysSystemService systemService;

    private final SysRoleService sysRoleService;

    private final BestOSS bestOSS;

    @Value("${system.bucket.name}")
    private String bucketName;

    public Boolean save(SysSystem sysSystem) {
        SysSystem entity = systemService.getOne(Wrappers.<SysSystem>query().lambda().eq(SysSystem::getSystemName, sysSystem.getSystemName()));
        BusinessAssert.isNull(entity, ErrorCodeAndMessage.create(9999981, "该子系统已存在"));
        sysSystem.setDelFlag("0");
        return systemService.save(sysSystem);
    }

    public Boolean updateById(SysSystem sysSystem) {
        SysSystem system = systemService.getById(sysSystem.getId());
        BusinessAssert.notNull(system, ErrorCodeAndMessage.create(9999981, "记录已删除，请刷新！"));
        return systemService.updateById(sysSystem);
    }

    /**
     * 根据id删除系统
     *
     * @param id
     * @return
     */
    public Boolean removeById(Integer id) {
        return systemService.removeById(id);
    }

    /**
     * 系统分页查询
     *
     * @param req
     * @param sysSystem
     * @return
     */
    public IPage page(PageReqDto req, SysSystem sysSystem) {
        Page page = new Page<>(req.getCurrent(), req.getSize());

        return systemService.page(page, Wrappers.query(sysSystem));
    }

    /**
     * 上传系统列表
     *
     * @param file
     * @return
     */
    public Object uploadImage(MultipartFile file) {
        InputStream inputStream = null;
        try {
            //拼接文件存储的键 eg:images/202010/xxx.jpg
            String prefix = "images/";
            Calendar calendar = Calendar.getInstance();
            String year = String.valueOf(calendar.get(Calendar.YEAR));
            String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
            String fileName = file.getOriginalFilename();
            //中文进行url编码
            if (StringUtils.isNotBlank(fileName)) {
                fileName = URLDecoder.decode(fileName, "UTF-8");
            }
            String eName = fileName.substring(fileName.lastIndexOf("."));
            String newFileName = UUID.randomUUID() + eName;
            String key = prefix.concat(year + "/").concat(month + "/").concat(newFileName);
            inputStream = file.getInputStream();

            //提前告知流大小
            int length = inputStream.available();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(length);
            objectMetadata.setContentType("image/jpg");
            //以流的形式将图片上传至对象存储
            objectMetadata.setContentLength(inputStream.available());
            bestOSS.putObject(bucketName, key, inputStream, objectMetadata);

            String url = "https://%s.cos.ap-shanghai.myqcloud.com/%s";
            url = String.format(url, "ymt-manager-system-1302439057", key);
            Map mapKey = new HashMap(2);
            mapKey.put("url", url);
            return mapKey;
        } catch (UnsupportedEncodingException e) {
            throw new BusinessException(Error.Biz.UNSUPPORTED_ENCODING);
        } catch (Exception e) {
            throw new BusinessException(Error.Biz.FILE_TO_STREAM_ERROR);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取系统列表
     *
     * @return
     */
    public List<SystemRoleRespDto> getSystemList(Integer id) {

        List<SystemRoleRespDto> systemAndRole = systemService.getSystemAndRole(id);

        // 查询该用户被分配的角色权限，只保留角色数据即可
        if (null != id) {
            for (int i = systemAndRole.size() - 1; i > -1; i--) {
                if (systemAndRole.get(i).getParentId() == 0) {
                    systemAndRole.remove(i);
                }else{
                    // 将该角色的ID使用”系统ID连字符和角色ID“代替
                    systemAndRole.get(i).setId(systemAndRole.get(i).getParentId()+"-"+systemAndRole.get(i).getId());
                }
            }
            return systemAndRole;
        }
        // 查询所有可被分配的角色权限并转成树形结构
        List<SystemRoleRespDto> respDtos = new ArrayList<>();
        for (SystemRoleRespDto systemRoleDto : systemAndRole
        ) {
            SystemRoleRespDto respDto = new SystemRoleRespDto();
            if (systemRoleDto.getParentId().equals(0)) {
                respDto.setId(systemRoleDto.getId());
                respDto.setParentId(0);
                respDto.setName(systemRoleDto.getName());
                respDto.setChildren(getChildren(systemAndRole, systemRoleDto.getId()));
                respDtos.add(respDto);
            }
        }
        return respDtos;
    }

    /**
     * 设置children数据
     *
     * @param subDto
     * @param id
     * @return
     */
    private List<SystemRoleRespDto> getChildren(List<SystemRoleRespDto> subDto, String id) {
        List<SystemRoleRespDto> subDtos = new ArrayList<>();
        for (SystemRoleRespDto sub :
                subDto) {
            if (sub.getParentId().toString().equals(id)) {
                SystemRoleRespDto subEntity = new SystemRoleRespDto();
                subEntity.setId((sub.getParentId() + "-" + sub.getId()));
                subEntity.setParentId(sub.getParentId());
                subEntity.setName(sub.getName());
                subDtos.add(subEntity);
            }
        }
        return subDtos;
    }

}
