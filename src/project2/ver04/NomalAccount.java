package project2.ver04;

class NomalAccount extends Account{
	
	int interest;
	public NomalAccount(String account, String name, int money, int interest) {
		super(account, name, money);
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
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public String getName() {
		return name;
	}
 

	
}