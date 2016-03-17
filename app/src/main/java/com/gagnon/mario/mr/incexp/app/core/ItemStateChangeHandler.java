package com.gagnon.mario.mr.incexp.app.core;

import android.support.annotation.NonNull;

/**
 * Created by mario on 3/13/2016.
 */
public interface ItemStateChangeHandler {
    void addListener(@NonNull ItemStateChangeListener listener);
    void notifyListener(@NonNull ItemStateChangeEvent event);
}
