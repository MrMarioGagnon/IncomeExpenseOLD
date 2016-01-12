package com.gagnon.mario.mr.incexp.app;

/**
 * Created by mario on 1/11/2016.
 */
public class DrawerItem {
    private String mTitle;
    private int mIcon;
    private boolean mSeparator;

    public DrawerItem(String title, int icon, boolean separator) {
        this.mIcon = icon;
        this.mTitle = title;
        this.mSeparator = separator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DrawerItem)) return false;

        DrawerItem that = (DrawerItem) o;

        if (mIcon != that.mIcon) return false;
        if (mSeparator != that.mSeparator) return false;
        return mTitle.equals(that.mTitle);

    }

    @Override
    public int hashCode() {
        int result = mTitle.hashCode();
        result = 31 * result + mIcon;
        result = 31 * result + (mSeparator ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DrawerItem{" +
                "mIcon=" + mIcon +
                ", mTitle='" + mTitle + '\'' +
                ", mSeparator=" + mSeparator +
                '}';
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        this.mIcon = icon;
    }

    public boolean isSeparator() {
        return mSeparator;
    }

    public void setSeparator(boolean separator) {
        this.mSeparator = separator;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
