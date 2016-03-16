package com.gagnon.mario.mr.incexp.app.core;

/**
 * Created by mario on 3/13/2016.
 */
public class ItemStateChangeEvent {

    private ObjectBase mItem;
    private Boolean mIsCancelled = false;

    public Boolean isCancelled() {
        return mIsCancelled;
    }

    public ObjectBase getItem() {
        return mItem;
    }

    public ItemStateChangeEvent(ObjectBase item, Boolean isCancelled){
        initialize(item, isCancelled);
    }

    public ItemStateChangeEvent(ObjectBase item){
        initialize(item, false);
    }

    private void initialize(ObjectBase item, Boolean isCancelled){
        if((!isCancelled) && item == null){
            throw new NullPointerException("Parameter item of type ObjectBase is mandatory.");
        }

        if((!isCancelled) && item.getId() == null && (!item.isNew())){
            throw new NullPointerException("Item id is null but item is not new.");
        }

        mItem = item;
        mIsCancelled = isCancelled;

    }
}
