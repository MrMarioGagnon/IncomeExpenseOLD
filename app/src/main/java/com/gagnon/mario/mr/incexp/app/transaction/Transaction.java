package com.gagnon.mario.mr.incexp.app.transaction;

import android.support.annotation.NonNull;

import com.gagnon.mario.mr.incexp.app.account.Account;
import com.gagnon.mario.mr.incexp.app.category.Category;
import com.gagnon.mario.mr.incexp.app.contributor.Contributor;
import com.gagnon.mario.mr.incexp.app.core.ObjectBase;
import com.gagnon.mario.mr.incexp.app.core.Tools;
import com.gagnon.mario.mr.incexp.app.payment_method.PaymentMethod;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Transaction extends ObjectBase implements Serializable, Comparable<Transaction> {

    private Account mAccount;
    private Category mCategory;
    private String mTransactionType;
    private String mDate;
    private Double mAmount;
    private String mCurrency;
    private Double mExchangeRate;
    private PaymentMethod mPaymentMethod;
    private String mNote;

    private Transaction() {

    }

    public static Transaction create(Long id, Account account, Category category, String transactionType, String date, Double amount, String currency, Double exchangeRate, PaymentMethod paymentMethod, String note) {

        Transaction newInstance = new Transaction();
        newInstance.mNew = false;
        newInstance.mDirty = false;
        newInstance.mId = id;
        newInstance.mAccount = account;
        newInstance.mCategory = category;
        newInstance.mTransactionType = transactionType;
        newInstance.mDate = date;
        newInstance.mAmount = amount;
        newInstance.mCurrency = currency;
        newInstance.mExchangeRate = exchangeRate;
        newInstance.mPaymentMethod = paymentMethod;
        newInstance.mNote = note;

        return newInstance;
    }

    public static Transaction createNew() {

        Transaction newInstance = new Transaction();
        newInstance.mNew = true;
        newInstance.mDirty = true;
        newInstance.mAccount = null;
        newInstance.mCategory = null;
        newInstance.mTransactionType = "-";
        newInstance.mDate = "";
        newInstance.mAmount = 0.0;
        newInstance.mCurrency = "";
        newInstance.mExchangeRate = 1.0;
        newInstance.mPaymentMethod = null;
        newInstance.mNote = "";

        return newInstance;

    }

    public Account getAccount() {
        return mAccount;
    }

    public void setAccount(Account account) {
        mAccount = account;
    }

    public Double getAmount() {
        return mAmount;
    }

    public void setAmount(Double amount) {
        mAmount = amount;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public String getCurrency() {
        return mCurrency;
    }

    public void setCurrency(String currency) {
        mCurrency = currency;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public Double getExchangeRate() {
        return mExchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        mExchangeRate = exchangeRate;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public String getTransactionType() {
        return mTransactionType;
    }

    public void setTransactionType(String transactionType) {
        mTransactionType = transactionType;
    }

    public PaymentMethod getPaymentMethod() {
        return mPaymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        mPaymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction)) return false;

        Transaction that = (Transaction) o;

        if (!mAccount.equals(that.mAccount)) return false;
        if (!mCategory.equals(that.mCategory)) return false;
        if (mTransactionType != that.mTransactionType) return false;
        if (!mDate.equals(that.mDate)) return false;
        if (!mAmount.equals(that.mAmount)) return false;
        if (!mCurrency.equals(that.mCurrency)) return false;
        if (!mExchangeRate.equals(that.mExchangeRate)) return false;
        if (!mPaymentMethod.equals(that.mPaymentMethod)) return false;
        return !(mNote != null ? !mNote.equals(that.mNote) : that.mNote != null);

    }

    @Override
    public int hashCode() {
        int result = mAccount.hashCode();
        result = 31 * result + mCategory.hashCode();
        result = 31 * result + mTransactionType.hashCode();
        result = 31 * result + mDate.hashCode();
        result = 31 * result + mAmount.hashCode();
        result = 31 * result + mCurrency.hashCode();
        result = 31 * result + mExchangeRate.hashCode();
        result = 31 * result + mPaymentMethod.hashCode();
        result = 31 * result + (mNote != null ? mNote.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(Transaction another) {
        // TODO implement later
        return 0;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "mTransactionType=" + mTransactionType +
                ", mDate='" + mDate + '\'' +
                ", mAmount=" + mAmount +
                ", mCurrency='" + mCurrency + '\'' +
                '}';
    }
}
