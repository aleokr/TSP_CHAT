package chat_sk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ChatServer
{
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

        String s;
        while((s = csock_br.readLine()) != null)
        {
            System.out.println("\rclient: " + s);//wypisywanie w konsoli rozmowy
            System.out.print("> ");
        }

        System.out.println("\rserver: Client has disconnected");//stracił połączenie 
        csock.close();//zamyka połaczenie z odpowiednim IP
        sock.close();//kończy działanie serwera
    }
}

