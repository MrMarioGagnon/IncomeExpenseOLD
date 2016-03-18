package com.gagnon.mario.mr.incexp.app.core;

/**
 * Created by mario on 3/17/2016.
 */
public class ItemRepositorySynchronizerException extends Exception {

    private ItemRepositorySynchronizerAction mAction;

    public ItemRepositorySynchronizerException(String detailMessage, ItemRepositorySynchronizerAction action) {
        super(detailMessage);
        mAction = action;
    }

    public ItemRepositorySynchronizerException(String detailMessage, Throwable throwable, ItemRepositorySynchronizerAction action) {
        super(detailMessage, throwable);
        mAction = action;
    }

    public ItemRepositorySynchronizerException(Throwable throwable, ItemRepositorySynchronizerAction action) {
        super(throwable);
        mAction = action;
    }

    public ItemRepositorySynchronizerException(ItemRepositorySynchronizerAction action) {
        super();
        mAction = action;
    }

    public ItemRepositorySynchronizerAction getAction() {
        return mAction;
    }

    public void setAction(ItemRepositorySynchronizerAction action) {
        mAction = action;
    }
}
