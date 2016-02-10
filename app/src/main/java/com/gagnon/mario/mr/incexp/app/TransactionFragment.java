package com.gagnon.mario.mr.incexp.app;

/**
 * Created by mario on 2/2/2016.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TransactionFragment extends Fragment {


    private ViewPager mViewPager;
    private TransactionFragment.SectionsPagerAdapter  mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout, null);
        TabLayout tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        mViewPager = (ViewPager) x.findViewById(R.id.container);
        mAdapter = new TransactionFragment.SectionsPagerAdapter(getChildFragmentManager());

        /**
         *Set an Apater for the View Pager
         */
        setupViewPager();

        tabLayout.setupWithViewPager(mViewPager);

        return x;

    }

    public String GetCurrentFragment(){
        int i = mViewPager.getCurrentItem();
        Fragment f = mAdapter.getItem(i);
        Bundle args = f.getArguments();
        String tag = args.getString("tag");
        return tag;
    }

    private void setupViewPager(){

        mAdapter.addFrag(new OneFragment(), "ONE");
        mAdapter.addFrag(new TwoFragment(), "TWO");
        mAdapter.addFrag(new ThreeFragment(), "THREE");
        mAdapter.addFrag(new FourFragment(), "FOUR");
        mAdapter.addFrag(new FiveFragment(), "FIVE");
        mAdapter.addFrag(new SixFragment(), "SIX");
        mAdapter.addFrag(new SevenFragment(), "SEVEN");
        mAdapter.addFrag(new EightFragment(), "EIGHT");
        mAdapter.addFrag(new NineFragment(), "NINE");
        mAdapter.addFrag(new TenFragment(), "TEN");
        mViewPager.setAdapter(mAdapter);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            int i = mFragmentList.size();

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title){
            Bundle args = new Bundle();
            args.putString("tag", title);
            fragment.setArguments(args);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}