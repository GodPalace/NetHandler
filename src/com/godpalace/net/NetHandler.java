package com.godpalace.net;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.*;
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
import java.io.ByteArrayInputStream;
import java.net.*;

public class NetHandler extends JPanel
        implements MouseListener , MouseMotionListener , MouseWheelListener ,
        ActionListener , KeyListener {

    private BufferedImage image;
    private final Timer timer;
    private final InetAddress ip;
    private final Thread sendMouse, receiveImage;
    private int x, y, mx, my;
    private boolean isStart, isEnter;

    private void sendMessage(InetAddress ip, String msg) {
        try (
                DatagramSocket d = new DatagramSocket();
                ) {

            d.send(new DatagramPacket(msg.getBytes(), 0, msg.length(),
                    ip, 3330));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public NetHandler(InetAddress ip) {
        this.ip = ip;
        isStart = false;
        isEnter = false;

        timer = new Timer(50, this);
        sendMouse = new Thread(new SendMouse());
        receiveImage = new Thread(new ReceiveImage());
    }

    public void startHandle() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        this.addKeyListener(this);

        isStart = true;
        timer.start();
        sendMouse.start();
        receiveImage.start();

        sendMessage(ip, "STARTHANDLE");
    }

    public void stopHandle() {
        this.removeMouseListener(this);
        this.removeMouseMotionListener(this);
        this.removeMouseWheelListener(this);
        this.removeKeyListener(this);

        isStart = false;
        isEnter = false;

        sendMessage(ip, "STOPHANDLE");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isStart) sendMessage(ip, "MOUSEDOWN\n" + e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isStart) sendMessage(ip, "MOUSEUP\n" + e.getButton());
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        isEnter = true;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        isEnter = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (isStart) sendMessage(ip, "MOUSEWHEEL\n" + e.getWheelRotation());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isStart) sendMessage(ip, "KEYDOWN\n" + e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (isStart) sendMessage(ip, "KEYUP\n" + e.getKeyCode());
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);

        g.setColor(Color.YELLOW);
        g.fillOval(mx - 3, my - 3, mx + 3, my + 3);
    }

    private class ReceiveImage implements Runnable {
        @Override
        public void run() {
            try (MulticastSocket ms = new MulticastSocket(3332)) {
                byte[] b = new byte[102400];
                DatagramPacket p = new DatagramPacket(b, 0, b.length);
                ms.joinGroup(InetAddress.getByName("224.3.2" + ip.getHostAddress().substring(
                        ip.getHostAddress().lastIndexOf("."))));

                while (isStart) {
                    ms.receive(p);
                    String[] msg = new String(p.getData(), 0, p.getLength()).split("\n");

                    ByteArrayInputStream in = new ByteArrayInputStream(msg[0].getBytes());
                    image = ImageIO.read(in);
                    in.close();

                    mx = Integer.parseInt(msg[1]);
                    my = Integer.parseInt(msg[2]);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class SendMouse implements Runnable {
        @Override
        public void run() {
            while (isStart) if (isEnter) {
                String msg = "MOUSEMOVE\n" + x + "\n" + y;
                sendMessage(ip, msg);

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
