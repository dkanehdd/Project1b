package project2.ver03;

class NomalAccount extends Account{
	private String account;
	private String name;
	private int money;

	private int interest;
	public NomalAccount(String account, String name, int money, int interest) {
		this.account=account;
		this.name = name;
		this.money= money;
		this.interest = interest;
	}
	@Override
	public void showAccount() {
		System.out.println("-----------------");
		System.out.println("계좌번호>"+account);
		System.out.println("고객이름>"+name);
		System.out.println("잔고>"+money);
		System.out.println("기본이자>"+interest+"%");
		System.out.println("-----------------");
	}
	@Override
	public double getInterest() {
		return interest*0.01;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public void setInterest(int interest) {
		this.interest = interest;
	}
	
}