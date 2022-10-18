package org.email;

import java.io.PrintWriter;

public class Login implements Runnable{
    private PrintWriter p;
    public Login(PrintWriter p){
        this.p=p;
    }
    @Override
    public void run() {
        System.out.println("Login线程启动成功");
        p.println("来自服务端的返回信息");
        p.flush();
    }
}
