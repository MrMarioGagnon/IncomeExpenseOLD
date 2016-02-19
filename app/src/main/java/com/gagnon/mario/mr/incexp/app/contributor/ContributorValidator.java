package com.gagnon.mario.mr.incexp.app.contributor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.Tools;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;
import com.gagnon.mario.mr.incexp.app.data.IncomeExpenseContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/17/2016.
 */
public class ContributorValidator implements ObjectValidator{

    // region Private Field
    private ContentResolver mContentResolver;
    // endregion

    // region Private Method
    private boolean isNameExists(String name){

        Uri contributorUri = IncomeExpenseContract.ContributorEntry.CONTENT_URI;

        String selection =IncomeExpenseContract.ContributorEntry.COLUMN_NAME + "=?";

        Cursor cursor = null;
        boolean asRows = false;
        try {
            cursor = mContentResolver.query(contributorUri, new String[]{IncomeExpenseContract.ContributorEntry.COLUMN_ID}, selection, new String[]{name}, null);
            asRows = cursor.moveToFirst();
        }finally{
            if(null != cursor) {
                cursor.close();
            }
        }

        return asRows;
    }

    // endregion

    // region Constructor

    public ContributorValidator(ContentResolver contentResolver){
        mContentResolver = contentResolver;
    }
    // endregion

    // region Public Method

    public ValidationStatus Validate(ObjectBase objectToValidate){

        List<String> messages = new ArrayList<>();

        if(null == objectToValidate) {
            messages.add("Null contributor");
        }else if(! (objectToValidate instanceof Contributor)){
            messages.add("Not a contributor");
        }else {

            Contributor contributor = (Contributor) objectToValidate;
            String name = contributor.getName().trim();

            if (name.length() == 0) {
                messages.add("Name is mandatory");
            }else if(isNameExists(name)){
                messages.add("Name already exists");
            }

        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }

    // endregion
}
