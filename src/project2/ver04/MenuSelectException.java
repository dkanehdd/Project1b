package project2.ver04;

public class MenuSelectException extends Exception{
	public MenuSelectException() {
		super("숫자의 범위가 잘못되었습니다.\n1~5사이 숫자를 입력하세요");
	}
}
