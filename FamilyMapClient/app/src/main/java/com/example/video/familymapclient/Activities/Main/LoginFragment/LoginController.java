package com.example.video.familymapclient.Activities.Main.LoginFragment;

import android.content.Context;
import android.widget.Toast;

import com.example.video.familymapclient.Communication.ClientCommunicator;
import com.example.video.familymapclient.Communication.ResultHolders.LoginResult;
import com.example.video.familymapclient.Communication.ResultHolders.RegisterResponse;
import com.example.video.familymapclient.Model.DatabaseModel.AuthToken;
import com.example.video.familymapclient.Model.UserInfo;
import com.example.video.familymapclient.Tasks.LoginTask;
import com.example.video.familymapclient.Tasks.RegisterTask;
import com.example.video.familymapclient.Tasks.UserInfoTask;

class LoginController {
    private LoginModel mModel;
    private Context mContext;

    LoginController(Context context){
        mModel = new LoginModel();
        mContext = context;
    }

    boolean checkLoginInput(){
        if (mModel.getServerHost() != null && mModel.getServerPort() != null &&
                mModel.getLoginRequest().getUsername() != null &&
                mModel.getLoginRequest().getPassword() != null){
            if(!mModel.getServerHost().equals("") && !mModel.getServerPort().equals("") &&
                    !mModel.getLoginRequest().getUsername().equals("") &&
                    !mModel.getLoginRequest().getPassword().equals("")){
                return true;
            }
        }
        return false;
    }

    boolean checkRegisterInput(){
        if (mModel.getServerHost() != null && mModel.getServerPort() != null &&
                mModel.getRegisterRequest().getUsername() != null &&
                mModel.getRegisterRequest().getPassword() != null &&
                mModel.getRegisterRequest().getFirstName() != null &&
                mModel.getRegisterRequest().getLastName() != null &&
                mModel.getRegisterRequest().getEmail() != null &&
                mModel.getRegisterRequest().getGender() != null){
            if(!mModel.getServerHost().equals("") && !mModel.getServerPort().equals("") &&
                    !mModel.getRegisterRequest().getUsername().equals("") &&
                    !mModel.getRegisterRequest().getPassword().equals("") &&
                    !mModel.getRegisterRequest().getFirstName().equals("") &&
                    !mModel.getRegisterRequest().getLastName().equals("") &&
                    !mModel.getRegisterRequest().getEmail().equals("") &&
                    !mModel.getRegisterRequest().getGender().equals("")){
                return true;
            }
        }
        return false;
    }

    private void getAllInfo(String username, String authToken){
        AuthToken token;
        UserInfo userInfo = null;

        token = new AuthToken(username, authToken);

        try {
            userInfo = new UserInfoTask().execute(token.getToken()).get();
            mModel.setUserInfo(userInfo);
        }catch(Exception e){
            e.printStackTrace();
        }

        if (userInfo == null){
            Toast.makeText(mContext, "Connection error!", Toast.LENGTH_SHORT).show();
        }
        else if (userInfo.getAllEvents() == null){
            Toast.makeText(mContext, "Invalid AuthToken!", Toast.LENGTH_SHORT).show();
        }
    }

    void login(){
        ClientCommunicator.SINGLETON.setLocalHost(mModel.getServerHost(), mModel.getServerPort());
        LoginResult result = null;

        try {
            result = new LoginTask().execute(mModel.getLoginRequest()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result == null) {
            Toast.makeText(mContext, "Connection error!", Toast.LENGTH_SHORT).show();
        } else if (result.isError()) {
            Toast.makeText(mContext, "Login failed!\n" + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
        } else {
            mModel.setSuccess(true);
            getAllInfo(result.getUsername(), result.getAuthToken());
        }
    }

    void register(){
        ClientCommunicator.SINGLETON.setLocalHost(mModel.getServerHost(), mModel.getServerPort());
        RegisterResponse response = null;

        try {
            response = new RegisterTask().execute(mModel.getRegisterRequest()).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response == null) {
            Toast.makeText(mContext, "Connecton error!", Toast.LENGTH_SHORT).show();
        } else if (response.isError()) {
            Toast.makeText(mContext, "Register failed!\n" + response.getErrorMessage(), Toast.LENGTH_SHORT).show();
        } else {
            mModel.setSuccess(true);
            getAllInfo(response.getUserName(), response.getAuthToken());
        }
    }

    void setServerHost(String host){
        mModel.setServerHost(host);
    }

    void setServerPort(String port){
        mModel.setServerPort(port);
    }

    void setUsername(String username){
        mModel.getRegisterRequest().setUsername(username);
        mModel.getLoginRequest().setUsername(username);
    }

    void setPassword(String password){
        mModel.getRegisterRequest().setPassword(password);
        mModel.getLoginRequest().setPassword(password);
    }

    void setFirstName(String firstName){
        mModel.getRegisterRequest().setFirstName(firstName);
    }

    void setLastName(String lastName){
        mModel.getRegisterRequest().setLastName(lastName);
    }

    void setEmail(String email){
        mModel.getRegisterRequest().setEmail(email);
    }

    void setGender(String gender){
        mModel.getRegisterRequest().setGender(gender);
    }

    UserInfo getUserInfo(){
        return mModel.getUserInfo();
    }

    boolean isSuccess(){
        return mModel.isSuccess();
    }
}
