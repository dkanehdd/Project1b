package multichatroom;

import java.util.Scanner;

public class TicTacToe {
	static char[][] board = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
	static void printGameBoard(char[][] board) {
		for(int i=0 ; i<board.length ; i++) {
			System.out.println(board[i][0]+" |"+board[i][1]+" |"+board[i][2]);
			if(i<2) {
				System.out.println("──┼──┼──");
			}
		}
	}
	static void player1() {
		Scanner sc = new Scanner(System.in);
		System.out.print("좌표를 입력하세요:");
		int x=sc.nextInt();
		int y=sc.nextInt();
		if(x>2||y>2) {
			System.out.println("좌표입력이 잘못되었습니다.");
			player1();
		}else if(board[x][y]!=' ') {
			System.out.println("좌표입력이 잘못되었습니다.");
			player1();
		}
		else {
			board[x][y]='X';
			printGameBoard(board);
		}
		
	}
	static void player2() {
		Scanner sc = new Scanner(System.in);
		System.out.print("좌표를 입력하세요:");
		int x=sc.nextInt();
		int y=sc.nextInt();
		if(x>2||y>2) {
			System.out.println("좌표입력이 잘못되었습니다.");
			player1();
		}else if(board[x][y]!=' ') {
			System.out.println("좌표입력이 잘못되었습니다.");
			player2();
		}
		else {
			board[x][y]='O';
			printGameBoard(board);
		}
	}
	static boolean gameover() {
		if(board[0][0]=='X'&&board[0][1]=='X'&&board[0][2]=='X')return true;
		else if(board[1][0]=='X'&&board[1][1]=='X'&&board[1][2]=='X')return true;
		else if(board[2][0]=='X'&&board[2][1]=='X'&&board[2][2]=='X')return true;
		else if(board[0][0]=='X'&&board[1][0]=='X'&&board[2][0]=='X')return true;
		else if(board[0][1]=='X'&&board[1][1]=='X'&&board[2][1]=='X')return true;
		else if(board[0][2]=='X'&&board[1][2]=='X'&&board[2][2]=='X')return true;
		else if(board[0][0]=='X'&&board[1][1]=='X'&&board[2][2]=='X')return true;
		else if(board[0][0]=='O'&&board[0][1]=='O'&&board[0][2]=='O')return true;
		else if(board[1][0]=='O'&&board[1][1]=='O'&&board[1][2]=='O')return true;
		else if(board[2][0]=='O'&&board[2][1]=='O'&&board[2][2]=='O')return true;
		else if(board[0][0]=='O'&&board[1][0]=='O'&&board[2][0]=='O')return true;
		else if(board[0][1]=='O'&&board[1][1]=='O'&&board[2][1]=='O')return true;
		else if(board[0][2]=='O'&&board[1][2]=='O'&&board[2][2]=='O')return true;
		else if(board[0][0]=='O'&&board[1][1]=='O'&&board[2][2]=='O')return true;
		return false;
	}
	public static void main(String[] args) {
		
		printGameBoard(board);
		
		while(true) {
			player1();
			if(gameover()==true) {
				System.out.println("X 승리");
				break;
			}
			player2();
			if(gameover()==true) {
				System.out.println("O 승리");
				break;
			}
		}
	}
}
