import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class handler extends JPanel
        implements MouseListener , MouseMotionListener , MouseWheelListener ,
        ActionListener , KeyListener {

    private BufferedImage image;
    private final Timer timer;
    private final InetAddress ip;
    private Thread sendMouse, receiveImage;
    private int x, y, lport;
    private boolean isStart, isEnter;

    private boolean sendMessage(InetAddress ip, int port, String msg) {
        try (
                DatagramSocket d = new DatagramSocket();
                ) {

            d.send(new DatagramPacket(msg.getBytes(), 0, msg.length(),
                    ip, port));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public handler(InetAddress ip, int lport) {
        this.ip = ip;
        this.lport= lport;
        isStart = false;
        isEnter = false;

        timer = new Timer(50, this);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class SendMouse implements Runnable {
        @Override
        public void run() {

        }
    }
}
