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

import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.account.AccountEditorActivity;
import com.gagnon.mario.mr.incexp.app.account.AccountFragment;
import com.gagnon.mario.mr.incexp.app.category.Category;
import com.gagnon.mario.mr.incexp.app.category.CategoryEditorActivity;
import com.gagnon.mario.mr.incexp.app.category.CategoryFragment;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.contributor.ContributorEditorActivity;
import com.gagnon.mario.mr.incexp.app.contributor.ContributorFragment;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseRequestWrapper;
import com.gagnon.mario.mr.incexp.app.helper.Utility;
import com.gagnon.mario.mr.incexp.app.payment_method.PaymentMethod;
import com.gagnon.mario.mr.incexp.app.payment_method.PaymentMethodEditorActivity;
import com.gagnon.mario.mr.incexp.app.payment_method.PaymentMethodFragment;
import com.gagnon.mario.mr.incexp.app.transaction.TransactionContainerFragment;

public class MainActivity extends AppCompatActivity implements ContributorFragment.OnItemSelectedListener,
        AccountFragment.OnItemSelectedListener,
        PaymentMethodFragment.OnItemSelectedListener,
        CategoryFragment.OnItemSelectedListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private final int ACTIVITY_CONTRIBUTOR = 100;
    private final int ACTIVITY_ACCOUNT = 200;
    private final int ACTIVITY_PAYMENT_METHOD = 300;
    private final int ACTIVITY_CATEGORY = 400;

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
        setContentView(R.layout.main_activity);

        /**
         *Setup the DrawerLayout and NavigationView
         */
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TransactionContainerFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.containerView, new TransactionContainerFragment(), "transaction").commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Income Expense");

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_contributor) {

                    MainActivity.this.toolbar.setTitle("Contributor");

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new ContributorFragment(), "contributor").commit();

                }

                if (menuItem.getItemId() == R.id.nav_account) {
                    MainActivity.this.toolbar.setTitle("Account");

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new AccountFragment(), "account").commit();
                }

                if (menuItem.getItemId() == R.id.nav_payment_method) {
                    MainActivity.this.toolbar.setTitle("Payment Method");

                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView, new PaymentMethodFragment(), "payment_method").commit();
                }

                if (menuItem.getItemId() == R.id.nav_category) {

                    //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
                    MainActivity.this.toolbar.setTitle("Category");


                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new CategoryFragment(), "category").commit();
                }

                if (menuItem.getItemId() == R.id.nav_transaction) {

                    MainActivity.this.toolbar.setTitle("Transaction");

                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();

                    TransactionContainerFragment tf = new TransactionContainerFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable( "accounts", IncomeExpenseRequestWrapper.getAccounts(getContentResolver(), true ));
                    tf.setArguments(bundle);

                    xfragmentTransaction.replace(R.id.containerView, tf, "transaction").commit();
                }

                if (menuItem.getItemId() == R.id.nav_settings) {

                    MainActivity.this.toolbar.setTitle("Settings");

                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }


                return false;
            }

        });

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
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

                String defaultCurrency;

                for (Fragment f : mFragmentManager.getFragments()) {

                    if (null != f && f.isVisible()) {

                        if (f instanceof TransactionContainerFragment) {
                            Snackbar.make(view, ((TransactionContainerFragment) f).GetCurrentFragment(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();

                        } else {

                            switch (f.getTag()) {
                                case "contributor":

                                    Contributor contributor = Contributor.createNew();

                                    Bundle arguments = new Bundle();
                                    arguments.putSerializable("item", contributor);

                                    Intent intent = new Intent(MainActivity.this, ContributorEditorActivity.class);
                                    intent.putExtras(arguments);
                                    startActivityForResult(intent, ACTIVITY_CONTRIBUTOR);

                                    break;
                                case "category":

                                    Category category = Category.createNew();

                                    arguments = new Bundle();
                                    arguments.putSerializable("item", category);

                                    intent = new Intent(MainActivity.this, CategoryEditorActivity.class);
                                    intent.putExtras(arguments);
                                    startActivityForResult(intent, ACTIVITY_CATEGORY);

                                    break;

                                case "account":

//                                    ContentResolver contentResolver = getContentResolver();
//                                    Uri accountUri = IncomeExpenseContract.AccountEntry.CONTENT_URI;
//
//                                    ContentValues contentValues = new ContentValues();
//                                    contentValues.put(IncomeExpenseContract.AccountEntry.COLUMN_NAME, "Account1");
//                                    contentValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY, "CAD");
//                                    contentValues.put(IncomeExpenseContract.AccountEntry.COLUMN_CLOSE, false);
//
//                                    contentResolver.insert(accountUri, contentValues);

                                    Account account = Account.createNew();

                                    defaultCurrency = Utility.getPreferredDefaultCurrency(MainActivity.this);
                                    Log.d(LOG_TAG, defaultCurrency);
                                    account.setCurrency(Utility.getPreferredDefaultCurrency(MainActivity.this));
                                    arguments = new Bundle();
                                    arguments.putSerializable("item", account);

                                    intent = new Intent(MainActivity.this, AccountEditorActivity.class);
                                    intent.putExtras(arguments);
                                    startActivityForResult(intent, ACTIVITY_ACCOUNT);


                                    break;
                                case "payment_method":

                                    PaymentMethod paymentMethod = PaymentMethod.createNew();

                                    defaultCurrency = Utility.getPreferredDefaultCurrency(MainActivity.this);
                                    Log.d(LOG_TAG, defaultCurrency);
                                    paymentMethod.setCurrency(Utility.getPreferredDefaultCurrency(MainActivity.this));
                                    arguments = new Bundle();
                                    arguments.putSerializable("item", paymentMethod);

                                    intent = new Intent(MainActivity.this, PaymentMethodEditorActivity.class);
                                    intent.putExtras(arguments);
                                    startActivityForResult(intent, ACTIVITY_PAYMENT_METHOD);

                                    break;
                                default:
                                    Snackbar.make(view, f.getTag(), Snackbar.LENGTH_LONG)
                                            .setAction("Action", null).show();
                                    break;
                            }
                        }


                    }
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle extras = null;
        if (null != data)
            extras = data.getExtras();

        switch (requestCode) {
            case ACTIVITY_CONTRIBUTOR:

                if (resultCode == RESULT_OK) {

                }

                break;
            case ACTIVITY_CATEGORY:

                if (resultCode == RESULT_OK) {

                }

                break;

            case ACTIVITY_ACCOUNT:

                if (resultCode == RESULT_OK) {

                }

                break;
            case ACTIVITY_PAYMENT_METHOD:

                if (resultCode == RESULT_OK) {

                }

                break;


            default:
                break;
        }

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

    @Override
    public void onItemSelected(Contributor contributor) {

        Bundle arguments = new Bundle();
        arguments.putSerializable("item", contributor);

        Intent intent = new Intent(MainActivity.this, ContributorEditorActivity.class);
        intent.putExtras(arguments);
        startActivityForResult(intent, ACTIVITY_CONTRIBUTOR);

    }

    @Override
    public void onItemSelected(Account account) {

        Bundle arguments = new Bundle();
        arguments.putSerializable("item", account);

        Intent intent = new Intent(MainActivity.this, AccountEditorActivity.class);
        intent.putExtras(arguments);
        startActivityForResult(intent, ACTIVITY_ACCOUNT);

    }

    @Override
    public void onItemSelected(PaymentMethod paymentMethod) {

        Bundle arguments = new Bundle();
        arguments.putSerializable("item", paymentMethod);

        Intent intent = new Intent(MainActivity.this, PaymentMethodEditorActivity.class);
        intent.putExtras(arguments);
        startActivityForResult(intent, ACTIVITY_PAYMENT_METHOD);

    }

    @Override
    public void onItemSelected(Category category) {
        Bundle arguments = new Bundle();
        arguments.putSerializable("item", category);

        Intent intent = new Intent(MainActivity.this, CategoryEditorActivity.class);
        intent.putExtras(arguments);
        startActivityForResult(intent, ACTIVITY_CATEGORY);

    }
}
