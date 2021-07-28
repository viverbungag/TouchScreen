import java.net.*;
import java.io.*;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.*;



public class server {

    public static void main(String[] args) throws IOException, AWTException{
        ServerSocket ss = new ServerSocket(8888);
        while (true){
            Socket s = ss.accept();
            GraphicsEnvironment ge = GraphicsEnvironment.
            getLocalGraphicsEnvironment();
            GraphicsDevice[] gs = ge.getScreenDevices();

            InputStreamReader in = new InputStreamReader(s.getInputStream());
            BufferedReader bf = new BufferedReader(in);
            String readThis = bf.readLine();
            System.out.println(readThis);
            String[] coordinates = readThis.split(",", 3);
            Robot robot = new Robot(gs[0]);
            
            robot.mouseMove(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));

            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            if (coordinates[2].equals("cancel")){
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            }
        }
    }
}



