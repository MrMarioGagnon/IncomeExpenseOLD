package com.gagnon.mario.mr.incexp.app.account;

import android.content.Context;

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

    public static AccountValidator create(Context context, List<String> names) {

        Map<Integer, String> messages = new HashMap<>();
        messages.put(R.string.validation_name_mandatory, context.getString(R.string.validation_name_mandatory));
        messages.put(R.string.validation_name_already_exists, context.getString(R.string.validation_name_already_exists));
        messages.put(R.string.validation_currency_mandatory, context.getString(R.string.validation_currency_mandatory));

        return new AccountValidator(names, messages);
    }

    // region Private Field
    private final List<String> mNames;
    private final Map<Integer, String> mValidationMessages;
    // endregion

    public AccountValidator(List<String> names, Map<Integer, String> validationMessages) {

        if (null == names) {
            throw new NullPointerException("Parameter names of type List<String> is mandatory.");
        }

        if (null == validationMessages) {
            throw new NullPointerException("Parameter validationMessages of type Map<Integer, String> is mandatory.");
        }

        mNames = names;
        mValidationMessages = validationMessages;
    }

    // endregion

    // region Constructor

    // region Private Method
    private boolean isNameExists(String name) {

        return mNames.contains(name.toUpperCase());

    }
    // endregion

    // region Public Method

    public ValidationStatus Validate(ObjectBase objectToValidate) throws Exception {

        if (null == objectToValidate) {
            throw new NullPointerException("Parameter objectToValidate of type ObjectBase is mandatory.");
        }

        if (!(objectToValidate instanceof Account)) {
            throw new IllegalArgumentException("Parameter objectToValidate must be an instance of Account");
        }

        List<String> messages = new ArrayList<>();

        Account account = (Account) objectToValidate;
        String name = account.getName().trim();
        String currency = account.getCurrency().trim();

        if (name.length() == 0) {
            messages.add( mValidationMessages.get(R.string.validation_name_mandatory));
        } else if (isNameExists(name)) {
            messages.add(mValidationMessages.get(R.string.validation_name_already_exists));
        }

        if(currency.length() == 0){
            messages.add(mValidationMessages.get(R.string.validation_currency_mandatory));
        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }

    // endregion
}
