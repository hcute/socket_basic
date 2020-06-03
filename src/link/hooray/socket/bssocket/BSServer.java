package link.hooray.socket.bssocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 模拟浏览器请求
 * 如果页面中有图片，那么浏览器就会开启新的线程重新请求
 */
public class BSServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileInputStream fis = null;
                    try {
                        InputStream is = socket.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String head = br.readLine();

                        String[] infos = head.split(" ");
                        String info = infos[1];
                        int i = info.indexOf("/", 1);
                        String htmlpath = info.substring(i + 1);

                        fis = new FileInputStream(new File(htmlpath));

                        OutputStream os = socket.getOutputStream();
                        os.write("HTTP/1.1 200 OK\r\n".getBytes());
                        os.write("Content-Type:text/html\r\n".getBytes());
                        os.write("\r\n".getBytes());

                        int len = 0;
                        byte[] bytes = new byte[1024];
                        while ((len = fis.read(bytes)) != -1) {
                            os.write(bytes);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fis.close();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
//        serverSocket.close();
    }
}
