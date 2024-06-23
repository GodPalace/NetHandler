import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;

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
import java.net.*;

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
        sendMouse = new Thread(new SendMouse());
        receiveImage = new Thread();
    }

    public void startHandle() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);

        timer.start();
        sendMouse.start();
        receiveImage.start();
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

    private class ReceiveImage implements Runnable {
        @Override
        public void run() {
            while (true) try (MulticastSocket ms = new MulticastSocket(3332)) {
                byte[] b = new byte[102400];
                DatagramPacket p = new DatagramPacket(b, 0, b.length);
                ms.joinGroup(InetAddress.getByName("224.3.2." + ip.getHostAddress().substring(
                        ip.getHostAddress().lastIndexOf("."))));

                while (true) if (isStart) {
                    ms.receive(p);


                }

            } catch (Exception e) {
            }
        }
    }

    private class SendMouse implements Runnable {
        @Override
        public void run() {
            while (true) try {

                while (true) if (isStart && isEnter) {
                    String msg = "MOUSEMOVE\n" + x + "\n" + y;

                    sendMessage(ip, 3330, msg);
                }

            } catch (Exception e) {
            }
        }
    }
}
