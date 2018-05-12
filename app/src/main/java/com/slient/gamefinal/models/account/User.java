package com.slient.gamefinal.models.account;

/**
 * Created by silent on 5/12/2018.
 */
public class User {

    public String username;
    public String password;
    public String accessToken;

    public User(){

    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

}
