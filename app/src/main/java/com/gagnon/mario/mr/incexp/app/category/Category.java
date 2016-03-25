package com.gagnon.mario.mr.incexp.app.category;

import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.core.ObjectBase;

import java.io.Serializable;

public class Category extends ObjectBase implements Serializable, Comparable<Category> {

    private String mName;
    private String mGroup;

    private Category() {

    }

    public static Category create(@NonNull Long id, String name, String group) {

        Category newInstance = new Category();
        newInstance.mNew = false;
        newInstance.mDirty = false;
        newInstance.mId = id;
        newInstance.mName = name;
        newInstance.mGroup = group;

        return newInstance;
    }

    public static Category createNew() {

        Category newInstance = new Category();
        newInstance.mNew = true;
        newInstance.mDirty = true;
        newInstance.mName = "";
        newInstance.mGroup = "";

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

    public String getGroup() {
        return null == mGroup ? "" : mGroup;
    }

    public void setGroup(String group) {

        if (!mName.equals(group)) {
            mDirty = true;
            mGroup = group;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;

        Category category = (Category) o;

        if (!mName.equals(category.mName)) return false;
        return mGroup.equals(category.mGroup);

    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mGroup.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(@NonNull Category instanceToCompare) {
        return getName().compareToIgnoreCase(instanceToCompare.getName());
    }

}
