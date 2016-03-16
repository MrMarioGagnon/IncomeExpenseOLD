package com.gagnon.mario.mr.incexp.app.contributor;

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
public class ContributorValidator implements ObjectValidator {

    private final List<String> mNames;
    private final Map<Integer, String> mValidationMessages;
    public ContributorValidator(List<String> names, Map<Integer, String> validationMessages) {

        if (null == names) {
            throw new NullPointerException("Parameter names of type List<String> is mandatory.");
        }

        if (null == validationMessages) {
            throw new NullPointerException("Parameter validationMessages of type Map<Integer, String> is mandatory.");
        }

        mNames = names;
        mValidationMessages = validationMessages;

    }

    public static ContributorValidator create(Context context, List<String> names) {

        Map<Integer, String> messages = new HashMap<>();
        messages.put(R.string.validation_name_mandatory, context.getString(R.string.validation_name_mandatory));
        messages.put(R.string.validation_name_already_exists, context.getString(R.string.validation_name_already_exists));

        return new ContributorValidator(names, messages);
    }

    private boolean isNameExists(String name) {

        return mNames.contains(name.toUpperCase());

    }

    public ValidationStatus Validate(@NonNull ObjectBase objectToValidate) throws Exception {

        if (!(objectToValidate instanceof Contributor)) {
            throw new IllegalArgumentException("Parameter objectToValidate must be an instance of Contributor");
        }

        List<String> messages = new ArrayList<>();

        Contributor contributor = (Contributor) objectToValidate;
        String name = contributor.getName().trim();

        if (name.length() == 0) {
            messages.add(mValidationMessages.get(R.string.validation_name_mandatory));
        } else if (isNameExists(name)) {
            messages.add(mValidationMessages.get(R.string.validation_name_already_exists));
        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }
}
