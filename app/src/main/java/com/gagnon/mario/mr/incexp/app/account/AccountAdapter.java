package com.gagnon.mario.mr.incexp.app.account;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.*;

/**
 * {@link AccountAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class AccountAdapter extends CursorAdapter {

    // region Public Static Class

    public static class ViewHolder {
        public final TextView textViewName;
        public final TextView textViewCurrency;

        public ViewHolder(View view) {
            textViewName = (TextView) view.findViewById(R.id.textView_account_name);
            textViewCurrency = (TextView) view.findViewById(R.id.textView_account_currency);
        }
    }

    // endregion Public Static Class

    // region Private Field

    private static final String LOG_TAG = AccountAdapter.class.getSimpleName();

    private static final int VIEW_TYPE_COUNT = 1;

    // endregion Private Field

    // region Constructor

    public AccountAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    // endregion Constructor

    // region Public Method

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = R.layout.account_list_item;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String name = cursor.getString(AccountFragment.COL_NAME);
        viewHolder.textViewName.setText(name);

        String currency = cursor.getString(AccountFragment.COL_CURRENCY);
        viewHolder.textViewCurrency.setText(currency);

    }

    // endregion Public Method

}