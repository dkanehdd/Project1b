package project2.ver04;

import java.io.Serializable;

public abstract class Account implements Serializable {
	public String account;
	public String name;
	public int money;
	
	Account(String account,String name, int money){
		this.account = account;
		this.money = money;
		this.name = name;
	}
	public void showAccount() {
	}
	public String getAccount() {
		return account;
	}
	public String getName() {
		return name;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public double getInterest() {
		return 0;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Account other = (Account) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		return true;
	}
}

