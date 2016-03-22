package com.gagnon.mario.mr.incexp.app.payment_method;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;

/**
 * Created by mario on 2/1/2016.
 */
public class PaymentMethodFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final int COL_ID = 0;
    public static final int COL_NAME = 1;
    public static final int COL_CURRENCY = 2;
    public static final int COL_EXCHANGE_RATE = 3;
    public static final int COL_CLOSE = 4;

    private static final String[] PAYMENT_METHOD_COLUMNS = {
            IncomeExpenseContract.PaymentMethodEntry.COLUMN_ID,
            IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME,
            IncomeExpenseContract.PaymentMethodEntry.COLUMN_CURRENCY,
            IncomeExpenseContract.PaymentMethodEntry.COLUMN_EXCHANGE_RATE,
            IncomeExpenseContract.PaymentMethodEntry.COLUMN_CLOSE
    };
    private static final String LOG_TAG = PaymentMethodFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";
    private static final int PAYMENT_METHOD_LOADER = 0;

    private PaymentMethodAdapter mPaymentMethodAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private ListView mListView;

    public PaymentMethodFragment() {
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

        mPaymentMethodAdapter = new PaymentMethodAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.payment_method_fragment, container, false);

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
        getLoaderManager().initLoader(PAYMENT_METHOD_LOADER, null, this);
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

        mListView = (ListView) v.findViewById(R.id.listview_payment_method);
        mListView.setAdapter(mPaymentMethodAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);
                if (null != cursor) {

                    Long id = cursor.getLong(cursor.getColumnIndex(IncomeExpenseContract.PaymentMethodEntry.COLUMN_ID));
                    String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME));
                    String currency = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.PaymentMethodEntry.COLUMN_CURRENCY));
                    Double exchangeRate = cursor.getDouble(cursor.getColumnIndex(IncomeExpenseContract.PaymentMethodEntry.COLUMN_EXCHANGE_RATE));
                    int close = cursor.getInt(cursor.getColumnIndex(IncomeExpenseContract.PaymentMethodEntry.COLUMN_CLOSE));
                    Boolean isClose = (close == 1);

                    //PaymentMethod paymentMethod = PaymentMethod.create(id, name, currency, isClose, IncomeExpenseRequestWrapper.getAccountContributors(getContext().getContentResolver(), id));
                    PaymentMethod paymentMethod = PaymentMethod.create(id, name, currency, exchangeRate, isClose);

                    ((PaymentMethodFragment.OnItemSelectedListener) getActivity()).onItemSelected(paymentMethod);

                }

                mPosition = position;
            }
        });

        mListView.setEmptyView(v.findViewById(R.id.textView_no_payment_method));

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // Sort order:  Ascending, by date.
        String sortOrder = IncomeExpenseContract.PaymentMethodEntry.COLUMN_NAME + " ASC";

        Uri paymentMethodUri = IncomeExpenseContract.PaymentMethodEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                paymentMethodUri,
                PAYMENT_METHOD_COLUMNS,
                null,
                null,
                sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mPaymentMethodAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mListView.smoothScrollToPosition(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mPaymentMethodAdapter.swapCursor(null);
    }

    // endregion LoaderManager.LoaderCallbacks Method

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface OnItemSelectedListener {
        void onItemSelected(PaymentMethod paymentMethod);
    }


}