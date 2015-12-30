package com.gagnon.mario.mr.incexp.app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gagnon.mario.mr.incexp.app.Helper.AccountUtils;
import com.gagnon.mario.mr.incexp.app.Helper.UserProfileLoader;

import java.util.List;

public class MainActivityOld extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<AccountUtils.UserProfile>{

    public static final String LOG_TAG = MainActivityOld.class.getSimpleName();

    private List<String> mPossible_emails;
    private List<String> mPossible_names;
    private String mPrimary_email;
    private Uri mPossiblePhoto;


    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch(requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getSupportLoaderManager().initLoader(0, Bundle.EMPTY, this);
                }else{
                    Toast.makeText(MainActivityOld.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // app_bar_main.xml
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab); // app_bar_main.xml
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout); // activity_main.xml
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            public void onDrawerOpened(View drawerView){

                TextView textViewUserName = (TextView) findViewById(R.id.textViewUserName);
                TextView textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
                //ImageView mImageViewUserPicture;

                if (mPossible_emails.size() > 0 && null != textViewUserEmail) textViewUserEmail.setText(mPrimary_email == null ? mPossible_emails.get(0) :
                        mPrimary_email);
                if (mPossible_names.size() > 0 && null != textViewUserName) textViewUserName.setText(mPossible_names.get(0));

//                if(null != mImageViewUserPicture) mImageViewUserPicture.setImageURI(mPossiblePhoto);
                super.onDrawerOpened(drawerView);
              }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); // activity_main.xml
  //      navigationView.setNavigationItemSelectedListener(this);

        final int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_CONTACTS);
        if(hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_ASK_PERMISSIONS);
        }else{
            getSupportLoaderManager().initLoader(0, Bundle.EMPTY, this);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public Loader<AccountUtils.UserProfile> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new UserProfileLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<AccountUtils.UserProfile> loader, AccountUtils.UserProfile user_profile) {
        mPossible_emails = user_profile.possibleEmails();
        mPossible_names = user_profile.possibleNames();
        mPrimary_email = user_profile.primaryEmail();
        mPossiblePhoto = user_profile.possiblePhoto();
////        mTextViewUserName = (TextView) findViewById(R.id.textViewUserName);
//  //      mTextViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);
//        //ImageView imageViewUserPicture = (ImageView) findViewById(R.id.imageViewUserPicture);
//
//
//
//        // Sets the text to the likely possibility, this is the user defined primary possibility or the first possibility if there
//        // isn't a primary possibility
//        if (possible_emails.size() > 0 && null != mTextViewUserName) mTextViewUserEmail.setText(primary_email == null ? possible_emails.get(0) :
//                primary_email);
//        if (possible_names.size() > 0 && null != mTextViewUserName) mTextViewUserName.setText(possible_names.get(0));
//        //if (possible_phone_numbers.size() > 0) _phone_number.setText(primary_phone_number == null? possible_phone_numbers.get(0) : primary_phone_number);
//
//        //imageViewUserPicture.setImageURI(user_profile.possiblePhoto());
///*
//        _email_address.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line,
//                possible_emails));
//        _full_name.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line,
//                possible_names));
//        _phone_number.setAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line,
//                possible_phone_numbers));*/
    }

    @Override
    public void onLoaderReset(Loader<AccountUtils.UserProfile> loader) {

    }
}
