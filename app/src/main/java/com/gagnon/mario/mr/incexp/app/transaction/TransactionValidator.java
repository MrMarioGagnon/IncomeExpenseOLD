package com.gagnon.mario.mr.incexp.app.transaction;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.Tools;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mario on 2/17/2016.
 */
public class TransactionValidator implements ObjectValidator {

    private final Map<Integer, String> mValidationMessages;

    public TransactionValidator(@NonNull Map<Integer, String> validationMessages) {
        mValidationMessages = validationMessages;
    }

    public static TransactionValidator create(Context context) {

        Map<Integer, String> messages = new HashMap<>();
        messages.put(R.string.validation_name_mandatory, context.getString(R.string.validation_name_mandatory));
        messages.put(R.string.validation_name_already_exists, context.getString(R.string.validation_name_already_exists));
        messages.put(R.string.validation_currency_mandatory, context.getString(R.string.validation_currency_mandatory));
        messages.put(R.string.validation_contributors_mandatory, context.getString(R.string.validation_contributors_mandatory));
        messages.put(R.string.validation_categories_mandatory, context.getString(R.string.validation_categories_mandatory));

        return new TransactionValidator(messages);
    }

    public ValidationStatus Validate(@NonNull ObjectBase objectToValidate){

        List<String> messages = new ArrayList<>();

        if (!(objectToValidate instanceof Transaction)) {
            return ValidationStatus.create("Wrong object type.");
        }

        Transaction transaction = (Transaction) objectToValidate;
        String currency = transaction.getCurrency().trim();

        if (currency.length() == 0) {
            messages.add(mValidationMessages.get(R.string.validation_currency_mandatory));
        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }

}
