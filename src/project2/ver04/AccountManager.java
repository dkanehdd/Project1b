package project2.ver04;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

import project2.BankingSystemVer04;

public class AccountManager{
	
	static Scanner s = new Scanner(System.in);
	Account[] ac;
	int acCnt;int threadCnt=0;
	HashSet<Account> hashSet;
	
	public AccountManager() {
		hashSet = new HashSet<Account>();
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
		System.out.println("선택:");
		select = s.nextInt();
		s.nextLine();
		if(select==1) {
			account="";
			System.out.print("계좌번호:");	account = s.nextLine();
			System.out.print("고객이름:");	name = s.nextLine();
			System.out.print("잔고:");money = s.nextInt();
			s.nextLine();
			System.out.print("기본이자%(정수형태로입력):");interest=s.nextInt();
			
			Account acc1 = new NomalAccount(account, name, money, interest);
			if(hashSet.add(acc1)==false) {
				System.out.println("중복계좌발견됨. 덮어쓸까요?(Y or N)");
				String y = s.next();
				if(y.equalsIgnoreCase("y")) {
					hashSet.remove(acc1);
				}else if(y.equalsIgnoreCase("n")) {
					System.out.println("계좌개설 취소");
					return;
				}
			}
			hashSet.add(acc1);
			System.out.println("계좌개설이 완료되었습니다.");
			
			
		}else if(select==2) {
			account="";
			System.out.print("계좌번호:");	account = s.nextLine();
			System.out.print("고객이름:");	name = s.nextLine();
			System.out.print("잔고:");money = s.nextInt();
			s.nextLine();
			System.out.print("기본이자%(정수형태로입력):");interest=s.nextInt();
			s.nextLine();
			System.out.print("신용등급(A,B,C등급):");grade=s.next();
			Account acc1 = new HighCreditAccount(account, name, money, interest,grade);
			if(hashSet.add(acc1)==false) {
				System.out.println("중복계좌발견됨. 덮어쓸까요?(Y or N)");
				String y = s.next();
				if(y.equalsIgnoreCase("y")) {
					hashSet.remove(acc1);
				}else if(y.equalsIgnoreCase("n")) {
					System.out.println("계좌개설 취소");
					return;
				}
			}
			hashSet.add(acc1);
			System.out.println("계좌개설이 완료되었습니다.");
			}
		}
	//////end of makeAccount
	
	public void showAccInfo() {
		Iterator<Account> itr= hashSet.iterator();
		while(itr.hasNext()) {
			Account a = itr.next();
			a.showAccount();
		}
		System.out.println("전체계좌정보 출력이 완료되었습니다.");
	}/////end of showAccInfo
	
	public void depositMoney() {
		System.out.println("***입   금***");
		System.out.println("계좌번호와 입금할 금액을 입력하세요");
		s.nextLine();
		System.out.print("계좌번호:");
		String ant = s.nextLine();
		try {
			System.out.print("입금액:");
			int dpMoney = s.nextInt();
			if(dpMoney<0||dpMoney%500!=0) {
				System.out.println(dpMoney+"원은 입금불가합니다");
				System.out.println("500원 단위로 입력하세요.");
			}
			else {
				Iterator<Account> itr= hashSet.iterator();
				while(itr.hasNext()) {
					Account a = itr.next();
					if(a.getAccount().equals(ant)) {
						double d = a.getInterest();
						a.setMoney(dpMoney+(int)(d*a.getMoney())
								+a.getMoney());
						System.out.println("입금완료~");
					}
				}
			}
		}
		catch (InputMismatchException e) {
			System.err.println("숫자를 입력하세요");
		}
	}////end of depositMoney
	
	
	public void withdrawMoney() {
		System.out.println("***출   금***");
		System.out.println("계좌번호와 출금할 금액을 입력하세요");
		s.nextLine();
		System.out.print("계좌번호:");
		String ant = s.nextLine();
		try {
			System.out.print("출금액:");
			int dpMoney = s.nextInt();
			if(dpMoney<0||dpMoney%1000!=0) {
				System.out.println(dpMoney+"원은 출금불가합니다");
			}
			else {
				Iterator<Account> itr= hashSet.iterator();
				while(itr.hasNext()) {
					Account a = itr.next();
					if(ant.equals(a.getAccount())) {
						if(a.getMoney()<dpMoney) {
							s.nextLine();
							System.out.println("잔고가 부족합니다. 금액천제를 출금할까요?");
							System.out.println("YES:금액전체 출금, NO:출금취소");
							String yes = s.nextLine();
							if(yes.equalsIgnoreCase("yes")) {
								System.out.println(a.getMoney()+
										"원이 출금되었습니다");
								a.setMoney(0);
							}else if(yes.equalsIgnoreCase("no")){
								System.out.println(yes+": 출금처리취소");
							}
						}else {
							
							a.setMoney(a.getMoney()-dpMoney);
						}
					}
				}
			}
		}
		catch (InputMismatchException e) {
			System.err.println("숫자를 입력하세요");
		}
	}/////withdrawMoney
	public void reset() {
		Iterator<Account> itr= hashSet.iterator();
		while(itr.hasNext()) {
			Account a = itr.next();
			hashSet.remove(a);
		}
	}
	public void saveAccount() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("src/project2/ver04/AccountInfo.obj"));
			Iterator<Account> itr= hashSet.iterator();
			while(itr.hasNext()) {
				Account a = itr.next();
				out.writeObject(a);
			}
			out.close();
			System.out.println("저장완료");
		}
		catch (Exception e) {
			System.out.println("예외발생");
		}
	}////end of saveAccount
	
	
	boolean daemon = false;
	public void AutoSave(AutoSaverT ast) {
		System.out.println("1.자동저장 on, 2.자동저장 off");
		String n = s.next();
		if(n.equalsIgnoreCase("1")) {
			try {
				if(daemon==false) {
					daemon=true;
				}else if(daemon==true) {
					System.out.println("이미 자동저장이 실행중입니다.");
				}
				if(BankingSystemVer04.cnt==0) {
					ast.setDaemon(daemon);
					ast.start();
				}
				BankingSystemVer04.cnt=1;
			}
			catch (IllegalThreadStateException e) {
				e.printStackTrace();
			}
		}else if(n.equalsIgnoreCase("2")) {
			ast.interrupt();
			daemon=false;
			BankingSystemVer04.cnt=0;
			System.out.println("자동저장 off");
		}
	}
	public void txtSave() {
		try {
			PrintWriter out = new PrintWriter(
					new FileWriter("src/project2/ver04/AutoSaveAccount.txt"));
			Iterator<Account> itr = hashSet.iterator();
			while(itr.hasNext()) {
				Account a = itr.next();
				//System.out.printf("계좌번호:%s, 이름:%s, 잔고:%s\n", a.getAccount(), a.getName(), a.getMoney());
				out.printf("계좌번호:%s, 이름:%s, 잔고:%s, 기본이자:%s\n ", a.getAccount(), a.getName(), a.getMoney(), a.getInterest());
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void readAccount() {
		try{
			ObjectInputStream in = new ObjectInputStream(
					new FileInputStream("src/project2/ver04/AccountInfo.obj"));
			Account acc = null ; 
			while(true) {
				acc = (Account)in.readObject();
				
				if(acc==null) {
					break;
				}
				hashSet.add(acc);
			}
			in.close();
		}
		catch (EOFException e) {
		}
		catch (FileNotFoundException e) {
			System.out.println("파일이 없습니다.");
		}
		catch (ClassNotFoundException e) {
			System.out.println("클래스가 없습니다.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}

