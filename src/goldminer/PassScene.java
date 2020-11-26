package goldminer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Polygon;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.tools.Tool;

public class PassScene extends JPanel{
	boolean ifpassed;
	BufferedImage passscene;
	
	public PassScene(boolean pstate){
		try {
			if(pstate == true)
				passscene = ImageIO.read(getClass().getResource("img/passscene1.png"));
			else
				passscene = ImageIO.read(getClass().getResource("img/passscene2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(passscene, 0, 0, Main.getWeight(), Main.getHeight(), null);
	}
	
	public void show() {
		requestFocus();
		repaint();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
