package com.slient.gamefinal.server;

/**
 * Created by silent on 5/12/2018.
 */
public class ConfigServer {

    public static final String BASE_URL = "http://10.0.2.2:8088/api";

    public static final String REGISTER_URL = BASE_URL + "/Register";
    public static final String LOGIN_URL = BASE_URL + "/Login";
    public static final String SCORE_URL = BASE_URL + "/Score";
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "/ChangePassword";

    public static final String ARGU_USERNAME = "Email";
    public static final String ARGU_PASSWORD = "Password";

    public static final String ARGU_USERNAME_LOGIN = "username";
    public static final String ARGU_PASSWORD_LOGIN = "password";
    public static final String ARGU_GRANT_TYPE_LOGIN = "grant_type";

    public static final String ARGU_USERNAME_SCORE = "Username";
    public static final String ARGU_DATETIME_SCORE = "DateTime";
    public static final String ARGU_SCORE_SCORE = "Score";

    public static final String ARGU_OLD_PASSWORD_PASS = "OldPassword";
    public static final String ARGU_NEW_PASSWORD = "NewPassword";

}
