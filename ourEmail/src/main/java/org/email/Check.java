package org.email;

import java.io.PrintWriter;

public class Check implements Runnable{
    private PrintWriter p;
    public Check(PrintWriter p){
        this.p=p;
    }
    @Override
    public void run() {

    }
}
