package project2.ver01;

import java.util.Scanner;

public class Account {
	
	static Scanner s = new Scanner(System.in);
	String account="";
	String name="";
	int money=0;
	Account[] ac;
	int acCnt;
	
	public Account(String account, String name, int money) {
		this.account = account;
		this.name = name;
		this.money = money;
	}
	public Account(int num) {
		ac = new Account[num];
		acCnt=0;
	}
	public void makeAccount() {
		System.out.println("***신규계좌개설***");
		if(acCnt>0) {
			s.nextLine();
		}
		System.out.print("계좌번호:");
		String account = s.nextLine();
		System.out.print("고객이름:");
		String name = s.nextLine();
		System.out.print("잔고:");
		int money = s.nextInt();
		ac[acCnt++]=new Account(account, name, money);
		System.out.println("계좌개설이 완료되었습니다.");
	}
	
	public void showAccInfo() {
		for(int i=0 ; i<acCnt ; i++) {
			System.out.println("-----------------");
			System.out.println("계좌번호:"+ac[i].account);
			System.out.println("고객이름:"+ac[i].name);
			System.out.println("잔고:"+ac[i].money);
			System.out.println("-----------------");
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
			if(ant.equals(ac[i].account)) {
				ac[i].money+=dpMoney;
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
			if(ant.equals(ac[i].account)) {
				ac[i].money-=dpMoney;
			}
		}
	}
}
