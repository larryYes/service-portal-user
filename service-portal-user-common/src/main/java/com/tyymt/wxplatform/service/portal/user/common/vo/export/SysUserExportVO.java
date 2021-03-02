package com.tyymt.wxplatform.service.portal.user.common.vo.export;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.io.Serializable;

/**
 * @author huangfei
 * @create 2020-10-14 16:15
 */
@Data
public class SysUserExportVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "用户名")
    @ColumnWidth(18)
    private String userName;

    @ExcelProperty(value = "姓名")
    @ColumnWidth(18)
    private String nickName;

    @ExcelProperty(value = "所属部门")
    @ColumnWidth(28)
    private String deptName;

    @ExcelProperty(value = "所属子系统")
    @ColumnWidth(32)
    private String systemAndAdmin;

    @ExcelProperty(value = "联系电话")
    @ColumnWidth(12)
    private String phone;

    @ExcelProperty(value = "邮箱")
    @ColumnWidth(28)
    private String email;

    @ExcelProperty(value = "人员状态")
    @ColumnWidth(12)
    private String enableFlag;

}
