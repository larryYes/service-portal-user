package com.tyymt.wxplatform.service.portal.user.common.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author huangfei
 * @create 2020-10-27 11:55
 */
@Getter
@Setter
@Builder
public class JWTToken {
    private String access_token;
    private String token_type;
    private Long expires_in;
}