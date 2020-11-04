package project2.ver02;


class HighCreditAccount extends Account{
	
	private int interest, gi;
	private String grade;
	
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
		super.showAccount();
		System.out.println("기본이자>"+interest+"%");
		System.out.println("신용등급>"+grade.toUpperCase());
		System.out.println("-----------------");
	}
	
}
