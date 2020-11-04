package project2.ver02;

public class Account {
	
	private String account;
	private String name;
	private int money;
	
	public Account(String account, String name, int money) {
		this.account = account;
		this.name = name;
		this.money = money;
	}
	public void showAccount() {
		System.out.println("-----------------");
		System.out.println("계좌번호>"+account);
		System.out.println("고객이름>"+name);
		System.out.println("잔고>"+money);
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

