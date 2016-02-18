package com.gagnon.mario.mr.incexp.app.contributor;

import com.gagnon.mario.mr.incexp.app.core.Tools;
import com.gagnon.mario.mr.incexp.app.core.ValidationStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mario on 2/17/2016.
 */
public class ContributorValidator {

    public static ValidationStatus Validate(Contributor contributor){

        List<String> messages = new ArrayList<>();

        if(null == contributor){
            messages.add("Null contributor");
        }else{
            if(contributor.getName().trim().length() == 0){
                messages.add("Name is mandatory");
            }
        }

        return ValidationStatus.create(Tools.join(messages, "\n"));
    }

}
