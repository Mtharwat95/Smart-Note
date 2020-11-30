package com.example.smartnote.Views.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smartnote.Remainder.AlarmBrodcast;
import com.example.smartnote.Database.Notes;
import com.example.smartnote.R;
import com.example.smartnote.Remainder.AlarmSoundService;
import com.example.smartnote.Util.AppExecutors;
import com.example.smartnote.Util.Util;
import com.example.smartnote.ViewModels.NewNoteViewModel;
import com.example.smartnote.databinding.ActivityNewNoteBinding;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class NewNote extends AppCompatActivity  {

    String TAG = "NewNoteActivity";

    NewNoteViewModel newNoteViewModel;
    ActivityNewNoteBinding binding;
    Notes oldNote;

    String notePriority = "Normal",noteType = "Text";

    byte[] image = null;
    Boolean editMode = false , isRemainder = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_new_note);
        newNoteViewModel = ViewModelProviders.of(this).get(NewNoteViewModel.class);



        if (getIntent().getSerializableExtra("SelectedNote")!=null){
             oldNote = (Notes) getIntent().getSerializableExtra("SelectedNote");
            Log.d(TAG, "onCreate: "+oldNote.getTitle());
            setOldNoteData(oldNote);
        }

        binding.newNoteRemainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.newNoteRemainder.isChecked()){
                    isRemainder = true;
                    binding.newNoteRemainder.setText(R.string.clear_remainder);
                    binding.newNoteRemainder.setButtonDrawable(R.drawable.ic_baseline_add_alert_24);
                    binding.dateAndTime.setVisibility(View.VISIBLE);
                    Util.showDatePicker(binding.newNoteDate, NewNote.this);
                }else {
                    isRemainder = false;
                    binding.newNoteRemainder.setText(R.string.add_remainder);
                    binding.newNoteRemainder.setButtonDrawable(R.drawable.ic_baseline_add_alarm_24);
                    binding.dateAndTime.setVisibility(View.GONE);
                }
            }
        });

        binding.saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date="";
                String time = "";

                if (isRemainder){
                    isRemainder = true;
                    //TODO SetAlarm
                    date = binding.newNoteDate.getText().toString();
                    time = binding.newNoteTime.getText().toString();
                    Log.d(TAG, "Time: "+time+"---->"+"Date: "+date);

                }else {
                    isRemainder = false;
                    date = Util.getCurrentDate();

                }

                if (binding.newNotePriority.isChecked()){
                    notePriority = "Private";
                }else {
                    notePriority = "Normal";
                }

                final Notes note = new Notes(
                        binding.newNoteTextTitle.getText().toString(),
                        date,
                        binding.newNoteTxtNote.getText().toString(),
                        image,
                        noteType,
                        notePriority);


                if (!note.getTitle().isEmpty() && !note.getDate().isEmpty()){

                    String finalDate = date;
                    String finalTime = time;
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            if (editMode){
                                note.setId(oldNote.getId());
                                newNoteViewModel.update(note);
                                Log.d(TAG, "Id: "+ note.getId() +"-->"+"Title: "+note.getTitle()
                                        +"-->"+"Date: "+note.getDate()+"-->"+"Text: "+note.getTextNote()
                                        +"-->"+"Image: "+ Arrays.toString(note.getImageNote())
                                        +"-->"+"noteType: "+note.getNoteType() +"-->"+"priority: "+note.getNotePriority());
                                finish();
                            }else {
                                newNoteViewModel.insert(note);
                                if (isRemainder){
                                    setAlarm(finalDate, finalTime);
                                }
                                Log.d(TAG, "Id: "+ note.getId() +"-->"+"Title: "+note.getTitle()
                                        +"-->"+"Date: "+note.getDate()+"-->"+"Text: "+note.getTextNote()
                                        +"-->"+"Image: "+ Arrays.toString(note.getImageNote())
                                        +"-->"+"noteType: "+note.getNoteType() +"-->"+"priority: "+note.getNotePriority());
                                finish();
                            }

                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(),"All Data Required",Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.newNotePriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.getFromSharedPref("Password",NewNote.this) != null){
                    if (binding.newNotePriority.isChecked()){
                        notePriority = "Private";
                    }else {
                        notePriority = "Normal";
                    }
                }else {
                    MaterialDialog dialog = Util.showConfirmationDialog(NewNote.this);
                    View dialogView =dialog.getView();
                    com.google.android.material.textview.MaterialTextView title = dialogView.findViewById(R.id.confirmDialogTitle);
                    Button no = dialogView.findViewById(R.id.confirmNo);
                    Button yes = dialogView.findViewById(R.id.confirmYes);
                    title.setText("Create Your Profile To Start Saving Private Notes....");
                    yes.setText("Create");
                    no.setText("Later");

                    no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            notePriority = "Normal";
                            binding.newNotePriority.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                    yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(NewNote.this,NewProfile.class));
                            binding.newNotePriority.setChecked(false);
                            dialog.dismiss();
                        }
                    });

                }
            }
        });

        binding.newNoteShowDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showDatePicker(binding.newNoteDate, NewNote.this);
            }
        });

        binding.newNoteShowTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.showTimePicker(binding.newNoteTime, NewNote.this);
            }
        });

        binding.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imageLayout.setVisibility(View.VISIBLE);
