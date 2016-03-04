package com.gagnon.mario.mr.incexp.app.account;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gagnon.mario.mr.incexp.app.*;

/**
 * {@link AccountAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class AccountAdapter extends CursorAdapter {


    private static final String LOG_TAG = AccountAdapter.class.getSimpleName();


    // region Public Static Class
    private static final int VIEW_TYPE_COUNT = 1;

    // endregion Public Static Class

    // region Private Field

    public AccountAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = R.layout.account_list_item;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    // endregion Private Field

    // region Constructor

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        Long accountId = cursor.getLong(AccountFragment.COL_ID);

        String name = cursor.getString(AccountFragment.COL_NAME);
        viewHolder.textViewName.setText(name);

        String currency = cursor.getString(AccountFragment.COL_CURRENCY);
        viewHolder.textViewCurrency.setText(currency);

        viewHolder.imageButtonContributor.setTag(accountId);
    }

    // endregion Constructor

    // region Public Method

    public class ViewHolder {
        public final TextView textViewName;
        public final TextView textViewCurrency;
        public final ImageButton imageButtonContributor;

        public ViewHolder(View view) {
            textViewName = (TextView) view.findViewById(R.id.textView_account_name);
            textViewCurrency = (TextView) view.findViewById(R.id.textView_account_currency);
            imageButtonContributor = (ImageButton) view.findViewById(R.id.imageButton_contributor);
            imageButtonContributor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Long id = (Long) v.getTag();
                    Log.d(LOG_TAG, "onClick");
                    Toast.makeText(v.getContext(), id.toString(), Toast.LENGTH_SHORT);
                }
            });
        }
    }

    // endregion Public Method

}