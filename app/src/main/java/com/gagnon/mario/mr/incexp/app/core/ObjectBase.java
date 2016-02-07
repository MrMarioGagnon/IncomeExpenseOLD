package com.gagnon.mario.mr.incexp.app.core;

import java.io.Serializable;

public abstract class ObjectBase implements Serializable {

    protected boolean mDirty;
    protected boolean mDead = false;
    protected boolean mNew;

    public boolean isDead() {
        return mDead;
    }

    public void setDead(boolean dead) {
        mDead = dead;
        mDirty = dead;
    }

    public boolean isDirty() {
        return mDirty;
    }

    public boolean isNew() {
        return mNew;
    }

    public void setNew(boolean new_) {
        mNew = new_;
        mDirty = mNew;
    }

}
