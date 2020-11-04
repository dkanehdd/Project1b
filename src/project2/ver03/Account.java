package project2.ver03;

public abstract class Account {
	private String account;
	private String name;
	private int money;
	
	public void showAccount() {
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}

