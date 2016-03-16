package com.gagnon.mario.mr.incexp.app.core;

import android.content.Context;
import android.net.Uri;

import com.gagnon.mario.mr.incexp.app.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mario on 3/15/2016.
 */
public interface ItemRepositoryProxy {

//    public static ContributorRepositoryProxy create(Context context, Uri itemUri, String itemType) {
//
//        if (context == null) {
//            throw new NullPointerException("Parameter context of type Context is mandatory.");
//        }
//
//        if(itemUri == null){
//            throw new NullPointerException("Parameter itemUri of type Uri is mandatory.");
//        }
//
//        if(itemType == null){
//            throw new NullPointerException("Parameter itemType of type String is mandatory.");
//        }
//
//        Map<Integer, String> messages = new HashMap<>();
//        messages.put(R.string.log_info_adding_item, context.getString(R.string.log_info_adding_item));
//        messages.put(R.string.log_info_added_item, context.getString(R.string.log_info_added_item));
//        messages.put(R.string.log_info_deleting_item, context.getString(R.string.log_info_deleting_item));
//        messages.put(R.string.log_info_deleted_item, context.getString(R.string.log_info_deleted_item));
//        messages.put(R.string.log_info_updating_item, context.getString(R.string.log_info_updating_item));
//        messages.put(R.string.log_info_updated_item, context.getString(R.string.log_info_updated_item));
//
//        return new ContributorRepositoryProxy(context.getContentResolver(), itemUri, messages);
//    }

    public ObjectBase Save(ObjectBase item);
}
