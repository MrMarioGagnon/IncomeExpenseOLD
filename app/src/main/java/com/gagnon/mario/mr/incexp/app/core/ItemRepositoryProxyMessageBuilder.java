package com.gagnon.mario.mr.incexp.app.core;

import android.content.Context;

import com.gagnon.mario.mr.incexp.app.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 3/15/2016.
 */
public class ItemRepositoryProxyMessageBuilder {

    public static Map<Integer, String> build(Context context) {
        Map<Integer, String> messages = new HashMap<>();
        messages.put(R.string.log_info_adding_item, context.getString(R.string.log_info_adding_item));
        messages.put(R.string.log_info_added_item, context.getString(R.string.log_info_added_item));
        messages.put(R.string.log_info_deleting_item, context.getString(R.string.log_info_deleting_item));
        messages.put(R.string.log_info_deleted_item, context.getString(R.string.log_info_deleted_item));
        messages.put(R.string.log_info_updating_item, context.getString(R.string.log_info_updating_item));
        messages.put(R.string.log_info_updated_item, context.getString(R.string.log_info_updated_item));

        return messages;
    }
}
