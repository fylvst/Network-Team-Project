// 监听socket连接
package org.email;

import net.iharder.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ToOthers implements Runnable{
    String realSender="mail1062964308@111.com";
    String receiver = "1062964308@qq.com";
    String subject="hello?";
    String body="hello you";

    public ToOthers(){
    }

    public ToOthers(String realSender, String receiver, String subject, String body){
        this.realSender=realSender;
        this.receiver=receiver;
        this.subject=subject;
        this.body=body;
    }

    @Override
    public void run() {
        // 申请qq或163的第三方smtp连接的邮箱账户
        String master="mail1062964308@163.com";
        String password = "PRRUUAVQPYRHHRPN";
        // 发送者
        String sender = "mail1062964308@163.com";

        String user = Base64.encodeBytes(master.substring(0, sender.indexOf("@")).getBytes());
        String pass = Base64.encodeBytes(password.getBytes());

        try {
            Socket socket = new Socket("smtp.163.com", 25);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writter = new PrintWriter(socket.getOutputStream(), true);

            System.out.println(reader.readLine()); //打印SMTP server连接成功的提示
            //HELO
            writter.println("HELO b");
            System.out.println(reader.readLine());
            //AUTH LOGIN
            writter.println("auth login");
            System.out.println(reader.readLine());
            writter.println(user);// 输入加密的账户名
            System.out.println(reader.readLine());
            writter.println(pass);//输入加密的密码
            System.out.println(reader.readLine());
            //Above   Authentication successful <pre name="code" class="java">

            // 设置接收者和发送者
            writter.println("mail from:<" + sender + ">");
            System.out.println(reader.readLine());
            writter.println("rcpt to:<" + this.receiver + ">");
            System.out.println(reader.readLine());

            // 设置数据
            writter.println("data");
            System.out.println(reader.readLine());
            writter.println("subject:"+this.subject);
            // TODO 这里的sender是我们希望可变的sender，但是如与master不一致，会被放进垃圾箱
            // TODO 我希望在PopReceiver中可以把他当成非垃圾
            writter.println("from:" + this.realSender);
            writter.println("to:" + this.receiver);
            writter.println("Content-Type: text/plain;charset='gb2312'");
            writter.println();
            writter.println(this.body);
            writter.println(".");
            writter.println("");
            System.out.println(reader.readLine());

            // 发送，退出
            writter.println("rset");
            System.out.println(reader.readLine());
            writter.println("quit");
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
