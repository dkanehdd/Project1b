package project2.ver02;

class NomalAccount extends Account{
	private int interest;
	public NomalAccount(String account, String name, int money, int interest) {
		super(account, name, money);
		this.interest = interest;
	}
	@Override
	public void showAccount() {
		super.showAccount();
		System.out.println("기본이자>"+interest+"%");
		System.out.println("-----------------");
	}
	@Override
	public double getInterest() {
		return interest*0.01;
	}
	
}