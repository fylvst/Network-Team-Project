package org.email;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*用户登录
 * 注册
 * 管理员登录
 *
 * */
public class MainServer {
    int port = 8080;

    public static <UserChangePwd> void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("等待中");
            Socket socket = serverSocket.accept();
            System.out.println("收到了");

            // 根据socket的内容分类
            ExecutorService pool = Executors.newFixedThreadPool(3);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            String request = bufferedReader.readLine();
            System.out.println(request);
            String[] paras = request.split("&");

            // 多线程，只适合没有前提状态的操作，所以Android每次发送的内容都要带上自己的账号信息
            switch (paras[0]) {
                // 用户 管理员登录
                case "login":
                    Login loginThread = new Login(printWriter);
                    pool.execute(loginThread);
                    break;

                    // 用户注册 管理员添加用户
                case "register":
                    Register registerThread = new Register(printWriter);
                    pool.execute(registerThread);
                    break;

                    // 改密
                case "changePwd":
                    ChangePwd changePwdThread=new ChangePwd();
                    pool.execute(changePwdThread);
                    break;

                    // 用户，管理员删用户
                case "delete":
                    Delete deleteThread = new Delete();
                    pool.execute(deleteThread);
                    break;

                    // 用户 发信
                case "send":
                    if (paras[1].substring(paras[1].indexOf("@")).equals("yrj.com")) {
                        ToMyself toMyself = new ToMyself(paras[1], paras[2], paras[3], paras[4]);
                        pool.execute(toMyself);
                    } else {
                        ToOthers toOthers = new ToOthers(paras[1], paras[2], paras[3], paras[4]);
                        pool.execute(toOthers);
                    }
                    break;

                    // 用户 收信
                case "check":
                    Check userCheckThread = new Check(printWriter);
                    pool.execute(userCheckThread);
                    break;

                    // 管理员赋权
                case "grant":
                    Grant grant = new Grant();
                    pool.execute(grant);
                    break;

                    // 管理员除权
                case "revoke":
                    Revoke revoke = new Revoke();
                    pool.execute(revoke);
                    break;

                    // 管理员禁用或解禁用户 "ban&用户的email&y/n"
                case "ban":
                    Ban ban = new Ban(paras[1],paras[2]);
                    pool.execute(ban);
                    break;

                    // 管理员群发
                case "sendMany":
                    SendMany sendMany = new SendMany();
                    pool.execute(sendMany);
                    break;

                    //TODO 其实还有一些需求，但是我这里暂时没写，详见邮件说明书
                //TODO 比如：设置邮箱大小，协议启停，设置协议端口，设置服务器域名，邮件过滤/账号过滤/IP地址过滤，日志查看/清除/设置日志存储位置/设置日志文件大小
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("MainServer错误");
        }
    }
}
