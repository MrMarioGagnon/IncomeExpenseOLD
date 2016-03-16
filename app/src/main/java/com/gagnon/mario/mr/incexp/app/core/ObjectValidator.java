package com.gagnon.mario.mr.incexp.app.core;

/**
 * Created by mario on 2/18/2016.
 */
public interface ObjectValidator {
    ValidationStatus Validate(ObjectBase objectToValidate) throws Exception;
}
