package goldminer;

import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.*; 

public class Main {
	private static int height = 600;
	private static int weight = 900;
	//金钱
	public static int money= 0;
	//倒计时
	public static int time=60*1000;
	//关卡数
	public static int round_num=1;
	//每一关目标钱数
	public static int round_target[]=new int[10];
	//当前关卡目标钱数
	public static int curtarget=0;
	//从袋子中抓到生力水
	public static int powernum = 0;
	//道具数，0生力水，1幸运草，2石头收藏书，3炸药，4优质钻石，5骨头收藏书，6硬币
	public static int[] tool_num=new int[7];
	
	//public static int screenSize = Toolkit.getDefaultToolkit().getScreenResolution();
	
	public static void main(String[] args) {
		//每一关目标钱数
		round_target[0]=650;
		round_target[1]=1195;
		round_target[2]=2010;
		round_target[3]=3095;
		round_target[4]=4450;
		round_target[5]=6075;
		round_target[6]=7970;
		round_target[7]=10135;
		round_target[8]=12570;
		round_target[9]=15511551;
		
		JFrame jf = new JFrame();
		jf.setBounds(0,0,900,630);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MachinePanel mp = new MachinePanel();
		GamePanel gp = new GamePanel();
		ShopPanel sp = new ShopPanel();
		PassScene ps;
		while (money >= curtarget){
			curtarget=round_target[round_num-1];
			CatchableArray.init();;
			jf.add(gp);
			jf.setVisible(true);
			System.out.print('0');
			gp.show();
			System.out.print('1');
			jf.remove(gp);
			

			for(int i = 0; i < 7; i++) {
				if(i != 3 && i != 6)
					tool_num[i] = 0;
			}
			
			if(money >= curtarget) ps = new PassScene(true);
			else {
				ps = new PassScene(false);
			}
			jf.add(ps);
			jf.setVisible(true);
			System.out.print('2');
			ps.show();
			System.out.print('3');
			jf.remove(ps);
			if(money < curtarget) {
				break;
			}
			
			jf.add(sp);
			jf.setVisible(true);
			System.out.print('4');
			sp.buy();
			System.out.print('5');
			jf.remove(sp);
			
			jf.add(mp);
			jf.setVisible(true);
			System.out.print('6');
			mp.show();
			System.out.print('7');
			jf.remove(mp);
		
			round_num += 1;

		}
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static void setHeight(int height) {
		Main.height = height;
	}

	public static int getWeight() {
		return weight;
	}

	public static void setWeight(int weight) {
		Main.weight = weight;
	}
	public static int getStartX() {
		return weight / 2 - 3;
	}
	public static int getStartY() {
		return 110 - 15;
	}
}
