package com.gagnon.mario.mr.incexp.app.transaction;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.account.Account;

import java.util.ArrayList;
import java.util.List;

public class TransactionContainerFragment extends Fragment {

    private static final String LOG_TAG = TransactionContainerFragment.class.getSimpleName();

    private ViewPager mViewPager;
    private TransactionContainerFragment.SectionsPagerAdapter  mAdapter;
    private List<Account> mAccounts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(null != bundle){
            mAccounts = (List<Account>) bundle.getSerializable("accounts");
        }
        if(mAccounts == null){
            mAccounts = new ArrayList<>();
        }


        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout, null);
        TabLayout tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        mViewPager = (ViewPager) x.findViewById(R.id.container);
        mAdapter = new TransactionContainerFragment.SectionsPagerAdapter(getChildFragmentManager());

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

        mAdapter.addFrag(new DashboardFragment(), "DashBoard");

        for(Account account: mAccounts){

            Log.d(LOG_TAG, account.getId().toString());
            TransactionFragment tf = new TransactionFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("AccountId", account.getId());
            tf.setArguments(bundle);

            mAdapter.addFrag(tf, account.getName());
        }

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
            Bundle args;
            args = fragment.getArguments();
            if(args == null){
                args = new Bundle();
            }

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