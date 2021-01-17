package com.example.smartnote.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartnote.R;

import java.util.ArrayList;
import java.util.List;

public class TabAdapter  extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private final List<Integer> mFragmentIconList = new ArrayList<>();
    Context context;

    public TabAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }


    public void addFragment(Fragment fragment, String title, int tabIcon) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        mFragmentIconList.add(tabIcon);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
//        return mFragmentTitleList.get(position);
    return null;
    }


    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs, null);
        com.google.android.material.textview.MaterialTextView tabTextView = view.findViewById(R.id.tabViewText);
        tabTextView.setText(mFragmentTitleList.get(position));

        androidx.constraintlayout.utils.widget.ImageFilterView tabImage = view.findViewById(R.id.tabViewImage);
        tabImage.setImageResource(mFragmentIconList.get(position));
        return view;
    }

    public View getSelectedTabView(int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.tabs, null);
        com.google.android.material.textview.MaterialTextView tabTextView = view.findViewById(R.id.tabViewText);
        tabTextView.setText(mFragmentTitleList.get(position));
        tabTextView.setTextColor(ContextCompat.getColor(context, R.color.parentLayout));

        androidx.constraintlayout.utils.widget.ImageFilterView tabImageView = view.findViewById(R.id.tabViewImage);
        tabImageView.setImageResource(mFragmentIconList.get(position));
        tabImageView.setColorFilter(ContextCompat.getColor(context, R.color.parentLayout), PorterDuff.Mode.SRC_ATOP);
        return view;
    }
}