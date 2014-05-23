//package socket;
//
//import java.io.IOException;
//import java.net.Socket;
//
//import org.springframework.messaging.Message;
//
//public class ClientThread implements Runnable
//{
//
//    private Socket s;
//    
//    private Handler handler;
//    
//    BufferedReader br;
//    
//    public ClientThread(Socket s, Handler handler) throws IOException{
//        
//        this.s = s;
//        this.handler = handler;
//        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
//    }
//    
//    @Override
//    public void run()
//    {
//        // TODO Auto-generated method stub
//        
//        try
//        {
//            String content = null;
//            while((content = br.readLine())!= null){
//                Message msg = new Message();
//                msg.obj = content;
//                handler.sendMessage(msg);
//            }
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//}
// 