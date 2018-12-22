package youtube_live;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class MainFile {

    public static void main(String args[]) throws Exception {

        File file = new File("Id.txt");

        if (file.exists()) {
            byte[] datainBytes;
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            datainBytes = new byte[dis.available()];
            dis.readFully(datainBytes);
            String content = new String(datainBytes, 0, datainBytes.length);
            WidgetOfSubscriber ws = new WidgetOfSubscriber(content);
            ws.Call_me(content);
            ws.setVisible(true);
        }else
        {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        }

    }

}
