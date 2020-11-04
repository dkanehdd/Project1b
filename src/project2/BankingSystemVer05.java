package project2;

import java.util.Scanner;

import project2.ver05.Account;
import project2.ver05.MenuChoice;

public class BankingSystemVer05{
	static Scanner s = new Scanner(System.in);
	static void showMenu() {
		System.out.println("-------Menu-------");
		System.out.println("1.계좌개설");
		System.out.println("2.입  금");
		System.out.println("3.출  금");
		System.out.println("4.계좌정보출력");
		System.out.println("5.프로그램 종료");
	}
	public static void main(String[] args) {
		Account ac = new Account(50);
		while(true) {
				
			showMenu();
			
			System.out.print("선택:");
			int select = s.nextInt();
			s.nextLine();
			if(select==MenuChoice.EXIT) {
				System.out.println("프로그램 종료");
				break;
			}
			switch (select) {
			case MenuChoice.MAKE:
				ac.makeAccount();break;
			case MenuChoice.DEPOSIT:
				ac.depositMoney();break;
			case MenuChoice.WITHDRAW:
				ac.withdrawMoney();break;
			case MenuChoice.INQUIRE:
				ac.showAccInfo();break;
			}
		}
	}
}
