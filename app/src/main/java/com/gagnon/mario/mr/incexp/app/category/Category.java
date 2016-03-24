package com.gagnon.mario.mr.incexp.app.category;

import com.gagnon.mario.mr.incexp.app.core.ObjectBase;

import java.io.Serializable;
import java.util.Arrays;

public class Category extends ObjectBase implements Serializable {

    private Long mId;
    private String mName;
    private String[] mSubCategories;

    private Category() {
    }

    public static Category create(Long id, String name, String[] subCategories) {

        Category category = new Category();
        category.mNew = false;
        category.mDirty = false;
        category.mId = id;
        category.mName = name;
        category.mSubCategories = subCategories;

        return category;
    }

    public static Category createNew() {

        Category category = new Category();
        category.mNew = true;
        category.mDirty = true;

        return category;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (mId == null) {
            if (other.mId != null)
                return false;
        } else if (!mId.equals(other.mId))
            return false;
        if (mName == null) {
            if (other.mName != null)
                return false;
        } else if (!mName.equals(other.mName))
            return false;
        return Arrays.equals(mSubCategories, other.mSubCategories);
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {

        if (null == mName || !mName.equals(name)) {
            mName = name;
            mDirty = true;
        }

    }

    public String[] getSubCategories() {
        return mSubCategories;
    }

    public void setSubCategories(String[] subCategories) {

        if (null == mSubCategories
                || !Arrays.equals(mSubCategories, subCategories)) {
            mSubCategories = subCategories;
            mDirty = true;
        }

    }

    public String getSubCategoriesAsString() {

        StringBuilder sb = new StringBuilder();

        for (String subCategory : getSubCategories()) {

            if (sb.length() != 0) {
                sb.append("|");
            }
            sb.append(subCategory);

        }

        return sb.toString();
    }

    public String getSubCategory(int i) {

        if (i > getSubCategories().length - 1)
            return null;

        return getSubCategories()[i];

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mId == null) ? 0 : mId.hashCode());
        result = prime * result + ((mName == null) ? 0 : mName.hashCode());
        result = prime * result + Arrays.hashCode(mSubCategories);
        return result;
    }

    @Override
    public String toString() {
        return getName();
    }

}
