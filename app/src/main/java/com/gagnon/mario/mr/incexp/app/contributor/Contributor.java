package com.gagnon.mario.mr.incexp.app.contributor;

import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.core.ObjectBase;

import java.io.Serializable;

public class Contributor extends ObjectBase implements Serializable, Comparable<Contributor> {

    private String mName;

    private Contributor() {

    }

    public static Contributor create(@NonNull Long id, String name) {

        Contributor newInstance = new Contributor();
        newInstance.mNew = false;
        newInstance.mDirty = false;
        newInstance.mId = id;
        newInstance.mName = name;

        return newInstance;
    }

    public static Contributor createNew() {

        Contributor newInstance = new Contributor();
        newInstance.mNew = true;
        newInstance.mDirty = true;
        newInstance.mName = "";

        return newInstance;

    }

    public String getName() {
        return null == mName ? "" : mName;
    }

    public void setName(String name) {

        if (!mName.equals(name)) {
            mDirty = true;
            mName = name;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contributor)) return false;

        Contributor that = (Contributor) o;

        return mId.equals(that.mId) && mName.equals(that.mName);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(@NonNull Contributor instanceToCompare) {
        return getName().compareToIgnoreCase(instanceToCompare.getName());
    }

}
