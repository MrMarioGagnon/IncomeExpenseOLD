package com.gagnon.mario.mr.incexp.app.category;

import android.content.Context;
import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.R;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
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
public class CategoryValidator implements ObjectValidator {

    private final List<String> mNames;
    private final Map<Integer, String> mValidationMessages;
    public CategoryValidator(@NonNull List<String> names, @NonNull Map<Integer, String> validationMessages) {

        mNames = names;
        mValidationMessages = validationMessages;

    }

    public static CategoryValidator create(@NonNull Context context, @NonNull List<String> names) {

        Map<Integer, String> messages = new HashMap<>();
        messages.put(R.string.validation_name_mandatory, context.getString(R.string.validation_name_mandatory));
        messages.put(R.string.validation_name_already_exists, context.getString(R.string.validation_name_already_exists));
        messages.put(R.string.validation_group_mandatory, context.getString(R.string.validation_group_mandatory));

        return new CategoryValidator(names, messages);
    }

    private boolean isNameExists(@NonNull String name) {

        return mNames.contains(name.toUpperCase());

    }

    public ValidationStatus Validate(@NonNull ObjectBase objectToValidate) {

        if (!(objectToValidate instanceof Category)) {
            return ValidationStatus.create("Wrong object type.");
        }

        List<String> messages = new ArrayList<>();

        Category category = (Category) objectToValidate;
        String name = category.getName().trim();
        String group = category.getGroup().trim();

        if (name.length() == 0) {
            messages.add(mValidationMessages.get(R.string.validation_name_mandatory));
        } else if (isNameExists(name)) {
            messages.add(mValidationMessages.get(R.string.validation_name_already_exists));
        }else if (group.length() == 0) {
            messages.add(mValidationMessages.get(R.string.validation_group_mandatory));
        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }
}
