package com.gagnon.mario.mr.incexp.app.account;

import com.gagnon.mario.mr.incexp.app.core.ObjectBase;

import java.io.Serializable;

public class Account extends ObjectBase implements Serializable, Comparable<Account> {

	// region Static Method

	public static Account create(Long id, String name, String currency, Boolean isClose) {

		Account newInstance = new Account();
		newInstance.mNew = false;
		newInstance.mDirty = false;
		newInstance.mId = id;
		newInstance.mName = name;
		newInstance.mCurrency = currency;
		newInstance.mIsClose = isClose;

		return newInstance;
	}

	public static Account createNew() {

		Account newInstance = new Account();
		newInstance.mNew = true;
		newInstance.mDirty = true;
		newInstance.mName = "";

		return newInstance;

	}

	// endregion Static Method

	// region Private Field

	private Long mId = null;
	private String mName;
	private String mCurrency;
	private Boolean mIsClose;

	// endregion Private Field

	// region Constructor

	private Account() {

	}

	// endregion Constructor

	// region Getter/Setter

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

		if (!mName.equals(name)) {
			mDirty = true;
			mName = name;
		}
	}

	public String getCurrency() {
		return mCurrency;
	}

	public void setCurrency(String currency) {
		mCurrency = currency;
	}

	public Boolean getIsClose() {
		return mIsClose;
	}

	public void setIsClose(Boolean isClose) {
		mIsClose = isClose;
	}
// endregion Getter/Setter

	// region Public Method


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Account)) return false;

		Account account = (Account) o;

		if (!mId.equals(account.mId)) return false;
		if (!mName.equals(account.mName)) return false;
		if (!mCurrency.equals(account.mCurrency)) return false;
		return mIsClose.equals(account.mIsClose);

	}

	@Override
	public int hashCode() {
		int result = mId.hashCode();
		result = 31 * result + mName.hashCode();
		result = 31 * result + mCurrency.hashCode();
		result = 31 * result + mIsClose.hashCode();
		return result;
	}

	@Override
	public String toString() {
		String toString = String.format("%1$s(%2$s)", getName(), getCurrency() );
		return toString;
	}

	@Override
	public int compareTo(Account instanceToCompare) {
		return getName().compareToIgnoreCase(instanceToCompare.getName());
	}

	// endregion Public Method
}