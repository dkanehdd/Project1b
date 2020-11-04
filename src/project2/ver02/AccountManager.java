package project2.ver02;

import java.util.Scanner;

public class AccountManager {
	
	static Scanner s = new Scanner(System.in);
	Account[] ac;
	int acCnt;
	
	public AccountManager(int num) {
		ac = new Account[num];
		acCnt=0;
	}
	public void makeAccount() {
		String account, name;
		String grade;
		int select,interest, money;
		System.out.println("***신규계좌개설***");
		System.out.println("-----계좌선택-----");
		System.out.println("1.보통계좌");
		System.out.println("2.신용신뢰계좌");
		System.out.print("선택:");
		if(acCnt>0) {
			s.nextLine();
		}
		select = s.nextInt();
		if(select==1) {
			s.nextLine();
			System.out.print("계좌번호:");	account = s.nextLine();
			System.out.print("고객이름:");	name = s.nextLine();
			System.out.print("잔고:");money = s.nextInt();
			System.out.print("기본이자%(정수형태로입력):");interest=s.nextInt();
			ac[acCnt] = new NomalAccount(account, name, money, interest);
			acCnt++;
		}else if(select==2) {
			s.nextLine();
			System.out.print("계좌번호:");	account = s.nextLine();
			System.out.print("고객이름:");	name = s.nextLine();
			System.out.print("잔고:");money = s.nextInt();
			System.out.print("기본이자%(정수형태로입력):");interest=s.nextInt();
			s.nextLine();
			System.out.print("신용등급(A,B,C등급):");grade=s.nextLine();
			ac[acCnt] = new 
					HighCreditAccount(account, name, money, interest,grade);
			acCnt++;
		}
		System.out.println(acCnt);
		System.out.println("계좌개설이 완료되었습니다.");
	}
	
	public void showAccInfo() {
		for(int i=0 ; i<acCnt ; i++) {
			ac[i].showAccount();
		}
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
	}
	public void depositMoney() {
		System.out.println("***입   금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요");
		s.nextLine();
		System.out.print("계좌번호:");
		String ant = s.nextLine();
		System.out.print("입금액:");
		int dpMoney = s.nextInt();
		for(int i=0 ; i<acCnt ; i++) {
			if(ant.equals(ac[i].getAccount())) {
				double d = ac[i].getInterest();
				System.out.println(d);
				ac[i].setMoney(dpMoney+(int)(d*ac[i].getMoney())
							+ac[i].getMoney());
			}
		}
	}
	public void withdrawMoney() {
		System.out.println("***출   금***");
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		s.nextLine();
		System.out.print("계좌번호:");
		String ant = s.nextLine();
		System.out.print("출금액:");
		int dpMoney = s.nextInt();
		for(int i=0 ; i<acCnt ; i++) {
			if(ant.equals(ac[i].getAccount())) {
				ac[i].setMoney(ac[i].getMoney()-dpMoney);
			}
		}
	}
}
