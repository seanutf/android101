package com.sean.android.login.manager;

import android.util.Log;

public class LoginManager {

    private LoginManager(){}

    private static class LoginManagerHolder{
        private static LoginManager instance = new LoginManager();
    }

    public static LoginManager getInstance(){
        return LoginManagerHolder.instance;
    }

    public void showTest(){
        Log.e("LoginTest","the LoginManager method showTes() is called");
    }
}
