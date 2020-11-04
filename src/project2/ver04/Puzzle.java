package project2.ver04;

import java.util.Random;
import java.util.Scanner;

public class Puzzle {
	
	static Scanner s = new Scanner(System.in);
	static Random rd = new Random();
	static char[][] puzzle = {{'1','2','3'},{'4','5','6'},{'7','8','X'}};
	static char[][] puzzle1 = {{'1','2','3'},{'4','5','6'},{'7','8','X'}};
	
	public void showBoard() {
		for(int i=0 ; i<puzzle.length ; i++) {
			for(int j=0 ; j<puzzle[i].length ; j++) {
				System.out.print(puzzle[i][j]+" ");
			}
			System.out.println();
		}
	}
	public boolean end() {
		int cnt=0;
		for(int i=0 ; i<puzzle.length ; i++) {
			for(int j=0 ; j<puzzle[i].length ; j++) {
				if(puzzle[i][j]==puzzle1[i][j]) {
					cnt++;
				}
			}
		}
		if(cnt==9) {
			return true;
		}else
			return false;
	}
	public void Suffle() {
		for(int s=1 ; s<=5 ; s++) {
			int rdNum = rd.nextInt(4);
			String move ="";
			if(rdNum==0) {
				move="a";
			}else if(rdNum==1){
				move="s";
			}else if(rdNum==2) {
				move="d";
			}else if(rdNum==3) {
				move="w";
			}
			int x=-1, y=-1;
			for(int i=0 ; i<puzzle.length ; i++) {
				for(int j=0 ; j<puzzle[i].length ; j++) {
					if(puzzle[i][j]=='X') {
						x=i;
						y=j;
					}
				}
			}
			if(move.equalsIgnoreCase("d")) {
				if(y+1>2) {
				}
				else {
					puzzle[x][y]=puzzle[x][y+1];
					puzzle[x][y+1]='X';
				}
			}else if(move.equalsIgnoreCase("a")) {
				if(y-1<0) {
				}
				else {
					puzzle[x][y]=puzzle[x][y-1];
					puzzle[x][y-1]='X';
				}
			}else if(move.equalsIgnoreCase("s")) {
				if(x+1>2) {
				}
				else {
					puzzle[x][y]=puzzle[x+1][y];
					puzzle[x+1][y]='X';
				}
			}else if(move.equalsIgnoreCase("w")) {
				if(x-1<0) {
				}
				else {
					puzzle[x][y]=puzzle[x-1][y];
					puzzle[x-1][y]='X';
				}
				
			}
		}
	}
	public  void Puzzle3_3() {
		Suffle();
		while(true) {
			
			
			showBoard();
			
			moveX();
			
			if(end()==true) {
				showBoard();
				System.out.println("정답입니다 ^^");
				break;
			}
		}
		while(true) {
			System.out.println("계속하시려면 아무키를, 종료하시려면 'Y'를 입력해주세요!");
			String end = s.nextLine();
			if(end.equalsIgnoreCase("y")) {
				System.out.println("종료");
				break;
			}else {
				System.out.println("다시시작!");
				Puzzle3_3();
				break;
			}
		}
		
	}
	public void moveX() {
		
		int x=-1, y=-1;
		for(int i=0 ; i<puzzle.length ; i++) {
			for(int j=0 ; j<puzzle[i].length ; j++) {
				if(puzzle[i][j]=='X') {
					x=i;
					y=j;
				}
			}
		}
		System.out.println("X가 이동할키 입력");
		System.out.println("[a]:왼쪽, [d]:오른쪽, [w]:위쪽, [s]:아래쪽");
		System.out.println("[x]:종료");
		String move = s.nextLine();
		if(move.equalsIgnoreCase("d")) {
			if(y+1>2) {
				System.out.println("***이동불가***");
				moveX();
			}
			else {
				puzzle[x][y]=puzzle[x][y+1];
				puzzle[x][y+1]='X';
			}
		}else if(move.equalsIgnoreCase("a")) {
			if(y-1<0) {
				System.out.println("***이동불가***");
				moveX();
			}
			else {
				puzzle[x][y]=puzzle[x][y-1];
				puzzle[x][y-1]='X';
			}
		}else if(move.equalsIgnoreCase("s")) {
			if(x+1>2) {
				System.out.println("***이동불가***");
				moveX();
			}
			else {
				puzzle[x][y]=puzzle[x+1][y];
				puzzle[x+1][y]='X';
			}
		}else if(move.equalsIgnoreCase("w")) {
			if(x-1<0) {
				System.out.println("***이동불가***");
				moveX();
			}
			else {
				puzzle[x][y]=puzzle[x-1][y];
				puzzle[x-1][y]='X';
			}
			
		}else if(move.equalsIgnoreCase("x")){
			return;
		}else {
			System.out.println("잘못 입력하셨습니다");
			moveX();
			
		}
	}
}
