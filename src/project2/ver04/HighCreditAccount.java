package project2.ver04;


class HighCreditAccount extends Account{
	

	int interest, gi;
	String grade;
	
	public HighCreditAccount(String account, String name, int money,
			int interest, String grade) {
		super(account, name, money);
		this.grade = grade;
		this.interest=interest;
		if(grade.equalsIgnoreCase("A")) {
			gi = interest+CustomSpecialRate.A;
		}else if(grade.equalsIgnoreCase("B")) {
			gi = interest+CustomSpecialRate.B;
		}else if(grade.equalsIgnoreCase("C")) {
			gi = interest+CustomSpecialRate.C;
		}
	}
	@Override
	public double getInterest() {
		return gi*0.01;
	}
	@Override
	public void showAccount() {
		System.out.println("-----------------");
		System.out.println("계좌번호>"+account);
		System.out.println("고객이름>"+name);
		System.out.println("잔고>"+money);
		System.out.println("기본이자>"+interest+"%");
		System.out.println("신용등급>"+grade.toUpperCase());
		System.out.println("-----------------");
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
