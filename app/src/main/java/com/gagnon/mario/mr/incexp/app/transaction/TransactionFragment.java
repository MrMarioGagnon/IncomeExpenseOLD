package com.gagnon.mario.mr.incexp.app.transaction;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v4.app.LoaderManager;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.account.AccountAdapter;
import com.gagnon.mario.mr.incexp.app.account.AccountFragment;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;
import android.support.v4.content.CursorLoader;

/**
 * Created by mario on 2/1/2016.
 */
public class TransactionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int COL_ID = 0;
    public static final int COL_ACCOUNT_NAME = 1;
    public static final int COL_CATEGORY_NAME = 2;
    public static final int COL_TYPE = 3;
    public static final int COL_DATE = 4;
    public static final int COL_AMOUNT = 5;
    public static final int COL_CURRENCY = 6;
    public static final int COL_PAYMENT_METHOD_NAME = 7;

    private static final String[] TRANSACTION_COLUMNS = {
            IncomeExpenseContract.TransactionEntry.TABLE_NAME + "." + IncomeExpenseContract.TransactionEntry.COLUMN_ID,
            IncomeExpenseContract.AccountEntry.TABLE_NAME + "." + IncomeExpenseContract.AccountEntry.COLUMN_NAME,
            IncomeExpenseContract.CategoryEntry.TABLE_NAME+ "." + IncomeExpenseContract.CategoryEntry.COLUMN_NAME,
            IncomeExpenseContract.TransactionEntry.TABLE_NAME + "." + IncomeExpenseContract.TransactionEntry.COLUMN_TYPE,
            IncomeExpenseContract.TransactionEntry.TABLE_NAME + "." + IncomeExpenseContract.TransactionEntry.COLUMN_DATE,
            IncomeExpenseContract.TransactionEntry.TABLE_NAME + "." + IncomeExpenseContract.TransactionEntry.COLUMN_AMOUNT,
            IncomeExpenseContract.TransactionEntry.TABLE_NAME + "." + IncomeExpenseContract.TransactionEntry.COLUMN_CURRENCY,
            IncomeExpenseContract.PaymentMethodEntry.TABLE_NAME + "." + IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME
    };
    private static final String LOG_TAG = AccountFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";
    private static final int TRANSACTION_LOADER = 0;

    private TransactionAdapter mTransactionAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private ListView mListView;

    private Long mAccountId;

    public TransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(null == bundle){
            throw new NullPointerException("Bundle");
        }
        mAccountId = bundle.getLong("AccountId");
        Log.d(LOG_TAG, "In create view " + mAccountId.toString());

        mTransactionAdapter = new TransactionAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.transaction_fragment, container, false);

        setupListView(rootView, inflater, container);

        // If there's instance state, mine it for useful information.
        // The end-goal here is that the user never knows that turning their device sideways
        // does crazy lifecycle related things.  It should feel like some stuff stretched out,
        // or magically appeared to take advantage of room, but data or place in the app was never
        // actually *lost*.
        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(TRANSACTION_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    private void setupListView(View v, final LayoutInflater inflater, ViewGroup container) {

        mListView = (ListView) v.findViewById(R.id.listview_account);
        mListView.setAdapter(mTransactionAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (null != cursor) {

                    Long id = cursor.getLong(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_NAME));
                    String currency = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_CURRENCY));
                    int close = cursor.getInt(cursor.getColumnIndex(IncomeExpenseContract.AccountEntry.COLUMN_CLOSE));
                    Boolean isClose = (close == 1);

//                    Account account = Account.create(id, name,
//                            currency,
//                            isClose,
//                            IncomeExpenseRequestWrapper.getAccountContributors(getContext().getContentResolver(),id),
//                            IncomeExpenseRequestWrapper.getAccountCategories(getContext().getContentResolver(),id));

                    ((AccountFragment.OnItemSelectedListener) getActivity()).onItemSelected(null);

                }

                mPosition = position;
            }
        });

        mListView.setEmptyView(v.findViewById(R.id.textView_no_account));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // Sort order:  Ascending, by date.
        String sortOrder = IncomeExpenseContract.AccountEntry.COLUMN_NAME + " ASC";

        Uri transactionUri = IncomeExpenseContract.TransactionEntry.CONTENT_URI;
        String selection = IncomeExpenseContract.TransactionEntry.TABLE_NAME + "." + IncomeExpenseContract.TransactionEntry.COLUMN_ACCOUNT_ID + "=?";
        String[] selectionArgs = new String[] { mAccountId.toString() };

        return new CursorLoader(getActivity(),
                transactionUri,
                TRANSACTION_COLUMNS,
                selection,
                selectionArgs,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mTransactionAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mListView.smoothScrollToPosition(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mTransactionAdapter.swapCursor(null);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface OnItemSelectedListener {
        void onItemSelected(Transaction transaction);
    }

}