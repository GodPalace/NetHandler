package Test;

import com.godpalace.net.NetHandler;

import javax.swing.*;
import java.net.InetAddress;

public class TestHandler extends JFrame {
    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame();
        f.setSize(500, 500);

        NetHandler handler = new NetHandler(InetAddress.getByName("127.0.0.1"));
        f.getContentPane().add(handler);
        handler.startHandle();

        f.setVisible(true);
    }
}
