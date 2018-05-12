package com.slient.gamefinal.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.slient.gamefinal.R;
import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.models.account.User;
import com.slient.gamefinal.server.ConfigServer;
import com.slient.gamefinal.server.account.LoginPostUserAsyncTask;

/**
 * Created by silent on 5/12/2018.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView createText;
    private Button loginButton;
    private android.support.v7.widget.Toolbar toolbar;

    public static LoginFragment newInstance(){
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        createText = view.findViewById(R.id.createText);
        loginButton = view.findViewById(R.id.loginButton);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Login");
        loginButton.setOnClickListener(this);
        createText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createText:
                ((MainActivity) getContext()).replaceRegisterFragment();
                break;
            case R.id.loginButton:
                if(!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
                    new LoginPostUserAsyncTask(getContext(),
                            new User(usernameEditText.getText().toString(), passwordEditText.getText().toString())).execute(ConfigServer.LOGIN_URL);
                } else {
                    Toast.makeText(getContext(), "Please complete all information", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
