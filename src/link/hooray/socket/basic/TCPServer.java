package link.hooray.socket.basic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 接收客户端的请求，给客户端回复数据
 *
 * 构造方法
 *      ServerSocket(int port)
 *      创建绑定到指定端口的服务器套接字。
 * 成员方法
 *      Socket	accept()
 *      侦听要连接到此套接字并接受它。
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        // 1，创建服务器
        ServerSocket serverSocket = new ServerSocket(8888);
        // 2，获取到请求的socket对象
        Socket accept = serverSocket.accept();
        // 3，获取输入流，读取客户端数据
        InputStream is = accept.getInputStream();
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        System.out.println(new String(bytes,0,len));
        // 4，获取输出流，回复客户端数据
        OutputStream os = accept.getOutputStream();
        os.write("收到谢谢".getBytes());
        // 5，释放资源
        accept.close();
        serverSocket.close();


    }
}
