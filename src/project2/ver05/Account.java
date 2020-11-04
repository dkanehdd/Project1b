package project2.ver05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Account {
	Connection con;
	Statement stmt;
	PreparedStatement psmt;
	ResultSet rs;
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
	
	public void connect(String user, String pass) {
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl", user, pass);
		
		}
		catch (SQLException e) {
			System.out.println("데이터베이스 연결 오류");
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(con!=null) con.close();
			if(psmt!=null) psmt.close();
			if(rs!=null) rs.close();
		}
		catch (Exception e) {
			System.out.println("자원 반납시 오류발생");
			e.printStackTrace();
		}
	}
	public void makeAccount() {
		
		try {
			String sql = "INSERT INTO banking_tb(idx, "
					+ "account, name, money) VALUES ( SEQ_BANKING.nextval,?,?,?)";
			Class.forName("oracle.jdbc.OracleDriver");
			connect("kosmo","1234");
			psmt = con.prepareStatement(sql);
			System.out.println("***신규계좌개설***");
			System.out.print("계좌번호:");
			String account = s.nextLine();
			System.out.print("고객이름:");
			String name = s.nextLine();
			System.out.print("잔고:");
			int money = s.nextInt();
			s.nextLine();
			psmt.setString(1, account);
			psmt.setString(2, name);
			psmt.setInt(3, money);
			int affected = psmt.executeUpdate();
			System.out.println(affected+ "개의 계좌가 개설되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
		
		
	}
	
	public void showAccInfo() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connect("kosmo","1234");
			String sql = "SELECT * FROM banking_tb ";
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			while(rs.next()) {
				String idx = rs.getString(1);
				String account = rs.getString(2);
				String name = rs.getString(3);
				String balance = rs.getString(4);
				
				System.out.printf("%s 계좌번호:%s 이름:%s 잔고:%s\n",
						idx,account,name,balance);
			} 
			System.out.println("=================================");
		}
		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		finally {
			close();
		}
	}
	public void depositMoney() {
		String sql = "UPDATE banking_tb SET money=money+? "
				+ " WHERE account=? ";
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			connect("kosmo","1234");
			psmt = con.prepareStatement(sql);
			System.out.println("***입   금***");
			System.out.println("계좌번호와 입금할 금액을 입력하세요");
			System.out.print("계좌번호:");
			String ant = s.nextLine();
			System.out.print("입금액:");
			int dpMoney = s.nextInt();
			s.nextLine();
			psmt.setInt(1, dpMoney);
			psmt.setString(2, ant);
			
			int affected = psmt.executeUpdate();
			System.out.println("~~입금완료~~");
		}
		catch (SQLException | ClassNotFoundException e) {
		}
		finally {
			close();
		}
	}
	public void withdrawMoney() {
		String sql = "UPDATE banking_tb SET money=money-? "
				+ " WHERE account=? ";
		try{
			Class.forName("oracle.jdbc.OracleDriver");
			connect("kosmo","1234");
			psmt = con.prepareStatement(sql);
			System.out.println("***출   금***");
			System.out.println("계좌번호와 출금할 금액을 입력하세요");
			System.out.print("계좌번호:");
			String ant = s.nextLine();
			System.out.print("출금액:");
			int dpMoney = s.nextInt();
			s.nextLine();
			psmt.setInt(1, dpMoney);
			psmt.setString(2, ant);
			
			int affected = psmt.executeUpdate();
			System.out.println("~~출금완료~~");
		}
		catch (SQLException | ClassNotFoundException e) {
		}
		finally {
			close();
		}
	}
}
