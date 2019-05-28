package chat_sk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class ChatServer
{	
	static String userLogin;
	static String userPassword;
	static String friendLogin;
	static boolean correct=false;
	static boolean connect=false;
	static int number_of_user;
	static ArrayList <User> list_of_users;
    public static int port = 13000;//port servera
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));//czyta  linie nadchodząca

    public static void main(String[] args)throws IOException
    {
        ServerSocket sock = new ServerSocket(port);//tworzy serwer
        System.out.println("server: Waiting for client to connect");
        Socket csock = sock.accept();//odczytuje adres IP ziomka który się z nim łączy
        System.out.println("server: Connection established");
        BufferedReader csock_br = new BufferedReader(new InputStreamReader(csock.getInputStream()));
        PrintWriter csock_pw = new PrintWriter(csock.getOutputStream(), true);//możliwość wysyłania wiadomości
        
        Thread chat_server_writer = new ChatWriter("chat_server_writer", csock_pw, con_br);//tworzy nowy watek pisania 
        chat_server_writer.start();//uruchamia wątek
        System.out.println("server: Enter your login ");
        userLogin=con_br.readLine();
        System.out.println("server: Enter your password ");
        userPassword=con_br.readLine();
        for(int i=0; i<list_of_users.size(); ++i) {
        	if(list_of_users.get(i).login==userLogin) {
        		if (list_of_users.get(i).password==userPassword) {
        			System.out.println("Login and password are correct");
        			correct=true;
        			number_of_user=i;
        		}else {
        			System.out.println("Login or password are uncorrect");
        		}
        	}
        }
        if(correct==true) {
        	list_of_users.get(number_of_user).log_in=true;
        	System.out.println("Enter your friend login");
        	friendLogin=con_br.readLine();
        	for(int i=0; i<list_of_users.get(number_of_user).list_of_friends.size(); ++i) {
            	if(list_of_users.get(number_of_user).list_of_friends.get(i).login==friendLogin) {
            		if (list_of_users.get(number_of_user).list_of_friends.get(i).log_in==true) {
            			System.out.println("Connect");
            			connect=true;
            		}else {
            			System.out.println("This user is not your friend or he/she is logout");
            		}
            	}
            }
        	if(connect==true) {
            String s;
            while((s = csock_br.readLine()) != null)
            {
                System.out.println("\rclient: " + s);//wypisywanie w konsoli rozmowy
                System.out.print("> ");
            }
        	}
        }
        

        System.out.println("\rserver: Client has disconnected");//stracił połączenie 
        csock.close();//zamyka połaczenie z odpowiednim IP
        sock.close();//kończy działanie serwera
    }
}

