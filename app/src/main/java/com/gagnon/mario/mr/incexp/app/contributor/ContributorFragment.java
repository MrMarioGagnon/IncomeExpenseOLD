package com.gagnon.mario.mr.incexp.app.contributor;

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
public class ContributorFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    // region Static Field

    public static final int COL_NAME = 1;
    private static final String[] CONTRIBUTOR_COLUMNS = {
            IncomeExpenseContract.ContributorEntry._ID,
            IncomeExpenseContract.ContributorEntry.COLUMN_NAME
    };
    private static final String LOG_TAG = ContributorFragment.class.getSimpleName();
    private static final String SELECTED_KEY = "selected_position";
    private static final int CONTRIBUTOR_LOADER = 0;

    // endregion Private Static Field

    // region Private Field

    private ContributorAdapter mContributorAdapter;
    private int mPosition = ListView.INVALID_POSITION;
    private ListView mListView;

    // endregion Private Field

    // region Public Interface
    public ContributorFragment() {
        // Required empty public constructor
    }

    // endregion Public Interface

    // region Constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Add this line in order for this fragment to handle menu events.
        //setHasOptionsMenu(true);

    }

    // endregion Constructor

    // region Public Method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContributorAdapter = new ContributorAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_contributor, container, false);

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
        getLoaderManager().initLoader(CONTRIBUTOR_LOADER, null, this);
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

    private void setupListView(View v, LayoutInflater inflater, ViewGroup container) {

        mListView = (ListView) v.findViewById(R.id.listview_contributor);
        mListView.setAdapter(mContributorAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cursor cursor = (Cursor)adapterView.getItemAtPosition(position);
                if(null != cursor){

                    long id = cursor.getLong(  cursor.getColumnIndex(IncomeExpenseContract.ContributorEntry.COLUMN_ID)  );
                    String name = cursor.getString(cursor.getColumnIndex(IncomeExpenseContract.ContributorEntry.COLUMN_NAME));

                    Contributor contributor = Contributor.create(id, name);

                    ((ContributorFragment.OnItemSelectedListener)getActivity()).onItemSelected(contributor);

                }

                mPosition = position;
            }
        });

        mListView.setEmptyView(v.findViewById(R.id.textView_no_contributor));

    }
    // endregion Public Method

    // region Private Method

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // This is called when a new Loader needs to be created.  This
        // fragment only uses one loader, so we don't care about checking the id.

        // Sort order:  Ascending, by date.
        String sortOrder = IncomeExpenseContract.ContributorEntry.COLUMN_NAME + " ASC";

        Uri contributorUri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;

        return new CursorLoader(getActivity(),
                contributorUri,
                CONTRIBUTOR_COLUMNS,
                null,
                null,
                sortOrder);
    }

    // endregion Private Method

    // region LoaderManager.LoaderCallbacks Method

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mContributorAdapter.swapCursor(data);
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            mListView.smoothScrollToPosition(mPosition);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mContributorAdapter.swapCursor(null);
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface OnItemSelectedListener {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Contributor contributor);
    }

    // endregion LoaderManager.LoaderCallbacks Method

}