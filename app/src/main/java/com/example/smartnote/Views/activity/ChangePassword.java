package com.example.smartnote.Views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.smartnote.R;
import com.example.smartnote.Util.Constants;
import com.example.smartnote.Util.Util;
import com.example.smartnote.databinding.ActivityChangePasswordBinding;

public class ChangePassword extends AppCompatActivity  {


    String odlPassword,oldEmail,currentEmail,currentPassword,newPassword,confirmNewPassword;

    ActivityChangePasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_change_password);

        binding.toolbarTitle.setText(getString(R.string.change_password));
        binding.close.setVisibility(View.VISIBLE);
        binding.close.setOnClickListener(view -> finish());

        if (Util.getFromSharedPref(Constants.PASSWORD,this)!=null && Util.getFromSharedPref(Constants.EMAIL,this)!=null){
            oldEmail = Util.getFromSharedPref(Constants.EMAIL,this);
            odlPassword = Util.getFromSharedPref(Constants.PASSWORD,this);
        }

        binding.changePassword.setOnClickListener(view -> {
            currentPassword = binding.currentPassword.getText().toString();
            currentEmail = binding.currentEmail.getText().toString();
            newPassword = binding.newPassword.getText().toString();
            confirmNewPassword = binding.confirmNewPassword.getText().toString();

            if (oldEmail!=null&&odlPassword!=null){
                if (!currentEmail.equals(oldEmail) || !currentPassword.equals(odlPassword)){
                    binding.currentEmail.requestFocus();
                    binding.currentEmail.setError("Invalid Email Or Password");
                    binding.currentPassword.setError("Invalid Email Or Password");
                }else if (newPassword.length()<6){
                    Toast.makeText(ChangePassword.this,"Your password should be at least 6 characters",Toast.LENGTH_SHORT).show();
                }else if (!newPassword.equals(confirmNewPassword)){
                    binding.confirmNewPassword.requestFocus();
                    binding.newPassword.setError("Password not match");
                    binding.confirmNewPassword.setError("Password not match");
                }else {
                    Util.updateInSharedPref(Constants.PASSWORD,newPassword,ChangePassword.this);
                    Toast.makeText(ChangePassword.this,"Your password changed successfully",Toast.LENGTH_SHORT).show();
                    finish();
//                    Util.updateInSharedPref(Constants.EMAIL,currentEmail,ChangePassword.this);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



}