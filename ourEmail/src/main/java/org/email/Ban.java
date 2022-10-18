package org.email;

import java.sql.ResultSet;

public class Ban implements Runnable{
    private String email;
    private String y;
    public Ban(String email,String y){
        this.email=email;
        this.y=y;
    }
    @Override
    public void run() {
        DataBaseConnector dataBaseConnector=new DataBaseConnector();
        try {
        if(this.y.equals("y")){
        dataBaseConnector.quest("update user set isBanned='true' where email='"+this.email+"';");
        } else{
            dataBaseConnector.quest("update user set isBanned='false' where email='"+this.email+"';");
        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
