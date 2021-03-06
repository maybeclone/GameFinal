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
import android.widget.Toast;
import android.widget.Toolbar;

import com.slient.gamefinal.R;
import com.slient.gamefinal.models.account.User;
import com.slient.gamefinal.server.ConfigServer;
import com.slient.gamefinal.server.account.RegisterPostUserAsyncTask;

/**
 * Created by silent on 5/12/2018.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private Button registerButton;
    private android.support.v7.widget.Toolbar toolbar;

    public static RegisterFragment newInstance(){
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        passwordAgainEditText = view.findViewById(R.id.passwordAgainEditText);
        registerButton = view.findViewById(R.id.registerButton);
        toolbar = view.findViewById(R.id.toolbar);

        registerButton.setOnClickListener(this);
        toolbar.setTitle("Register");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:
                if(!emailEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()
                        && !passwordAgainEditText.getText().toString().isEmpty()){
                    if(passwordEditText.getText().toString().equals(passwordAgainEditText.getText().toString())){
                        new RegisterPostUserAsyncTask(getContext(),
                                new User(emailEditText.getText().toString(), passwordEditText.getText().toString())).execute(ConfigServer.REGISTER_URL);
                    } else{
                        Toast.makeText(getContext(), "Password is not the same", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Please complete all information", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
