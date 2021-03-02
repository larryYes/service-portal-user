package com.tyymt.wxplatform.service.portal.user.common.error;

import lombok.extern.slf4j.Slf4j;
import net.bestjoy.cloud.core.bean.Result;
import net.bestjoy.cloud.web.error.DefaultGlobalExceptionHandler;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;


/**
 * @author huangfei
 * @create 2020-12-07
 */
@Slf4j
@Component
@ControllerAdvice
@RestController
public class UnifiedExceptionHandler extends DefaultGlobalExceptionHandler {
    /**
     * 业务异常
     *
     * @param e 异常
     * @return 异常结果
     */
    @ExceptionHandler(value = FileUploadBase.SizeLimitExceededException.class)
    public Result handleBusinessException(FileUploadBase.SizeLimitExceededException e) {
        log.error(e.getMessage(), e);

        return Result.fail(99999999,"上传的图片过大");
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public Result handleBusinessException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);

        return Result.fail(99999999,"上传的图片过大");
    }

}
