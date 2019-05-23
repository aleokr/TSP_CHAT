package chat_sk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ChatClient
{
    public static int port = 13000;
    public static BufferedReader con_br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args)throws IOException
    {
        System.out.print("Enter server address: ");
        String address = con_br.readLine();
        Socket sock = new Socket(address, port);//łączenie się z serverem przy pomocy IP
        BufferedReader sock_br = new BufferedReader(new InputStreamReader(sock.getInputStream()));//dalej analogicznie jak w serverze
        PrintWriter sock_pw = new PrintWriter(sock.getOutputStream(), true);
        System.out.println("Connection established");

        Thread chat_client_writer = new ChatWriter("chat_client_writer", sock_pw, con_br);
        chat_client_writer.start();
        
        String s;
        while((s = sock_br.readLine()) != null)
        {
            System.out.println("\rserver: " + s);
            System.out.print("> ");
        }
        sock.close();
    }
}
