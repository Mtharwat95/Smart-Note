package com.example.smartnote.Views.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.smartnote.R;
import com.example.smartnote.Util.Constants;
import com.example.smartnote.Util.Util;
import com.example.smartnote.databinding.ActivityNewProfileBinding;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class NewProfile extends AppCompatActivity {
    String TAG = "NewProfile";
    byte[] image = null;
    String eMail,password,confirmPassword;

    ActivityNewProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_profile);


        binding.close.setOnClickListener(view -> {
            finish();
            Toast.makeText(getApplicationContext(),"You Will Cannot Using Private Notes",Toast.LENGTH_SHORT).show();
        });

        binding.addProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pix.start(NewProfile.this, Options.init().setRequestCode(1));
            }
        });

        binding.addProfileSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eMail = binding.addProfileEmail.getText().toString();
                password = binding.addProfilePassword.getText().toString();
                confirmPassword = binding.addProfileConfirmPassword.getText().toString();
                if (password.equals(confirmPassword)){
                    if (Util.validateLogin(binding.addProfileEmail , binding.addProfilePassword)){
                        Util.saveInSharedPref(Constants.PASSWORD,password,NewProfile.this);
                        Util.saveInSharedPref(Constants.EMAIL,eMail,NewProfile.this);
                        Toast.makeText(getApplicationContext(),"Created Success",Toast.LENGTH_SHORT).show();
                        finish();
//                        Util.saveInSharedPref("ProfileImage",imagePath,NewProfile.this);
//                        Log.d(TAG, "ImagePath: "+imagePath);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Passwords Doesn't Match",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

/*    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        if (resultCode == Activity.RESULT_OK && requestCode == 1) {
            ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            assert returnValue != null;
            Uri imageUri = Uri.fromFile(new File(returnValue.get(0)));
            image = Util.convertImageToByte(imageUri,getApplication());
            imagePath = returnValue.get(0);
            Log.d(TAG, "onActivityResult: "+ returnValue.get(0));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Boolean ImageAdded = Util.setImageBitmap(imageUri, NewProfile.this, binding.addProfileImage);
                if (!ImageAdded) {
//                    binding.newNoteImage.setVisibility(View.GONE);
                    Toast.makeText(this, "Please select another Photo", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        Log.d(TAG, "onActivityResult: " + "4050");
        if (requestCode == PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS) {
            // If request is cancelled, the result arrays are empty.
            Log.d(TAG, "onActivityResult: " + "6070");
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Util.addToast(this, "Approve permissions to open Pix ImagePicker");
            }
        } else {
            Log.d(TAG, "onActivityResult: " + "100000");
        }
    }*/

}