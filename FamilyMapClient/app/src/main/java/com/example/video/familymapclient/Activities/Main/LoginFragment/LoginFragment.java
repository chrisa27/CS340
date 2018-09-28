package com.example.video.familymapclient.Activities.Main.LoginFragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.video.familymapclient.Activities.Main.MainActivity;
import com.example.video.familymapclient.Model.UserInfo;
import com.example.video.familymapclient.R;

public class LoginFragment extends Fragment{
    public static final String USER_INFO = "userInfo";

    private Button mLoginButton;
    private Button mRegisterButton;
    private LoginController mController;

    private void setButtons(){
        if (mController.checkLoginInput()){
            mLoginButton.setEnabled(true);
        }
        else{
            mLoginButton.setEnabled(false);
        }

        if(mController.checkRegisterInput()){
            mRegisterButton.setEnabled(true);
        }
        else{
            mRegisterButton.setEnabled(false);
        }
    }

    private void loginSuccess(){
        Bundle bundle = new Bundle();
        bundle.putSerializable(USER_INFO, mController.getUserInfo());
        ((MainActivity)getActivity()).createMap(bundle);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mController = new LoginController(getContext());

        EditText mServerHostEdit;
        EditText mServerPortEdit;
        EditText mUsernameEdit;
        EditText mPasswordEdit;
        EditText mFirstNameEdit;
        EditText mLastNameEdit;
        EditText mEmailEdit;
        RadioGroup mGenderGroup;

        mServerHostEdit = (EditText) v.findViewById(R.id.server_host_input);
        mServerHostEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setServerHost(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {
                //not needed
            }
        });

        mServerPortEdit = (EditText) v.findViewById(R.id.server_port_input);
        mServerPortEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setServerPort(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUsernameEdit = (EditText) v.findViewById(R.id.username_input);
        mUsernameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setUsername(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPasswordEdit = (EditText) v.findViewById(R.id.password_input);
        mPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setPassword(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFirstNameEdit = (EditText) v.findViewById(R.id.first_name_input);
        mFirstNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setFirstName(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastNameEdit = (EditText) v.findViewById(R.id.last_name_input);
        mLastNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setLastName(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmailEdit = (EditText) v.findViewById(R.id.email_input);
        mEmailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mController.setEmail(s.toString());
                setButtons();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGenderGroup = (RadioGroup) v.findViewById(R.id.gender_group);
        mGenderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.male_radio){
                    mController.setGender("m");
                }else{
                    mController.setGender("f");
                }
                setButtons();
            }
        });

        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mLoginButton.setClickable(true);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.login();
                if (mController.isSuccess()) {
                    loginSuccess();
                }
            }
        });

        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setClickable(true);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mController.register();
                if(mController.isSuccess()) {
                    loginSuccess();
                }
            }
        });

        return v;
    }

    public static UserInfo getUserInfo(Bundle bundle){
        return (UserInfo)bundle.getSerializable(USER_INFO);
    }
}
