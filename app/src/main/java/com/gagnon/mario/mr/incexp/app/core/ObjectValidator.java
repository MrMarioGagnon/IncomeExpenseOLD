package com.gagnon.mario.mr.incexp.app.core;

import android.support.annotation.NonNull;

/**
 * Created by mario on 2/18/2016.
 */
public interface ObjectValidator {
    ValidationStatus Validate(@NonNull ObjectBase objectToValidate);
}
