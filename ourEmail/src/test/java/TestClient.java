import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class TestClient {
    @Test
    public void shouldAnswerWithTrue() {
        try{
            Socket socket=new Socket("127.0.0.1",8080);

            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter printWriter=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            printWriter.println("login&squy&123456");
            printWriter.flush();

            String s=bufferedReader.readLine();
            System.out.println(s);

            socket.close();

      }catch(Exception e){
            e.printStackTrace();
        }
    }
}