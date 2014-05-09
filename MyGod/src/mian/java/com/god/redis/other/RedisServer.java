package com.god.redis.other;
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  
import java.util.List;  
  
public class RedisServer {  
  
    private RedisDB redis;  
      
    public RedisServer(RedisDB redis) {  
        this.redis = redis;  
    }  
      
    @SuppressWarnings("unchecked")  
    public void start() {  
        ServerSocket serverSocket = null;  
        try {  
            serverSocket = new ServerSocket(1234);  
            while (true) {  
                Socket socket = serverSocket.accept();  
                  
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());  
                List<Object> request = (List<Object>) input.readObject();  
                  
                Object response = null;  
                if ("Set".equals(request.get(0))) {  
                    redis.Set((String) request.get(1), request.get(2));  
                }  
                else if ("Get".equals(request.get(0))) {  
                    response = redis.Get((String) request.get(1));  
                }  
                  
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());  
                output.writeObject(response);  
                  
                input.close();  
                output.close();  
                socket.close();  
            }  
        }   
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally {  
            if (serverSocket != null) {  
                try {  
                    serverSocket.close();  
                } catch (IOException e) {  
                }  
            }  
        }  
          
    }  
      
} 