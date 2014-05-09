package com.god.redis.other;
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.Serializable;  
import java.net.Socket;  
import java.util.Arrays;  
import java.util.List;  
  
public class RedisClient {  
  
    public <T extends Serializable> void Set(String key, Object value) {  
        sendRequest(Arrays.asList("Set", key, value));  
    }  
      
    public Object Get(String key) {  
        return sendRequest(Arrays.<Object>asList("Get", key));  
    }  
      
    private Object sendRequest(List<Object> payload) {  
        Socket socket = null;  
        try {  
            socket = new Socket("localhost", 1234);  
              
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
            output.writeObject(payload);  
            output.flush();  
              
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
            Object response = input.readObject();  
              
            output.close();  
            input.close();  
            return response;  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (socket != null) {  
                try {  
                    socket.close();  
                } catch (Exception e) {  
                }  
            }  
        }  
        return null;  
    }  
      
}  