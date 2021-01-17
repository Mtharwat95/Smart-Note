package com.example.smartnote.Views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.example.smartnote.R;
import com.example.smartnote.Util.Constants;
import com.example.smartnote.Util.SendMail;
import com.example.smartnote.Util.Util;
import com.example.smartnote.databinding.ActivityForgetPasswordBinding;

public class ForgetPassword extends AppCompatActivity {

    ActivityForgetPasswordBinding binding;
    String oldEmail = " ",email,oldPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forget_password);

        binding.sendPassword.setOnClickListener(view -> {
            oldEmail = Util.getFromSharedPref(Constants.EMAIL,this);
            email = binding.myEmail.getText().toString();
            ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
            if (!oldEmail.equals("") && email.equals(oldEmail)){
                oldPassword = Util.getFromSharedPref(Constants.PASSWORD,this);
                if (networkInfo.isConnected()){
                    //Creating SendMail object
                    SendMail sm = new SendMail(ForgetPassword.this, email, Constants.MAIL_SUBJECT,
                            Constants.MAIL_MESSAGE+"{ "+oldPassword+" }");
                    //Executing sendmail to send email
                    sm.execute();
                }else {
                    Toast.makeText(ForgetPassword.this,"Something Went Wrong Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }else {
                binding.myEmail.requestFocus();
                binding.myEmail.setError("This Email Doesn't Exist");
            }
        });

        binding.close.setOnClickListener(view -> finish());
    }

}