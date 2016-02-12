package com.gagnon.mario.mr.incexp.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.contributor.ContributorEditorActivity;
import com.gagnon.mario.mr.incexp.app.contributor.ContributorFragment;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int ACTIVITY_CONTRIBUTOR = 100;

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TransactionFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TransactionFragment(),"transaction").commit();

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Transaction");

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_contributor) {

                    //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
                    MainActivity.this.toolbar.setTitle("Contributor");

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new ContributorFragment(), "contributor").commit();

                }

                if (menuItem.getItemId() == R.id.nav_account) {
                    //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
                    MainActivity.this.toolbar.setTitle("Account");


                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new AccountFragment(), "account").commit();
                }

                if (menuItem.getItemId() == R.id.nav_category) {

                    //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
                    MainActivity.this.toolbar.setTitle("Category");


                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new CategoryFragment(), "category").commit();
                }

                if (menuItem.getItemId() == R.id.nav_transaction) {

                    MainActivity.this.toolbar.setTitle("Transaction");

                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TransactionFragment(), "transaction").commit();
                }

                return false;
            }

        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

        setupFAB();
    }

    private void setupFAB() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d(LOG_TAG, String.valueOf(mFragmentManager.getFragments().size()));

                for(Fragment f : mFragmentManager.getFragments()){

                    if(null != f && f.isVisible()){

                        if(f instanceof TransactionFragment){
                            Snackbar.make(view, ((TransactionFragment)f).GetCurrentFragment(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        }else{

                            switch ( f.getTag() ){
                                case "contributor":

                                    Log.d(LOG_TAG, "Building Contributor Editor Screen");
                                    Contributor contributor = Contributor.createNew();

                                    Bundle arguments = new Bundle();
                                    arguments.putSerializable("item", contributor);

                                    Intent intent = new Intent(MainActivity.this, ContributorEditorActivity.class);
                                    intent.putExtras(arguments);
                                    startActivityForResult(intent, ACTIVITY_CONTRIBUTOR);

                                    break;
                                default:
                               Snackbar.make(view, f.getTag() , Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                                    break;
                            }
                        }


                    }
                }

//                ContentValues contributorValues  = new ContentValues();
//                contributorValues.put( IncomeExpenseContract.ContributorEntry.COLUMN_NAME, "Mario" );
//                Uri contributorUri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;
//
//                MainActivity.this.getContentResolver().insert(contributorUri, contributorValues);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
