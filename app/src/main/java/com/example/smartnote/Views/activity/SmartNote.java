package com.example.smartnote.Views.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.smartnote.Model.Repository.Repo;
import com.example.smartnote.Util.Constants;
import com.example.smartnote.Util.Util;
import com.example.smartnote.Views.fragment.NormalNotes;
import com.example.smartnote.Views.fragment.PrivateNotes;
import com.example.smartnote.R;
import com.example.smartnote.Adapters.TabAdapter;
import com.example.smartnote.databinding.SmartNoteBinding;
import com.google.android.material.tabs.TabLayout;

public class SmartNote extends AppCompatActivity {

    TabAdapter tabAdapter;
    private SmartNoteBinding binding;
    private static final String TAG = "SmartNote";
//    SmartNoteViewModel smartNoteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.smart_note);
//        smartNoteViewModel = ViewModelProviders.of(this).get(SmartNoteViewModel.class);

//        binding.setLifecycleOwner(this);

        if (Util.getFromSharedPref(Constants.PASSWORD,this)==null || Util.getFromSharedPref(Constants.EMAIL,this)==null){
            binding.menuToolbar.setVisibility(View.GONE);
        }else {
            setupMenu();
        }


        binding.menuToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check if the user created or Not
                if (Util.getFromSharedPref("Password",SmartNote.this) != null){
                    Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
                    startActivity(intent);
                }else {
                    startActivity(new Intent(getApplicationContext(), NewProfile.class));
                }

            }
        });


        binding.newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewNote.class));
            }
        });

        showViewPager();


    }

    @SuppressLint("NewApi")
    void setupMenu(){
        binding.menuToolbar.inflateMenu(R.menu.profile_menu);
        binding.menuToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId()==R.id.changePassword){
                startActivity(new Intent(SmartNote.this,ChangePassword.class));
            }else if (item.getItemId() == R.id.forgetPassword){
                startActivity(new Intent(SmartNote.this,ForgetPassword.class));
            }else if (item.getItemId() == R.id.restData){
                Log.d(TAG, "onLongPress: ");
                MaterialDialog dialog = Util.showConfirmationDialog(this);

                View view =dialog.getView();
                com.google.android.material.textview.MaterialTextView title = view.findViewById(R.id.confirmDialogTitle);
                Button no = view.findViewById(R.id.confirmNo);
                Button yes = view.findViewById(R.id.confirmYes);
                title.setText("Are You Sure You Want To Reset All Data ?");

                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Repo repo = new Repo(getApplication());
                        repo.deleteAll();
                        Util.clearAllSharedPref(SmartNote.this);
                        dialog.dismiss();
/*                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.delete(notes);
//                        mDb.noteDAO().deleteNote(notes);
                        dialog.dismiss();
                    }
                });*/

                    }
                });

            }
            return false;
        });
    }


    void showViewPager(){
        tabAdapter = new TabAdapter(getSupportFragmentManager(),this);
        tabAdapter.addFragment(new NormalNotes(),"Normal Notes",R.drawable.ic_baseline_event_note_24 );
        tabAdapter.addFragment(new PrivateNotes(), "Private Notes",R.drawable.ic_baseline_stars_24);
        binding.viewPager.setAdapter(tabAdapter);
//        viewPager.setAdapter(tabAdapter);
        binding.tabHostLayout.setupWithViewPager(binding.viewPager);
//        tabLayout.setupWithViewPager(viewPager);

        highLightCurrentTab(0);

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                highLightCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void highLightCurrentTab(int position) {
        for (int i = 0; i < binding.tabHostLayout.getTabCount(); i++) {
            TabLayout.Tab tab = binding.tabHostLayout.getTabAt(i);
            assert tab != null;
            tab.setCustomView(null);
            tab.setCustomView(tabAdapter.getTabView(i));
        }
        if (position == 0){
            binding.toolbarTitle.setText("Normal Notes");
        }else {
            binding.toolbarTitle.setText("Private Notes");
        }

        TabLayout.Tab tab = binding.tabHostLayout.getTabAt(position);
        assert tab != null;
        tab.setCustomView(null);
        tab.setCustomView(tabAdapter.getSelectedTabView(position));
    }
}