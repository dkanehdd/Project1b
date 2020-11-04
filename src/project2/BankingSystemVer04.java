package project2;

import java.util.InputMismatchException;
import java.util.Scanner;

import project2.ver04.AccountManager;
import project2.ver04.AutoSaverT;
import project2.ver04.MenuChoice;
import project2.ver04.MenuSelectException;
import project2.ver04.Puzzle;

public class BankingSystemVer04 {
	static Scanner s = new Scanner(System.in);
	public static int cnt=0;
	static void showMenu() {
		System.out.println("-------Menu-------");
		System.out.println("1.계좌개설");
		System.out.println("2.입  금");
		System.out.println("3.출  금");
		System.out.println("4.계좌정보출력");
		System.out.println("5.저장옵션");
		System.out.println("6.프로그램종료");
		System.out.println("7.3by3게임");
	}
	public static void main(String[] args) {
		
		Puzzle puzzle = new Puzzle();
		AutoSaverT ast = null;
		AccountManager ac = new AccountManager();
		ac.readAccount();
		 
		while(true) {
				
			showMenu();
			try {
				System.out.print("선택:");
				int select=s.nextInt();
				s.nextLine();
				if(select<1||select>7) {
					MenuSelectException mse = new MenuSelectException();
					throw mse;
				}
				if(select==MenuChoice.EXIT) {
					ac.saveAccount();
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
				case MenuChoice.AUTOSAVE:
					
					if(cnt==0)
					{ast = new AutoSaverT(ac);}
					ac.AutoSave(ast);
					
					break;
				case 7:
					puzzle.Puzzle3_3();break;
				}
			}
			catch (InputMismatchException e) {
				System.err.println("숫자가 아닙니다.");
				s.nextLine();
			}
			catch (MenuSelectException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
			}
		}
	}
	
	
}
