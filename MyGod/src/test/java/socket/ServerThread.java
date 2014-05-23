package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
public class ServerThread implements Runnable
{

    private Socket s = null;
    private BufferedReader br = null;
    public ServerThread(Socket s) throws IOException
    {
            this.s = s;
            InetAddress in = s.getInetAddress();
            String inetAddress = in.getHostAddress();
            
            if(!inetAddress.equals("170.81.304.112")&&!inetAddress.equals("170.81.304.47")){// 注意：这里的ip为自己和对方的ip地址，下同
                br = null;
            }
            else {
                br = new BufferedReader(new InputStreamReader(s.getInputStream(), "utf-8"));
            }
    }

    @Override
    public void run()
    {
        try
        {
            String content = null;

            while ((content = readFromClient()) != null)
            {
                for (Socket s : SimpleServer.socketList)
                {
                    if(!s.getInetAddress().getHostAddress().equals("170.81.304.112")&&!s.getInetAddress().getHostAddress().equals("170.81.304.47")){
                        
                    }
                    else{
                        OutputStream os = s.getOutputStream();
                        os.write((content + "\n").getBytes("utf-8"));
                    }
                    
                }
                    }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private String readFromClient()
    {
        if(br != null){
            try
            {
                return br.readLine();
            }
            catch (IOException e)
            {
            }
        }

        return null;
    }

}