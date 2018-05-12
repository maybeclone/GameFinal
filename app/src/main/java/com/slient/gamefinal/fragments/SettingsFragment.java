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

import com.slient.gamefinal.R;
import com.slient.gamefinal.server.ConfigServer;
import com.slient.gamefinal.server.account.ChangePasswordPostAsyncTask;

/**
 * Created by silent on 5/13/2018.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener {
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText newPasswordAgainEditText;
    private Button changeButton;
    private android.support.v7.widget.Toolbar toolbar;

    public static SettingsFragment newInstance(){
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oldPasswordEditText = view.findViewById(R.id.oldPasswordEditText);
        newPasswordEditText = view.findViewById(R.id.newPasswordEditText);
        newPasswordAgainEditText = view.findViewById(R.id.newPasswordAgainEditText);
        changeButton = view.findViewById(R.id.changeButton);
        toolbar = view.findViewById(R.id.toolbar);

        changeButton.setOnClickListener(this);
        toolbar.setTitle("Settings");
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
            case R.id.changeButton:
                if(!oldPasswordEditText.getText().toString().isEmpty() && !newPasswordEditText.getText().toString().isEmpty()
                        && !newPasswordAgainEditText.getText().toString().isEmpty()){
                    if(newPasswordEditText.getText().toString().equals(newPasswordAgainEditText.getText().toString())){
                        new ChangePasswordPostAsyncTask(getContext(), oldPasswordEditText.getText().toString(), newPasswordEditText.getText().toString()).execute(ConfigServer.CHANGE_PASSWORD_URL);
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
