package project2;

import java.util.InputMismatchException;
import java.util.Scanner;

import project2.ver03.AccountManager;
import project2.ver03.MenuChoice;
import project2.ver03.MenuSelectException;


public class BankingSystemVer03{
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
		AccountManager ac = new AccountManager(50);
		while(true) {
				
			showMenu();
			try {
				System.out.print("선택:");
				int select = s.nextInt();
				if(select<1||select>5) {
					MenuSelectException mse = new MenuSelectException();
					throw mse;
				}
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
			catch (InputMismatchException e) {
				e.printStackTrace();
				s.nextLine();
			}
			catch (MenuSelectException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
}
