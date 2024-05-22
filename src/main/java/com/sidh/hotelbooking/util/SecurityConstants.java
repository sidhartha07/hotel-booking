package com.sidh.hotelbooking.util;

public class SecurityConstants {

    private SecurityConstants() {

    }

    public static final String REGISTER = "/api/v1/customer/register";
    public static final String LOGIN = "/api/v1/customer/login";
    public static final String BEARER = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    public static final String SWAGGER_API_DOCS_V2 = "/v2/api-docs";
    public static final String SWAGGER_API_DOCS_V3 = "/v3/api-docs";
    public static final String SWAGGER_API_DOCS_V3_ALL = "/v3/api-docs/**";
    public static final String SWAGGER_RESOURCES = "/swagger-resources";
    public static final String SWAGGER_RESOURCES_ALL = "/swagger-resources/**";
    public static final String SWAGGER_CONFIGURATION_UI = "/configuration/ui";
    public static final String SWAGGER_CONFIGURATION_SECURITY = "/configuration/security";
    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String WEBJARS = "/webjars/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
}
