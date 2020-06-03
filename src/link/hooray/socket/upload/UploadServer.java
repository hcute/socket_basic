package link.hooray.socket.upload;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * 文件上传的服务器端
 *
 * 步骤
 *      1，创建服务器对象 ServerSocket
 *      2，使用服务器对象的accept获取客户端对象
 *      3，使用客户端对象获取到InputStream
 *      4，判断文件夹是否存在
 *      5，创建本地的OutputStream，绑定输出的目的地
 *      6，使用客户端网络输入流读取数据
 *      7，使用本地的输入流，写入数据到文件
 *      8，使用客户端获取网络输出流
 *      9，使用网络输出流输出内容到服务端
 *      10，释放资源
 */
public class UploadServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        while(true) {
            Socket s = serverSocket.accept();
            new Thread( ()->{
                FileOutputStream fos = null;
                try {
                    InputStream is = s.getInputStream();
                    File file = new File("upload");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    String fileName = "hooray" + System.currentTimeMillis() + new Random().nextInt(999999) + ".pdf";
                    fos =new FileOutputStream(new File(file, fileName));
                    byte[] bytes = new byte[1024];
                    int len = 0;

                    while (( len = is.read(bytes)) != -1) {
                        fos.write(bytes);
                    }
                    OutputStream os = s.getOutputStream();
                    os.write("上传成功".getBytes());

                }catch (IOException e) {
                    System.out.println(e);
                }finally {
                    try {
                        fos.close();
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }
}
