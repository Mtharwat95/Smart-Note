package com.example.smartnote.Util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smartnote.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Util {

    static String TAG = "Util";
    public static int IMAGE_REQUEST = 1;
    public static int IMAGE_REQUEST_Profile = 2;
    public static int IMAGE_REQUEST_National = 3;
    public static int IMAGE_REQUEST_National1 = 4;
    public static int IMAGE_REQUEST_National2 = 5;
    public static int IMAGE_REQUEST_National3 = 6;
    public static int IMAGE_REQUEST_Profile1 = 7;


    public static boolean validateRegister(EditText userName, EditText Email, EditText password) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (userName.getText().toString().isEmpty() || userName.getText().toString().length() < 3) {
            userName.requestFocus();
            userName.setError("Required");
            return false;
        } else if (Email.getText().toString().trim().isEmpty() || Email.getText().toString().length() < 5 ||
                !Email.getText().toString().trim().matches(emailPattern)) {
            Email.requestFocus();
            Email.setError("Your E-mail is incorrect");
            return false;
        } else if (password.getText().toString().isEmpty() || password.getText().toString().length() < 6) {
            Email.requestFocus();
            Email.setError("Password must be at least 6 character");
            return false;
        } else
            return true;

    }

/*
    public static boolean validateRegister(UserData userData) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (userName.getText().toString().isEmpty() || userName.getText().toString().length() < 3) {
            userName.requestFocus();
            userName.setError("Required");
            return false;
        } else if (Email.getText().toString().trim().isEmpty() || Email.getText().toString().length() < 5 ||
                !Email.getText().toString().trim().matches(emailPattern)) {
            Email.requestFocus();
            Email.setError("Your E-mail is incorrect");
            return false;
        } else if (password.getText().toString().isEmpty() || password.getText().toString().length() < 6) {
            Email.requestFocus();
            Email.setError("Password must be at least 6 character");
            return false;
        } else
            return true;

    }
*/

    public static void hideKeyboard(Activity activity){
        if(activity != null)
        {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

            if(imm != null)
            {
                imm.hideSoftInputFromWindow((IBinder) activity.getWindow(), 0);
            }
        }
    }

    public static boolean validateRegister(EditText fullName, EditText nationalID, EditText Email, EditText password,
                                           EditText phoneNumber, RadioButton gender, TextView country, EditText address,
                                           RadioButton haveCar) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (fullName.getText().toString().isEmpty() || fullName.getText().toString().length() < 3) {
            fullName.requestFocus();
            fullName.setError("All Data Require");
            return false;
        } else if (nationalID.getText().toString().isEmpty() || nationalID.getText().toString().length() < 14) {
            nationalID.requestFocus();
            nationalID.setError("All Data Require");
            return false;
        } else if (Email.getText().toString().trim().isEmpty() ||
                !Email.getText().toString().trim().matches(emailPattern)) {
            Email.requestFocus();
            Email.setError("Your E-mail is incorrect");
            return false;
        } else if (password.getText().toString().isEmpty() || password.getText().toString().length() < 6) {
            password.requestFocus();
            password.setError("All Data Require");
            return false;
        } else if (phoneNumber.getText().toString().trim().isEmpty() || phoneNumber.getText().toString().length() < 11) {
            phoneNumber.requestFocus();
            phoneNumber.setError("All Data Require");
            return false;
        }
        if (gender.getText().toString().trim().isEmpty()) {
            gender.requestFocus();
            gender.setError("All Data Require");
            return false;
        } else if (country.getText().toString().trim().isEmpty()) {
            country.requestFocus();
            country.setError("All Data Require");
            return false;
        } else if (address.getText().toString().trim().isEmpty()) {
            address.requestFocus();
            address.setError("All Data Require");
            return false;
        } else if (haveCar.getText().toString().trim().isEmpty()) {
            haveCar.requestFocus();
            haveCar.setError("All Data Require");
            return false;
        } else
            return true;

    }

    public static boolean validateCar(TextView selectCar, TextView selectCarYear, EditText carNumber, EditText carCC) {
        if (selectCar.getText().toString().trim().isEmpty()) {
            selectCar.requestFocus();
            selectCar.setError("All Data Require");
            return false;
        } else if (selectCarYear.getText().toString().trim().isEmpty()) {
            selectCarYear.requestFocus();
            carNumber.setError("All Data Require");
            return false;
        } else if (carCC.getText().toString().trim().isEmpty()) {
            carCC.requestFocus();
            carCC.setError("All Data Require");
            return false;
        } else
            return true;
    }

    public static boolean validateLogin(EditText Email, EditText password) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (Email.getText().toString().trim().isEmpty() ||
                Email.getText().toString().length() < 5 ||
                !Email.getText().toString().trim().matches(emailPattern)) {
            Email.requestFocus();
            Email.setError("Your E-mail is incorrect");
            return false;
        } else if (password.getText().toString().isEmpty() ||
                password.getText().toString().length() < 6) {
            password.requestFocus();
            password.setError("Password must be at least 6 character");
            return false;
        } else
            return true;

    }

    public static void startFragment(Fragment fragment, String tag, AppCompatActivity activity, int parent) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(parent, fragment, tag);
        transaction.commit();
    }

    public static void clearBackStack(AppCompatActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public static void clearBackStack(Fragment activity) {
        FragmentManager manager = activity.getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    public static void startActivityUtil(Activity activity, Class destination) {
        Intent intent = new Intent(activity, destination);
        activity.startActivity(intent);
        activity.finish();
    }

    public static void addToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //TODO /* ================================================================= */
    public static void saveInSharedPref(String key, String value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    //----------------------------------------------------------------- //
    public static String getFromSharedPref(String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());

        String value = appSharedPrefs.getString(key, "");
        if (value != null && !value.isEmpty())
            return value;
        else
            return null;
    }

    //----------------------------------------------------------------------------------//
    /* ================================================================= */
    public static void saveListInShared(String key, List<String> value, Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor mEdit1 = sp.edit();
        /* sKey is an array */
        mEdit1.putInt("Status_size", value.size());

        for (int i = 0; i < value.size(); i++) {
            mEdit1.putString(key, value.get(i));
        }

        mEdit1.apply();
    }

    //----------------------------------------------------------------- //
    public static List<String> getListFromSharedPref(String key, Context context) {
        SharedPreferences mSharedPreference1 = PreferenceManager.getDefaultSharedPreferences(context);
        List<String> dd = new ArrayList<>();

        int size = mSharedPreference1.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            dd.add(mSharedPreference1.getString(key, null));
        }
        return dd;
    } /* ============================================================== */

    //----------------------------------------------------------------------------------//
    public static void saveIntegerSharedPref(String key, Integer value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }

    //----------------------------------------------------------------------------------//
    public static Integer getIntegerFromSharedPref(String key, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());

        Integer value = appSharedPrefs.getInt(key, 0);
        if (value != null)
            return value;
        else
            return 0;
    }

    /* ============================================================== */

    public static void clear(Context context, String key) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.remove(key);
        prefsEditor.apply();
    }

    public static void clearAllSharedPref(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.clear().apply();
    }

    public static void updateInSharedPref(String key, String value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static void updateIntegerInSharedPref(String key, Integer value, Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        prefsEditor.putInt(key, value);
        prefsEditor.apply();
    }


    //----------------------------------------------------------------------------------//
    //TODO /* ============================================================== */


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Boolean setImageBitmap(Uri imageUri, Context context, ImageView imageView) {
        if (!Objects.requireNonNull(imageUri.getPath()).isEmpty()) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
                imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap, imageView.getWidth(), imageView.getHeight(), false));
                return true;
            } catch (IOException e) {
                Log.d("openCamera", Objects.requireNonNull(e.getLocalizedMessage()));
                e.printStackTrace();
                return false;
            }
        } else {
            Log.d("openCamera", ": 2");
            return false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getFileExtention(Activity activity, Uri uri) {
        ContentResolver contentResolver = Objects.requireNonNull(activity).getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    private static String date, time;

    public static void showDatePicker(final TextView textView, Context context) {
        // Get Current Date

        final Calendar c = Calendar.getInstance();
        //TODO /* ======================================================== */
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(date);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public static void showTimePicker(final TextView textView, Context context) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String AM_PM;
                        if (hourOfDay < 12) {
                            AM_PM = "AM";
                        } else {
                            AM_PM = "PM";
                        }
                        time = hourOfDay + ":" + minute + "  " + AM_PM;
                        textView.setText(time);
//                        txtTime.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public static void changeFragment(Fragment fragment, boolean needToAddBackstack, FragmentManager mFragmentManager, int fragmentId) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(fragmentId, fragment);
        mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (needToAddBackstack)
            mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public static String getCurrentDate() {
//        Date c = Calendar.getInstance().getTime();

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

//        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
//        Log.d(TAG, "getCurrentDate: " + currentDate);
        return currentDate;
    }

    public static boolean isBefore(String tripDate) {

        @SuppressLint("SimpleDateFormat")
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Date date1 = null;
        Date currentDate = Calendar.getInstance().getTime();
        try {
            date1 = format.parse(tripDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date1.before(currentDate)) {
            return true;
        }

        Log.d(TAG, "getDate: " + date1 + "---->");
        Log.d(TAG, "getDate222: " + currentDate + "---->");
        return false;
    }


    public static MaterialDialog showCustomDialog(Context context) {
        MaterialDialog dialog;
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.custom_progress, false)
                .show();
        View view = dialog.getCustomView();
        assert view != null;

        return dialog;
    }
    static Boolean confirmRes = null;
    public static MaterialDialog showConfirmationDialog(Context context) {

        MaterialDialog dialog;
        dialog = new MaterialDialog.Builder(context)
                .customView(R.layout.confirmation_dialog, false)
                .show();
        View view = dialog.getCustomView();
        assert view != null;

        return dialog;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String loadJSONFromAsset(String jsonFile,Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("TAG", "loadJSONFromAsset: "+json +" ");
        return json;
    }

    public static byte[] convertImageToByte(Uri uri , Application context){
        byte[] data = null;
        try {
            ContentResolver cr = context.getBaseContext().getContentResolver();
            InputStream inputStream = cr.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }
    public static Bitmap byteToBitmap(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

}
