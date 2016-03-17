package com.gagnon.mario.mr.incexp.app.account;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.R;
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
public class AccountValidator implements ObjectValidator {

    private final List<String> mNames;
    private final Map<Integer, String> mValidationMessages;

    public AccountValidator(@NonNull List<String> names, @NonNull Map<Integer, String> validationMessages) {
        mNames = names;
        mValidationMessages = validationMessages;
    }

    public static AccountValidator create(Context context, List<String> names) {

        Map<Integer, String> messages = new HashMap<>();
        messages.put(R.string.validation_name_mandatory, context.getString(R.string.validation_name_mandatory));
        messages.put(R.string.validation_name_already_exists, context.getString(R.string.validation_name_already_exists));
        messages.put(R.string.validation_currency_mandatory, context.getString(R.string.validation_currency_mandatory));
        messages.put(R.string.validation_contributors_mandatory, context.getString(R.string.validation_contributors_mandatory));

        return new AccountValidator(names, messages);
    }

    private boolean isNameExists(String name) {

        return mNames.contains(name.toUpperCase());

    }

    public ValidationStatus Validate(@NonNull ObjectBase objectToValidate){

        List<String> messages = new ArrayList<>();

        if (!(objectToValidate instanceof Account)) {
            return ValidationStatus.create("Wrong object type.");
        }

        Account account = (Account) objectToValidate;
        String name = account.getName().trim();
        String currency = account.getCurrency().trim();

        if (name.length() == 0) {
            messages.add(mValidationMessages.get(R.string.validation_name_mandatory));
        } else if (isNameExists(name)) {
            messages.add(mValidationMessages.get(R.string.validation_name_already_exists));
        }

        if (currency.length() == 0) {
            messages.add(mValidationMessages.get(R.string.validation_currency_mandatory));
        }

        if (account.getContributors().size() == 0) {
            messages.add((mValidationMessages.get(R.string.validation_contributors_mandatory)));
        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }

}
