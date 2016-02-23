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

	public String getName() {
		return mName;
	}


	public void setId(Long id) {
		mId = id;
	}

	public void setName(String name) {

		if (!mName.equals(name)) {
			mDirty = true;
			mName = name;
		}
	}

	// endregion Getter/Setter

	// region Public Method

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Account)) return false;

		Account that = (Account) o;

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
	public int compareTo(Account instanceToCompare) {
		return getName().compareToIgnoreCase(instanceToCompare.getName());
	}

	// endregion Public Method
}
