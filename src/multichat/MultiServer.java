package multichat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class MultiServer {
	//멤버변수
	static int personCnt = 0;
	Connection con;
	Statement stmt;
	PreparedStatement psmt;
	ResultSet rs;
	static ServerSocket serverSocket =null;
	static Socket socket = null;
	//클라이언트 정보저장을 위한 Map컬렉션생성
	Map<String, PrintWriter> clientMap;
	HashSet<String> blacklist = new HashSet<String>();
	HashSet<String> pWords = new HashSet<String>();
	Map<String, String> fixName= new HashMap<String, String>();
	Map<String, String> blockName;
	ArrayList<String> blockUser= new ArrayList<String>();
	HashSet<String> fixUser=new HashSet<String>();
	HashSet<String> list = new HashSet<String>();
	boolean same=false;
	int bnum = 0;
	public MultiServer() {
		//클라이언트의 이름과 출력스트림을 저장할 HashMap 컬렉션 생성
		clientMap = new HashMap<String, PrintWriter>();
		//HashMap 동기화 설정. 쓰레드가 사용자정보에 동시에 접근하는것을 차단
		blacklist.add("민우");
		pWords.add("ㅅㅂ");
		pWords.add("자바");
		Collections.synchronizedMap(clientMap);
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin://@localhost:1521:orcl", "kosmo", "1234");
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 오류");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void init() {
		
		try {
			
			//9999번 포트를 설정하여 서버객체를 생성하고 클라이언트의 접속을 대기한다.
			serverSocket = new ServerSocket(9999);
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+"(클라이언트)의"
				+socket.getPort()+" 포트를통해"
				+socket.getLocalAddress()+"(서버)의"
				+socket.getLocalPort()+"포트로 연결됨");
				
				/*
				클라이언트의 메세지를 모든 클라이언트에게 전달하기 위한
				쓰레드 생성 및 시작. 한명당 하나씩의 쓰레드가 생성된다.
				 */
				Thread mst = new MultiServerT(socket);
				mst.start();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				serverSocket.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		MultiServer ms = new MultiServer();
		ms.init();
	}
	
	//접속된 모든 클라이언트에게 메세지를 전달하는 메소드
	public void sendAllMsg(String name, String msg, String flag) {
		
		//Map에 저장된 객체의 키값(접속자명)을 먼저 얻어온다.
		Iterator<String> it = clientMap.keySet().iterator();
		Iterator<String> itr = list.iterator();
		String list1="";
		while(itr.hasNext()) {
			String str = itr.next();
			if(!name.equals(str)) {
				list1+= str+"  ";
			}
		}
		//저장된 객체(클라이언트)의 갯수만큼 반복한다.
		while(it.hasNext()) {
			try {
				//컬렉션의 key는 클라이언트의 접속자명이다.
				String clientName = it.next();
				boolean is =false;
				if(bnum>0) {
					if(blockName.containsKey(clientName)) {
						String[] bArr = blockName.get(clientName).split(" ");
						for(int i=0 ; i<bArr.length ; i++) {
							if(bArr[i].equals(name)) {
								is=true;
							}
						}
					}
				}
				if(is==true) continue;
				//각 클라이언트의 PrintWriter객체를 얻어온다.
				PrintWriter it_out = 
						(PrintWriter)clientMap.get(clientName);
				if(it_out==null) break;
				if(msg==null) break;
				if(flag.equals("One")) {
					//flag가 One이면 해당 클라이언트 한명에게만 전송
					if(name.equals(clientName)) {
						//컬렉션에 저장된 접속자명과 일치하는 경우 메세지 전송
						it_out.println(URLEncoder.encode("귓속말 : "+msg, "UTF-8"));
					}
				}else if(flag.equals("Err")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("※"+msg,"UTF-8"));
					}
				}
				else if(flag.equals("List")) {
					if(name.equals(clientName)) {
						it_out.println(URLEncoder.encode("※"+msg+"\n"+list1,"UTF-8"));
					}
				}
				else {
					//name의 유무에 따라 메세지와 알림창으로 나뉨
					
					if(name.equals("")) {
					//접속, 퇴장에서 사용되는 부분
					it_out.println(URLEncoder.encode(msg,"UTF-8"));
					}
					else {
					it_out.println(URLEncoder.encode("["+name+"]:"+msg,"UTF-8"));
					}
					
					
				}
			}
			catch (Exception e) {
				System.out.println("예외:"+e);
			}
		}
		
	}
	/*
	내부클래스
	init()에 기술되었던 스트림을 생성후 메세지를 읽기쓰기하던 부분이
	해당 내부 클래스로 이동되었다.
	 */
	class MultiServerT extends Thread{
		
		 Socket socket;
		 PrintWriter out = null;
		 BufferedReader in =null;
		 //생성자 : socket을 기반으로 입출력 스트림 생성
		 public MultiServerT(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(
						new InputStreamReader(this.socket.getInputStream(), "UTF-8"));
				
			}
			catch (Exception e) {
				System.out.println("예외 : "+e);
			}
		 }
		 /*
		 쓰레드로 동작할 run()에서는 클라이언트의 접속자명과 메세지를 지속적으로 읽어
		  Echo해주는 역할을 하고있다.
		  */
		@Override
		public void run() {
			
			String name = "";
			String s = "";
			String blockUser="";
			try {
				//클라이언트의 이름을 읽어와서 저장
				name = in.readLine();
				name = URLDecoder.decode(name, "UTF-8");
				blockUser = name;
				blockName=new HashMap<String, String>();
				if(clientMap.containsKey(name)) {
					same=true;
					name=name+".";
					clientMap.put(name, out);
					
					sendAllMsg(name, "중복된 대화명입니다. 다시 입력하세요", "Err");
					
					clientMap.remove(name);
					in.close();
					out.close();
					return;
				}
				if(blacklist.contains(name)) {
					same=true;
					clientMap.put(name, out);
					sendAllMsg(name, "차단된 대화명입니다. 다시 입력하세요", "Err");
					clientMap.remove(name);
					return;
				}
				
				
				//방금 접속한 클라이언트를 제외한 나머지에게 사용자의입장을 알려준다.
				
					//현재 접속자의 정보를 HashMap에 저장한다.
				list.add(name);
				clientMap.put(name, out);
				personCnt++;
				if(clientMap.size()>MAXPERSON.MAXPERSON) {
						sendAllMsg(name, "접속자수가 초과되었습니다.", "Err");
					clientMap.remove(name);
					list.remove(name);
					same=true;
						return;
				}
				sendAllMsg(name, "서버와 연결되었습니다.", "Err");
				sendAllMsg("", name+"님이 입장하셧습니다.", "All");
				
				
				
				//HashMap에 저장된 객체의 수로 접속자수를 파악할수 있다.
				System.out.println(name + " 접속");
				System.out.println("현재 접속자 수는"+clientMap.size()+"명 입니다.");
				//입력한 메세지는 모든 클라이언트에게 Echo된다.
				while(in != null) {
					
					s=in.readLine();
					s = URLDecoder.decode(s, "UTF-8");
					String sql = "INSERT INTO chat_talking VALUES ( seq_chat.nextval,?,?, default)";
					
					psmt = con.prepareStatement(sql);
					psmt.setString(1, name);
					psmt.setString(2, s);
					psmt.executeUpdate();
					if(s==null) break;
					if(pWords.contains(s)) {
						sendAllMsg(name, "금칙어 입니다.", "Err"); 
						continue;
					}
					System.out.println(name + " >> "+s);
					
					/*
					클라이언트가 전송한 메세지가 명령어인지 판단한다.
					 */
					if(s.charAt(0)=='/') {
						//만약 '/'로 시작한다면 명령어이다.
						/*
						귓속말은 아래와 같이 전속하게 된다.
						-> /to 대화명 대화내용
							:따라서 split()을 통해 space로 문자열을 분리한다.
						 */
						String[] strArr = s.split(" ");
						/*
						대화내용에 스페이스가 있는경우 문장의 끝까지를 출력해야 하므로
						배열의 크기만큼 반복하면서 문자열을 이어준다.
						 */
						String msgContent = "";
						for(int i=2 ; i<strArr.length ; i++) {
							msgContent+=strArr[i]+" ";
						}
						if(strArr[0].equals("/to")) {
							sendAllMsg(strArr[1], msgContent, "One");
						}else if(strArr[0].equals("/boom")){
							serverSocket.close();
							in.close();
							out.close();
							socket.close();
						}else if(strArr[0].equals("/fixto")) {
							fixName.put(name, strArr[1]); 
							fixUser.add(name);
							sendAllMsg(name, strArr[1]+"님에게 귓속말 시작", "Err");
						}else if(strArr[0].equals("/unfixto")) {
							fixName.remove(name);
							fixUser.remove(name);
							sendAllMsg(name, "귓속말이 해제되었습니다.", "Err");
						}else if(strArr[0].equals("/list")) {
							sendAllMsg(name, "대화중인 사용자:", "List");
						}else if(strArr[0].equals("/block")) {
							if(bnum>0) {
								if(blockName.get(name)!=null) {
								bnum--;
								strArr[1]= blockName.get(name)+" "+strArr[1] ;
								}
							}
							sendAllMsg(name, strArr[1], "Err");
							blockName.put(name, strArr[1]);
							bnum++;
							sendAllMsg( name, strArr[1]+"사용자의 대화를 차단합니다.", "Err");
						}else if(strArr[0].equals("/unblock")) {
							String bname = "";
							if(strArr[1].equalsIgnoreCase("all")) {
								blockName.remove(name);
								bnum--;
								sendAllMsg(name, "모든 대화차단을 해제합니다.", "Err");
							}else {
								if(blockName.get(name)!=null) {
									String[] arr = blockName.get(name).split(" ");
									for(int i=0 ; i<arr.length ; i++) {
										if(!strArr[1].equals(arr[i])){
											bname+=arr[i]+" ";
										}
									}
									if(!bname.equals("")) {
										blockName.put(name, bname);
									}else {
										blockName.remove(name);
										bnum--;
									}
								}
								sendAllMsg(name, strArr[1]+"님의 대화차단을 해제합니다.", "Err");
							}
							
						}else if(strArr[0].equals("/")) {
							sendAllMsg(name, "/to 대화명  -> 대화명에게 귓속말하기", "Err");
							sendAllMsg(name, "/fixto 대화명 -> 대화명에게 귓속말 고정하기", "Err");
							sendAllMsg(name, "/unfixto -> 귓속말 고정 해제하기", "Err");
							sendAllMsg(name, "/block 대화명 -> 대화명이 보내는 대화 차단하기", "Err");
							sendAllMsg(name, "/unblock 대화명 -> 해당 대화명 대화차단 해제하기(대화명에 all을 입력하면 모든 대화차단 해제)", "Err");
							sendAllMsg(name, "/list -> 현재 대화중인 대화명 출력하기", "Err");
						}
					}
					else {
						//만약 '/'로 시작하지않으면 일반 메세지이다.
						if(fixUser.contains(name)) {
							sendAllMsg(fixName.get(name), s, "One");
						}
						else{
							sendAllMsg(name, s, "All");
						}
						
						
					}
					
				}
			}
			catch (UnsupportedEncodingException e) {
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("예외");
			}
			catch (Exception e) {
				System.out.println("예외:"+e);
			}
			finally {
				/*
				클라이언트가 접속을 종료하면 Socket예외가 발생하게 되어
				finally절로 진입하게 된다. 이때 "대화명"을 통해 정보를
				삭제한다.
				 */
				if(same==false) {
					clientMap.remove(name);
					sendAllMsg("", name+"님이 퇴장하셨습니다.","All");
					//퇴장하는 클라이언트의 쓰레드명을 보여준다.
					System.out.println(name+
							" ["+Thread.currentThread().getName()+"] 퇴장");
					System.out.println("현재 접속자 수는"+clientMap.size()+"명 입니다.");
				}
				
				try {
					in.close();
					out.close();
					socket.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
		}
	}
}
