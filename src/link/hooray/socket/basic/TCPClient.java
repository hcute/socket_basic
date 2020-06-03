package link.hooray.socket.basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * tcp 客户端
 *
 * 构造方法
 *      Socket(String ip,int port)
 * 成员方法
 *      getOutputStream()
 *      getInputStream()
 */
public class TCPClient {

    public static void main(String[] args) throws IOException {

        //1，创建客户端
        Socket socket = new Socket("127.0.0.1", 8888);
        //2，获取客户端的输出流，想服务器端发送数据
        OutputStream os = socket.getOutputStream();
        os.write("你好服务器".getBytes());

        //3，获取输入流，读取服务端返回的数据
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        System.out.println(new String(bytes,0,len));
        //4，释放资源
        socket.close();

    }
}
