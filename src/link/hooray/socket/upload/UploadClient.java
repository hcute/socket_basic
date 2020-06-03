package link.hooray.socket.upload;

import java.io.*;
import java.net.Socket;

/**
 *  文件上传步骤
 *      1，创建一个本地字节输入流读取本地的文件
 *      2，创建一个socket对象
 *      3，使用socket中的getOutputStream ，获取网络字节输出流
 *      4，使用本地输入流读取文件
 *      5，使用网络输出流将内容写入到服务器
 *      6，使用socket中的getInputStream，获取网络字节输入流
 *      7，使用网络字节输入流，读取服务器的返回
 *      8，释放资源
 */
public class UploadClient {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(new File("RocketMQ使用手册.pdf"));
        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream os = socket.getOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;

        // 读取本地的文件，但是不会读取到文件结束标记，也不会将结束标记写入服务端
        while ((len = fis.read(bytes)) != -1) {
            os.write(bytes);
        }

        // 需要写入结束标记
        socket.shutdownOutput();

        InputStream is = socket.getInputStream();

        while ((len = is.read(bytes)) !=-1) {
            System.out.println(new String(bytes,0,len));
        }
        fis.close();
        socket.close();

    }
}
