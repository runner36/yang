//package socket;
//
//import java.awt.Button;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.util.logging.Handler;
//
//import javax.swing.text.View;
//
//import org.aspectj.bridge.Message;
//
//import com.mysql.jdbc.log.Log;
//
//public class Client extends Activity
//{
//    private EditText inputEditText = null;
//    private TextView content = null;
//    private Button sendMsg = null;
//    Socket s;
//    
//    OutputStream os;
//
//    private Handler handler = new Handler(){
//
//        @Override
//        public void handleMessage(Message msg)
//        {
//            // TODO Auto-generated method stub
//            super.handleMessage(msg);
//            content.append("\n"+msg.obj.toString());
//            Log.v("Jeny", "-----------"+content);
//        }
//        
//    };
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        // TODO Auto-generated method stub
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.client);
//        initWidget();
//        try
//        {
//            s = new Socket("10.81.34.112", 30000);
//            new Thread(new ClientThread(s, handler)).start();
//            os = s.getOutputStream();
//        }
//        catch (UnknownHostException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        sendMsg.setOnClickListener(new OnClickListener()
//        {
//            
//            @Override
//            public void onClick(View v)
//            {
//                // TODO Auto-generated method stub
//                try
//                {
//                   os.write((inputEditText.getText().toString()+"\r\n").getBytes("utf-8"));
//                    inputEditText.setText("");
//                }
//                catch (UnsupportedEncodingException e)
//                {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                catch (IOException e)
//                {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    
//    private void initWidget(){
//        inputEditText = (EditText) findViewById(R.id.input);
//        content = (TextView) findViewById(R.id.content);
//        sendMsg = (Button) findViewById(R.id.sendMsg);
//    }
//}