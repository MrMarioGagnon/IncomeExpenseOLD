package com.gagnon.mario.mr.incexp.app.contributor;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;

/**
 * {@link ContributorAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class ContributorAdapter extends CursorAdapter {

    private static final String LOG_TAG = ContributorAdapter.class.getSimpleName();

    public ContributorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.contributor_list_item;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(ContributorFragment.COL_NAME);
        viewHolder.textViewName.setText(name);

    }

    public static class ViewHolder {
        public final TextView textViewName;

        public ViewHolder(View view) {
            textViewName = (TextView) view.findViewById(R.id.textView_contributor_name);
        }
    }

}