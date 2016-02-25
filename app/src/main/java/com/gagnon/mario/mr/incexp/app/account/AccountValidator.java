package com.gagnon.mario.mr.incexp.app.account;

import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.core.ObjectValidator;
import com.gagnon.mario.mr.incexp.app.core.Tools;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/17/2016.
 */
public class AccountValidator implements ObjectValidator{

    // region Private Field
    private final List<String> mNames = new ArrayList<>();
    // endregion

    // region Private Method
    private boolean isNameExists(String name){

        return mNames.contains(name.toUpperCase());

    }

    // endregion

    // region Constructor

    public AccountValidator(List<String> names){

        if(null == names){
            throw new NullPointerException("Parameter names of type List<String> is mandatory.");
        }

        mNames.addAll(names);
    }
    // endregion

    // region Public Method

    public ValidationStatus Validate(ObjectBase objectToValidate) throws Exception {

        if(null == objectToValidate){
            throw new NullPointerException("Parameter objectToValidate of type ObjectBase is mandatory.");
        }

        if(!(objectToValidate instanceof Account)){
            throw new IllegalArgumentException("Parameter objectToValidate must be an instance of Account");
        }

        List<String> messages = new ArrayList<>();

        Account account = (Account) objectToValidate;
        String name = account.getName().trim();

         if (name.length() == 0) {
             messages.add("Name is mandatory");
         }else if(isNameExists(name)){
             messages.add("Name already exists");
         }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }

    // endregion
}
