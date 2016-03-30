package com.gagnon.mario.mr.incexp.app.transaction;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.account.AccountFragment;

/**
 * {@link TransactionAdapter} exposes a list of weather forecasts
 * from a {@link Cursor} to a {@link android.widget.ListView}.
 */
public class TransactionAdapter extends CursorAdapter {


    private static final String LOG_TAG = TransactionAdapter.class.getSimpleName();

    public TransactionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.transaction_list_item;

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        String type = cursor.getString(TransactionFragment.COL_TYPE);
        viewHolder.textViewType.setText(type);
        String date = cursor.getString(TransactionFragment.COL_DATE);
        viewHolder.textViewDate.setText(date);
        String currency = cursor.getString(TransactionFragment.COL_CURRENCY);
        viewHolder.textViewCurrency.setText(currency);
        Double amount = cursor.getDouble(TransactionFragment.COL_AMOUNT);
        viewHolder.textViewDate.setText(amount.toString());

    }

    public class ViewHolder {
        public final TextView textViewType;
        public final TextView textViewDate;
        public final TextView textViewCurrency;
        public final TextView textViewAmount;

        public ViewHolder(View view) {
            textViewType = (TextView) view.findViewById(R.id.textView_type);
            textViewDate = (TextView) view.findViewById(R.id.textView_date);
            textViewCurrency = (TextView) view.findViewById(R.id.textView_currency);
            textViewAmount = (TextView) view.findViewById(R.id.textView_amount);
        }
    }

}