//                binding.newNoteTxtNote.setHeight(10);
//                binding.note.setMinimumHeight(10);
                noteType = "Image";
                //TODO Open Pix
                Pix.start(NewNote.this, Options.init().setRequestCode(1));
            }
        });

        binding.openNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteType = "Text";
                binding.imageLayout.setVisibility(View.GONE);
            }
        });

        binding.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteType = "Image";
                //TODO Open Pix
                Pix.start(NewNote.this, Options.init().setRequestCode(1));
            }
        });

    }

    private void setOldNoteData(Notes oldNote) {
        editMode = true;
        binding.toolbarTitle.setText("Update Note");
        binding.saveNote.setText("Update");

        binding.newNoteTextTitle.setText(oldNote.getTitle());
        binding.newNoteDate.setVisibility(View.VISIBLE);
        binding.newNoteDate.setText(oldNote.getDate());

        binding.newNoteTxtNote.setText(oldNote.getTextNote());

        if (oldNote.getNotePriority().equals("Private")){
            binding.newNotePriority.setChecked(true);
        }
        Log.d(TAG, "Id: "+ oldNote.getId() +"-->"+"Title: "+oldNote.getTitle()
                +"-->"+"Date: "+oldNote.getDate()+"-->"+"Text: "+oldNote.getTextNote()
                +"-->"+"Image: "+ Arrays.toString(oldNote.getImageNote())
                +"-->"+"noteType: "+oldNote.getNoteType() +"-->"+"priority: "+oldNote.getNotePriority());

        if (oldNote.getImageNote()!=null){
            binding.imageLayout.setVisibility(View.VISIBLE);
            binding.newNoteImage.setImageBitmap(Util.byteToBitmap(oldNote.getImageNote()));
        }

        binding.dateAndTime.setVisibility(View.VISIBLE);
        binding.newNoteTime.setVisibility(View.GONE);
        binding.newNoteDate.setText(oldNote.getDate());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null){
            if (resultCode == Activity.RESULT_OK && requestCode == 1) {
                ArrayList<String> returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                assert returnValue != null;
//            profileImageUri = Uri.fromFile(new File(returnValue.get(0)));
                Uri imageUri = Uri.fromFile(new File(returnValue.get(0)));
                image = Util.convertImageToByte(imageUri,getApplication());
                binding.imageLayout.setVisibility(View.VISIBLE);
                binding.newNoteImage.setImageBitmap(Util.byteToBitmap(image));
            }
        }else {
            Toast.makeText(this,"No Image Selected",Toast.LENGTH_SHORT).show();
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
    }

    private void setAlarm(String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getApplicationContext(), AlarmBrodcast.class);
//        intent.putExtra("event", text);
        //TODO Send Note data to open when click notification
        intent.putExtra("time", date);
        intent.putExtra("date", time);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + time;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Toast.makeText(NewNote.this,"Remainder Set At: " +dateandtime,Toast.LENGTH_SHORT).show();
//        finish();

    }


}