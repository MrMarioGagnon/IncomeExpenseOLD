package com.gagnon.mario.mr.incexp.app.core;

import com.gagnon.mario.mr.incexp.app.contributor.Contributor;

/**
 * Created by mario on 2/18/2016.
 */
public interface ObjectValidator {
    public ValidationStatus Validate(ObjectBase objectToValidate);
}
