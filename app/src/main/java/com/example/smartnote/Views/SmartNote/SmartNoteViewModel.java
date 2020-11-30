package com.example.smartnote.Views.SmartNote;

import androidx.lifecycle.ViewModel;

public class SmartNoteViewModel extends ViewModel {

//    static MutableLiveData<String> title = new MutableLiveData<>();
//    AppCompatActivity context;
//
//    public SmartNoteViewModel(AppCompatActivity context) {
//    this.context = context;
//    }
//
//    public static void setTitle(String address){
//        title.setValue(address);
//    }
//
//    public void newNote(){
//        context.startActivity(new Intent(context, NewNote.class));
//    }
//
//    public void openProfile(){
//        //TODO check if the user created or Not
//        Intent intent = new Intent(context, MyProfile.class);
//        context.startActivity(intent);
//    }
//
//    @BindingAdapter("adapter")
//    public static void setUpPager(ViewPager pager , SmartNoteViewModel viewModel, TabLayout tabLayout){
//        TabAdapter tabAdapter = new TabAdapter(viewModel.context.getSupportFragmentManager(),pager.getContext());
//        tabAdapter.addFragment(new NormalNotes(),"Normal Notes", R.drawable.ic_baseline_event_note_24 );
//        tabAdapter.addFragment(new PrivateNotes(), "Private Notes",R.drawable.ic_baseline_stars_24);
//        pager.setAdapter(tabAdapter);
////        viewPager.setAdapter(tabAdapter);
//        tabLayout.setupWithViewPager(pager);
////        tabLayout.setupWithViewPager(viewPager);
//
//        highLightCurrentTab(0,tabLayout,tabAdapter);
//
//        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//            @Override
//            public void onPageSelected(int position) {
//                highLightCurrentTab(position,tabLayout,tabAdapter);
//            }
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//    }
//
//    private static void highLightCurrentTab(int position,TabLayout tabLayout,TabAdapter tabAdapter) {
//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            assert tab != null;
//            tab.setCustomView(null);
//            tab.setCustomView(tabAdapter.getTabView(i));
//        }
//        if (position == 0){
//            setTitle("Normal Notes");
////            binding.toolbarTitle.setText("Normal Notes");
//        }else {
//            setTitle("Private Notes");
////            binding.toolbarTitle.setText("Private Notes");
//        }
//
//        TabLayout.Tab tab = tabLayout.getTabAt(position);
//        assert tab != null;
//        tab.setCustomView(null);
//        tab.setCustomView(tabAdapter.getSelectedTabView(position));
//    }

}
