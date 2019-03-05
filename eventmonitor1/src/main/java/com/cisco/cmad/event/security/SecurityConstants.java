/**
 * 
 */
package com.cisco.cmad.event.security;

/**
 * @author tcheedel
 *
 */
public class SecurityConstants {

    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 86_400_000; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String LOGIN_URL = "/login";
    public static final String SIGN_UP_URL = "/user";
    public static final String INDEX_HTML = "/*html";
    public static final String JS_FILES = "/*.js";
    public static final String IMAGE_FILES = "/imagefiles/*";

}
