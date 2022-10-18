package org.email;

import java.sql.ResultSet;

public class ToMyself implements Runnable {
    String realSender = "mail1062964308@yrj.com";
    String receiver = "1062964308@yrj.com";
    String subject = "hello?";
    String body = "hello you";

    public ToMyself() {
    }

    public ToMyself(String realSender, String receiver, String subject, String body) {
        this.realSender = realSender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void run() {
        DataBaseConnector dataBaseConnector=new DataBaseConnector();
        try {
            ResultSet s=dataBaseConnector.quest("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}