
java管道通信 (2009-02-21 08:56:50)转载▼
标签： java 管道通信 it	分类： java学习
Java提供管道功能，实现管道通信的类有两组：PipedInputStream和PipedOutputStream或者是PipedReader和PipedWriter。管道通信主要用于不同线程间的通信。
一个PipedInputStream实例对象必须和一个PipedOutputStream实例对象进行连接而产生一个通信管道。PipedOutputStream向管道中写入数据，PipedIntputStream读取PipedOutputStream向管道中写入的数据。一个线程的PipedInputStream对象能够从另外一个线程的PipedOutputStream对象中读取数据。
PipedInputStream和PipedOutputStream实例
 
 //Sender类
package pipeCommu;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
public class Sender extends Thread{
    private PipedOutputStream out=new PipedOutputStream();//发送者创建PipedOutputStream向外写数据; 
    public PipedOutputStream getOut(){
        return out;
    }
    public void run(){
        String strInfo="hello,receiver";
        try{
            out.write(strInfo.getBytes());//写入数据
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
 
 //Reader类，负责接收数据
package pipeCommu;
import java.io.PipedInputStream; 
public class Reader extends Thread{
    private PipedInputStream in=new PipedInputStream();//发送者创建PipedOutputStream向外写数据
    public PipedInputStream getIn(){
        return in;
    }
    public void run(){
        byte[] buf=new byte[1024];//声明字节数组
        try{
            int len=in.read(buf);//读取数据，并返回实际读到的字节数
            System.out.println("receive from sender:"+new String(buf,0,len));
            in.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
package pipeCommu;
import java.io.*;
public class PipedStream {
     public static void main(String[] args) throws Exception{
        Sender send=new Sender();
        Reader read=new Reader();
        PipedOutputStream out=send.getOut();
        PipedInputStream in=read.getIn();
        out.connect(in);//或者也可以用in.connect(out);
        send.start();
        read.start();
    }  
}

PipedReader和PipedWriter
 
package pipeCommu;
import java.io.*;
public class PipedCommu {
     public static void main(String[] args) {
         // TODO Auto-generated method stub
         try{
              PipedReader reader=new PipedReader();
              PipedWriter writer=new PipedWriter(reader);
              Thread a=new Send(writer);
              Thread b=new Read(reader);
              a.start();
              Thread.sleep(1000);
              b.start();
         }catch(IOException e){
              e.printStackTrace();
             
         }catch(InterruptedException e1){
              e1.printStackTrace();           
         }
     }
}
     class Send extends Thread{
         PipedWriter writer;
         public Send(PipedWriter writer){
              this.writer=writer;
         }
         public void run(){
              try{
                   writer.write("this is a.hello world".toCharArray());
                   writer.close();
              }catch(IOException e){
                   e.printStackTrace();
              }            
         }
     }
     class Read extends Thread{
         PipedReader reader;
         public Read(PipedReader reader){
              this.reader=reader;
         }
         public void run(){
              System.out.println("this is B");
              try{
                   char[] buf=new char[1000];
                   reader.read(buf,0,100);
                   System.out.println(new String(buf));
              }catch(Exception e){
                   e.printStackTrace();
              }
         }
     }