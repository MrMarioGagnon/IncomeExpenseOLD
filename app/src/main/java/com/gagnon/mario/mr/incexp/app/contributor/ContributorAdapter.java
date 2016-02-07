package com.gagnon.mario.mr.incexp.app.contributor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;

/**
 * {@link ContributorAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class ContributorAdapter extends CursorAdapter {

    // region Public Static Class

    public static class ViewHolder {
        public final TextView textViewName;

        public ViewHolder(View view) {
            textViewName = (TextView) view.findViewById(R.id.textView_contributor_name);
        }
    }

    // endregion Public Static Class

    // region Private Field

    private static final String LOG_TAG = ContributorAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_COUNT = 1;

    // endregion Private Field

    // region Constructor

    public ContributorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    // endregion Constructor

    // region Public Method

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(ContributorFragment.COL_NAME);
        viewHolder.textViewName.setText(name);

    }

    // endregion Public Method

}