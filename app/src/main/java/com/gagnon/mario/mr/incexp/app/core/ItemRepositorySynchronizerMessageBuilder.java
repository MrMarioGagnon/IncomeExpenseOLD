package com.gagnon.mario.mr.incexp.app.core;

import android.content.Context;

import com.gagnon.mario.mr.incexp.app.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 3/15/2016.
 */
public class ItemRepositorySynchronizerMessageBuilder {

    public static Map<Integer, String> build(Context context, String objectType) {

        Map<Integer, String> messages = new HashMap<>();

        switch (objectType) {
            case "AccountRepositorySynchronizer":
                messages.put(R.string.log_info_adding_item, context.getString(R.string.log_info_adding_item));
                messages.put(R.string.log_info_added_item, context.getString(R.string.log_info_added_item));
                messages.put(R.string.log_info_deleting_item, context.getString(R.string.log_info_deleting_item));
                messages.put(R.string.log_info_deleted_item, context.getString(R.string.log_info_deleted_item));
                messages.put(R.string.log_info_updating_item, context.getString(R.string.log_info_updating_item));
                messages.put(R.string.log_info_updated_item, context.getString(R.string.log_info_updated_item));
                messages.put(R.string.log_info_number_deleted_associated_contributor, context.getString(R.string.log_info_number_deleted_associated_contributor));
                messages.put(R.string.log_error_adding_item, context.getString(R.string.log_error_adding_item));
                messages.put(R.string.log_error_deleting_item, context.getString(R.string.log_error_deleting_item));
                messages.put(R.string.log_error_updating_item, context.getString(R.string.log_error_updating_item));
                messages.put(R.string.log_info_number_added_associated_contributor, context.getString(R.string.log_info_number_added_associated_contributor));
                break;
            case "PaymentMethodRepositorySynchronizer":
                messages.put(R.string.log_info_adding_item, context.getString(R.string.log_info_adding_item));
                messages.put(R.string.log_info_added_item, context.getString(R.string.log_info_added_item));
                messages.put(R.string.log_info_deleting_item, context.getString(R.string.log_info_deleting_item));
                messages.put(R.string.log_info_deleted_item, context.getString(R.string.log_info_deleted_item));
                messages.put(R.string.log_info_updating_item, context.getString(R.string.log_info_updating_item));
                messages.put(R.string.log_info_updated_item, context.getString(R.string.log_info_updated_item));
                messages.put(R.string.log_info_number_deleted_associated_contributor, context.getString(R.string.log_info_number_deleted_associated_contributor));
                messages.put(R.string.log_error_adding_item, context.getString(R.string.log_error_adding_item));
                messages.put(R.string.log_error_deleting_item, context.getString(R.string.log_error_deleting_item));
                messages.put(R.string.log_error_updating_item, context.getString(R.string.log_error_updating_item));
                messages.put(R.string.log_info_number_added_associated_contributor, context.getString(R.string.log_info_number_added_associated_contributor));
                break;
            case "ContributorRepositorySynchronizer":
                messages.put(R.string.log_info_adding_item, context.getString(R.string.log_info_adding_item));
                messages.put(R.string.log_info_added_item, context.getString(R.string.log_info_added_item));
                messages.put(R.string.log_info_deleting_item, context.getString(R.string.log_info_deleting_item));
                messages.put(R.string.log_info_deleted_item, context.getString(R.string.log_info_deleted_item));
                messages.put(R.string.log_info_updating_item, context.getString(R.string.log_info_updating_item));
                messages.put(R.string.log_info_updated_item, context.getString(R.string.log_info_updated_item));
                break;
            case "CategoryRepositorySynchronizer":
                messages.put(R.string.log_info_adding_item, context.getString(R.string.log_info_adding_item));
                messages.put(R.string.log_info_added_item, context.getString(R.string.log_info_added_item));
                messages.put(R.string.log_info_deleting_item, context.getString(R.string.log_info_deleting_item));
                messages.put(R.string.log_info_deleted_item, context.getString(R.string.log_info_deleted_item));
                messages.put(R.string.log_info_updating_item, context.getString(R.string.log_info_updating_item));
                messages.put(R.string.log_info_updated_item, context.getString(R.string.log_info_updated_item));
                break;
        }
        return messages;
    }
}
