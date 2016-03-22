package com.gagnon.mario.mr.incexp.app.payment_method;

import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.core.Tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PaymentMethod extends ObjectBase implements Serializable, Comparable<PaymentMethod> {

    private String mName;

    private String mCurrency;
    private Double mExchangeRate;
    private Boolean mIsClose;

    private List<Contributor> mContributors;

    private PaymentMethod() {

    }

    public static PaymentMethod create(Long id, String name, String currency, Double exchangeRate, Boolean isClose /*, List<Contributor> contributors*/) {

        PaymentMethod newInstance = new PaymentMethod();
        newInstance.mNew = false;
        newInstance.mDirty = false;
        newInstance.mId = id;
        newInstance.mName = name;
        newInstance.mCurrency = currency;
        newInstance.mExchangeRate = exchangeRate;
        newInstance.mIsClose = isClose;
//        newInstance.mContributors = contributors;

        return newInstance;
    }

    public static PaymentMethod createNew() {

        PaymentMethod newInstance = new PaymentMethod();
        newInstance.mNew = true;
        newInstance.mDirty = true;
        newInstance.mName = "";
        newInstance.mCurrency = "";
        newInstance.mExchangeRate = 1.0;
        newInstance.mContributors = new ArrayList<>();

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

    public String getCurrency() {
        return null == mCurrency ? "" : mCurrency;
    }

    public void setCurrency(String currency) {

        if (!mCurrency.equals(currency)) {
            mDirty = true;
            mCurrency = currency;
        }
    }

    public Double getExchangeRate() {
        return mExchangeRate;
    }

    public void setExchangeRate(Double exchangeRate){
        if(!mExchangeRate.equals(exchangeRate)){
            mDirty = true;
            mExchangeRate = exchangeRate;
        }
    }

    public Boolean getIsClose() {
        return mIsClose;
    }

    public void setIsClose(Boolean isClose) {
        mIsClose = isClose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentMethod)) return false;

        PaymentMethod that = (PaymentMethod) o;

        if (!mName.equals(that.mName)) return false;
        if (!mCurrency.equals(that.mCurrency)) return false;
        if (!mExchangeRate.equals(that.mExchangeRate)) return false;
        if (!mIsClose.equals(that.mIsClose)) return false;
        return !(mContributors != null ? !mContributors.equals(that.mContributors) : that.mContributors != null);

    }

    @Override
    public int hashCode() {
        int result = mName.hashCode();
        result = 31 * result + mCurrency.hashCode();
        result = 31 * result + mExchangeRate.hashCode();
        result = 31 * result + mIsClose.hashCode();
        result = 31 * result + (mContributors != null ? mContributors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("%1$s(%2$s)", getName(), getCurrency());
    }

    @Override
    public int compareTo(@NonNull PaymentMethod instanceToCompare) {
        return getName().compareToIgnoreCase(instanceToCompare.getName());
    }

    public String getContributorsForDisplay(){
        return Tools.join(mContributors, ",");
    }

    public void addContributor(Contributor contributor){
        mDirty = true;
        mContributors.add(contributor);
    }

    public void clearContributor(){
        mDirty = true;
        mContributors.clear();
    }

    public List<Contributor> getContributors(){
        return mContributors;
    }

}
