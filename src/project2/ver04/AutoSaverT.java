package project2.ver04;

import java.util.Scanner;

public class AutoSaverT extends Thread {
	static Scanner s = new Scanner(System.in);
	AccountManager am; 
	public AutoSaverT(AccountManager am) {
		this.am = am;
	}
	@Override
	public void run() {
		while(true) {
			try {
				am.txtSave();
				System.out.println("~~자동저장중~~");
				sleep(5000);
			}
			catch (InterruptedException e) {
				return;
			}
			catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
