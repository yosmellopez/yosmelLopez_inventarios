package com.bancopichincha.inventario.web.rest.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

public class HttpResponseUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String REFRESH_TOKEN_HEADER = "DRATS";

    public static final String AUTHORIZATION_TOKEN = "access_token";

    public static final String AUTHORIZATION_REFRESH_TOKEN = "refresh_token";

    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        String jwt = request.getParameter(AUTHORIZATION_TOKEN);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }

    public static String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        String jwt = request.getParameter(AUTHORIZATION_REFRESH_TOKEN);
        if (StringUtils.hasText(jwt)) {
            return jwt;
        }
        return null;
    }
}
