package socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimpleServer
{

    public static ArrayList<Socket> socketList = new ArrayList<Socket>();
    public static ArrayList<Map<String, Socket>> arrayList = new ArrayList<Map<String,Socket>>();
    public static Map<String, Socket> map = new HashMap<String, Socket>();
    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
                        
        ServerSocket ss = new ServerSocket(30000);
        
        while(true){
            
            Socket s = ss.accept();
            InetAddress in = s.getInetAddress();

            String inetAddress = in.getHostAddress();
            map.put(in.getHostAddress(), s);//存放客户端访问服务的ip地址
            socketList.add(s);// 存放客户端访问服务器的socket
            
            new Thread(new ServerThread(s)).start();
        }
        
    }

}