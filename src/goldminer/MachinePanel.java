package goldminer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MachinePanel extends JPanel{
	BufferedImage machineImg;
	BufferedImage shopBackImg;
	BufferedImage poleImg;
	
	BufferedImage goldImg;
	BufferedImage bagImg;
	BufferedImage stoneImg;
	int error = 0;
	boolean end = false; //表示老虎机的结束
	BufferedImage graph[] = new BufferedImage[3];
	public MachinePanel(){
		try {
			stoneImg = ImageIO.read(getClass().getResource("img/stone.png"));
			bagImg = ImageIO.read(getClass().getResource("img/bag.png"));
			goldImg = ImageIO.read(getClass().getResource("img/gold.png"));
			graph[0] = stoneImg;
			graph[1] = bagImg;
			graph[2] = goldImg;
			poleImg = ImageIO.read(getClass().getResource("img/pole.png"));
			shopBackImg = ImageIO.read(getClass().getResource("img/newshopBack.png"));
			machineImg = ImageIO.read(getClass().getResource("img/new_machine.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				super.mouseReleased(e);
				int x = e.getX();
				int y = e.getY();
				if(x >= 75 && x <= 75 + 90 && y >= 127 && y <= 127 + 30) {
					if(Main.tool_num[6] >= 10) {
						play(10);
					}
					else {
						error = 1;
					}
				}
				else if(x >= 75 && x <= 75 + 90 && y >= 165 && y <= 165 + 30){
					if(Main.tool_num[6] >= 100) {
						play(100);
					}
					else {
						error = 1;
					}
				}
				else if(x >= 75 && x <= 75 + 90 && y >= 202 && y <= 202 + 30) {
					if(Main.tool_num[6] >= 500) {
						play(500);
					}
					else {
						error = 1;
					}
				}
				else if(x >= 750 && x <= 750 + 60 && y >= 75 && y <= 75 + 38) {
					end = true;
				}
				if(error == 1) {
					repaint();
				}
			}
		});
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(shopBackImg, 0,0,Main.getWeight(), Main.getHeight(),null);
		g.drawImage(machineImg, Main.getWeight() / 2 - 225, 150,Main.getWeight() / 2, 375, null);
		g.drawImage(poleImg,667, 187, 75,135,null);
		g.drawImage(graph[0],300, 225, 86, 71, null);
		g.drawImage(graph[1],397, 225, 86, 71, null);
		g.drawImage(graph[2],487, 225, 86, 71, null);
		// 设置字体属性 画提示字
		g.setFont(new Font("黑体", Font.BOLD, 22));
		g.drawString("硬币数:" + Main.tool_num[6], 187, Main.getHeight() - 23);
		g.drawString("当前金钱:" + Main.money, 375, Main.getHeight() - 23);
		g.drawString("目标金钱:" + Main.curtarget, 562, Main.getHeight() - 23);
		g.drawString("选择硬币数启动机器: ", 37, 112);
		g.drawRoundRect(75, 127, 112, 30, 4, 4);
		g.drawRoundRect(75, 165, 112, 30, 4, 4);
		g.drawRoundRect(75, 202, 112, 30, 4, 4);
		g.setColor(Color.green);
        g.fillRect(75, 127, 112, 30);
        g.fillRect(75, 165, 112, 30);
        g.fillRect(75, 202, 112, 30);
        g.setFont(new Font("黑体", Font.BOLD, 22));
        g.setColor(Color.black);
        g.drawString("10 coins",  75, 150);
		g.drawString("100 coins", 75, 187);
		g.drawString("500 coins", 75, 225);
		g.setFont(new Font("黑体", Font.BOLD, 37));
		g.setColor(Color.yellow);
		g.drawString("黄 金 矿 工", 340, 80);
		if(error > 0) {
			g.drawString("拒绝白嫖，从我做起!", 262, 140);
		}
		g.setFont(new Font("黑体", Font.BOLD, 20));
		g.setColor(Color.green);
		g.fillRect(750, 60, 75, 38);
        g.setColor(Color.black);
		g.drawString("下一关", 756, 86);
	}
	public void show() {
		requestFocus();
		while(!end) {
			repaint();
		}
		end = false;
		error = 0;
	}
	public void play(int coinnumber) {
		Main.tool_num[6] -= coinnumber;
		repaint();
		int type[] = new int [3];
		for(int i = 0; i < 3; i++) {
			type[i] = (int)(Math.random() * 3);
			//System.out.println("Type of " + i + " = " + type[i]);
		}
		int pos = 0;
		switch(coinnumber) {
			case 10:
				pos = 0;break;
			case 100:
				pos = 1;break;
			case 500:
				pos = 2;break;
		}
		if(type[0] == 0 && type[1] == 0 && type[2] == 0) {
			int money[] = {20, 200, 2000};
			Main.money += money[pos];
		}
		if(type[0] == 1 && type[1] == 1 && type[2] == 1) {
			int money[] = {5, 55, 265};
			Main.money += money[pos];
		}
		int count = 0;
		for(int i = 0; i < 3; i++) {
			if(type[i] == 2) count += 1;
		}
		int reward_gold[][] = {{10, 110, 530},{20,220,1060},{100,1000,4000}};
		if(count >= 1) {
			Main.money += reward_gold[count - 1][pos];
		}
		graph[0] = stoneImg;
		graph[1] = bagImg;
		graph[2] = goldImg;
		BufferedImage ans[] = {graph[type[0]],graph[type[1]],graph[type[2]]};
		//System.out.println("I'm going to refresh the machine");
		for(int i = 0; i < 3; i++) {
			graph[i] = ans[i];
		}
		repaint();
	}
	
	public static void main(String [] args) {
		JFrame jf = new JFrame();
		jf.setBounds(225, 75, Main.getWeight(), Main.getHeight());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MachinePanel mp = new MachinePanel();
		jf.add(mp);
		jf.setVisible(true);
		mp.show();
	}
	
}
