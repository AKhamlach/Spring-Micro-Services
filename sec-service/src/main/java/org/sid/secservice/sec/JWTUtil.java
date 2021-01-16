package org.sid.secservice.sec;

public class JWTUtil {
    public static final String SECRET = "mySecret1234";
    public static final String AUTH_HEADER = "Authorization";
    public static final long EXPIRE_ACCESS_TOKEN = 120000;//2min
    public static final long EXPIRE_REFRESH_TOKEN = 900000;//15min
    public static final String PREFIX = "Bearer ";
    public static final String USERS_URI = "/users";
    public static final String ROLES_URI = "/roles";
    public static final String ADD_ROLE_TO_USER_URI = "/addRoleToUser";
    public static final String REFRESH_TOKEN_URI = "/refreshToken";
    public static final String PROFILE_URI = "/profile";



}
