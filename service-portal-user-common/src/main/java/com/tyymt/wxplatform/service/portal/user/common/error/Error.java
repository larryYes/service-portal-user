package com.tyymt.wxplatform.service.portal.user.common.error;

import net.bestjoy.cloud.core.error.ErrorCodeAndMessage;

/**
 * 异常常量
 *
 * @author 肖晟鹏
 * @email xiaocpa@digitalchina.com
 * @date 2020-09-22
 */
public interface Error {

    interface Biz{
        ErrorCodeAndMessage RECORDS_NOT_FOUND = ErrorCodeAndMessage.create(000004,"记录不存在");
        ErrorCodeAndMessage UNSUPPORTED_ENCODING = ErrorCodeAndMessage.create(000005,"url编码异常");
        ErrorCodeAndMessage COS_UPLOAD_ERROR = ErrorCodeAndMessage.create(000006,"对象存储文件上传异常");
        ErrorCodeAndMessage FILE_TO_STREAM_ERROR = ErrorCodeAndMessage.create(000007,"前端上传文件获取流异常");
    }

}
