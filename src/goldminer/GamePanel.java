package goldminer;

import java.util.*;
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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javazoom.jl.player.Player;

public class GamePanel extends JPanel {
	
	BufferedImage backgroundImg;
	BufferedImage mySelfImg[]= new BufferedImage[5];
	BufferedImage tool3Img;
	BufferedImage boomImg[] = new BufferedImage[3];
	BufferedImage clawImg;
	BufferedImage rotatedImg;	
	int mepos = 0;
	int meheavypos = 3;
	int meheavynum = 8;
	int menum = 0;
	int boom_explode = 0;
	int boom_ex_time = 2;
	int explode = 0;
	int ex_time = 2;
	int power_num = 0;
	boolean played = false;
	Player player = null;
	//暂停还是继续
	private int pauseFlag = 1;
	Claw c;
	int time = Main.time;
	
	public GamePanel() {
		//缓冲图片
		try {
			boomImg[0] = ImageIO.read(getClass().getResource("img/boom1.png"));
			boomImg[1] = ImageIO.read(getClass().getResource("img/boom2.png"));
			boomImg[2] = ImageIO.read(getClass().getResource("img/boom3.png"));
			
			tool3Img = ImageIO.read(getClass().getResource("img/tool3.png"));
			backgroundImg = ImageIO.read(getClass().getResource("img/background.png"));
			mySelfImg[0] = ImageIO.read(getClass().getResource("img/me1.png"));
			mySelfImg[1] = ImageIO.read(getClass().getResource("img/me2.png"));
			mySelfImg[2] = ImageIO.read(getClass().getResource("img/me_power.png"));
			mySelfImg[3] = ImageIO.read(getClass().getResource("img/meheavy1.png"));
			mySelfImg[4] = ImageIO.read(getClass().getResource("img/meheavy2.png"));
			clawImg = ImageIO.read(getClass().getResource("img/claw1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		c = new Claw();
		
		//监听鼠标 暂停
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if (e.getX() > 660 && e.getY() > 30 && e.getX() < 720 && e.getY() < 90)
					pauseFlag *= -1;
				repaint();
			}
		});
		
		//监听键盘 出钩/扔炸弹
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				super.keyReleased(e);
				int keyCode = e.getKeyCode();
				int bombnum = Main.tool_num[3];
				// 修改钩子状态
				if (keyCode == KeyEvent.VK_DOWN) {
					mepos = 1;
					c.stretch();
				}
				else if (explode == 0 && keyCode == KeyEvent.VK_UP && bombnum > 0 && 
						c.getItem() != null && c.getstateFlag() == 2) 
				{
					//删除物品
					c.getItem().deleteFlag = true;
					c.setItem(null);
					c.stretchSpeed = 5;
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								File file = new File("explosive.mp3");
								FileInputStream fis = new FileInputStream(file);
								BufferedInputStream stream = new BufferedInputStream(fis);
								Player tmpplayer = new Player(stream);
								tmpplayer.play();
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					t.setPriority(10);
					t.start();
					//允许放炸弹
					explode = 3;
					Main.tool_num[3] -= 1;
				}
			}
		});
	
	}
	
	
	public void paint(Graphics g) {

		super.paint(g);
		
		// 画背景
		g.drawImage(backgroundImg, 0, 0, Main.getWeight(), Main.getHeight(), null);
		//画人
		if(Main.powernum > 0) {
			g.drawImage(mySelfImg[2], (Main.getWeight() / 2 - 52), 37, 150, 75, null);
			Main.powernum -= 1;
		}
		else if(c.getItem() == null){
			if(c.getstateFlag() != 2 || menum < 5) {
				g.drawImage(mySelfImg[mepos], (Main.getWeight() / 2 - 52), 37, 150, 75, null);
				if(c.getstateFlag() == 2 && menum < 5) {
					menum += 1;
				}
			}
			else if(c.getstateFlag() == 2){
				menum = 0;
				mepos = 1 - mepos;
				g.drawImage(mySelfImg[mepos], (Main.getWeight() / 2 - 52), 37, 150, 75, null);
			}
		}
		else {
			//抓到物品,肯定是上升的时候
			if(meheavynum == 0) {
				meheavypos = 7 - meheavypos;
				meheavynum = 8;
			}
			g.drawImage(mySelfImg[meheavypos], (Main.getWeight() / 2 - 52), 37, 150, 75, null);
			meheavynum -= 1;
		}
		//画炸药
		if(Main.tool_num[3] > 0) {
			for(int i = 0; i < Main.tool_num[3];i++) {
				g.drawImage(tool3Img, 112 + Main.getWeight() / 2 + 28 * i , 60, 28, 40, null);
			}
		}
		
		
		//画出所有的物品
		for(int i = 0; i < CatchableArray.catchableSet.size(); i++) {
			Catchable tmpitem = CatchableArray.catchableSet.get(i);
			g.drawImage(tmpitem.getImg(c.getAngle()), tmpitem.getX(), tmpitem.getY(),tmpitem.size2(), tmpitem.Size, null);
		}
		
		//扔炸弹
		if(explode != 0) {
			g.drawImage(boomImg[3 - explode], c.getX(), c.getY(),75, 75, null);
			if(ex_time != 0) {
				ex_time -= 1;
			}
			else {
				explode -= 1;
				ex_time = 3;
			}
		}
		// 设置2D画笔 颜色粗细
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(2.0f));

		// 画细线
		g2.drawLine(Main.getStartX(), Main.getStartY(), c.getX(), c.getY());

		// 画爪子
		if (c.getItem()==null) {
			rotatedImg = rotateImg.rotateImage(clawImg, c.getAngle(),52,41);
			g.drawImage(rotatedImg, c.getX()-38, c.getY()-28, 75, 75, null);
		}
		
		// 设置字体属性 画提示字
		g.setFont(new Font("宋体", Font.BOLD, 22));
		g.drawString("金钱:" + Main.money, 37, 45);
		g.drawString("目标钱数:" + Main.curtarget, 37, 90);
		g.drawString("时间:" + time / 1000, 750, 45);
		g.drawString("第 " + Main.round_num + " 关", 750, 90);
		if (pauseFlag == 1)
			g.drawString("暂停", 667, 67);
		else
			g.drawString("继续", 667, 67);
		g.drawRoundRect(660, 30, 60, 60, 4, 4);
	}

	
	public void show() {
		//获取焦点
		requestFocus();
		
		//初始化
		pauseFlag = 1;
		time = Main.time;
		c.inition();
		repaint();
		
		//死循环
		while(true) {	
			requestFocus();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (pauseFlag == 1) {
				//钩子行为
				c.move();
				//更新物品 包括爆炸和删除
				CatchableArray.update(c.getspeed(), c.getAngle());
				//music
				if(time <= 10 * 1000 && played == false && Main.money + 500 < Main.curtarget) {
					Thread t = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								File file = new File("music_10.mp3");
								FileInputStream fis = new FileInputStream(file);
								BufferedInputStream stream = new BufferedInputStream(fis);
								player = new Player(stream);
								player.play();
								player.close();
							} catch (Exception e) {
								// TODO: handle exception
							}
						}
					});
					t.setPriority(10);
					t.start();
					played = true;
				}
				//刷新
				repaint();
				
				time -= 20;
				if (time == 0) {
					break;
				}
			}
					
		}
		
	}
	
}
