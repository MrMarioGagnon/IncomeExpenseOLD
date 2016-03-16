package com.gagnon.mario.mr.incexp.app.core;

/**
 * Created by mario on 3/13/2016.
 */
public interface ItemStateChangeHandler {
    void addListener(ItemStateChangeListener listener);
    void notifyListener(ItemStateChangeEvent event);
}
