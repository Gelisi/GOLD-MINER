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

//import sun.awt.AWTAccessor.MouseEventAccessor;
//import org.graalvm.compiler.hotspot.replacements.NewObjectSnippets;

public class ShopPanel extends JPanel {

	int FrameHeight=600,FrameWidth=900;
	int nowTime=0; // 运行的时间
	int show_num = 10;
	int buy_num = 0;
	boolean stop_fresh = false;
	int shoper_num = 0;

	// 道具类
	class Tool{
		BufferedImage Image;  // 原始图
		BufferedImage Image_; // 鼠标悬浮时高亮的图
		boolean chosen=false; // 该轮是否被选择
		int pos; // 道具在桌子上的位置
		int price; // 工具的价格
		int price_upper_bound;
		int price_lower_bound;
		String description=new String();
	}
	int toolNum=6; // 可选择的道具总数
	int toolChooseNum=4; // 桌上可摆放的道具总数
	Tool[] toolSet=new Tool[toolNum]; // 所以可供选择的道具列表 
	int[] upperBound={600,500,100,150,500,100};
	int[] lowerBound={200,50, 20, 50, 200,20 };

	BufferedImage shopback;
	BufferedImage shoper[] = new BufferedImage[5];
	BufferedImage tip;
	BufferedImage table;

	String tipSentence="点击下面的物品即可进行买卖，买好物品后点击下一关继续游戏。";
	int tipLen=tipSentence.length();

	// toolX,Y 最左道具的左上角坐标，toolX2最右道具的X坐标，toolHeight,Width,高&宽
	int toolX=112,toolX2=562,space=150,toolY=375,toolHeight=75,toolWidth=75,descriptionY=510;
	int desLen=27,describeSpace=37; // 每行描述字符的上限
	boolean[] remain;  // 道具是否还在桌上
	boolean[] describe;  // 是否要描述该道具(remain条件下才可能describe)
	boolean[] highlight; // 道具是否被高亮
	boolean breakFlag = false;

	// 构造函数
	public ShopPanel() {
		//restoration();
		for(int i=0;i<toolNum;i++) toolSet[i]=new Tool();

		// 缓冲图片
		try{
			shopback=ImageIO.read(getClass().getResource("img/shopBack.png"));
			shoper[0]=ImageIO.read(getClass().getResource("img/shoper1.png"));
			shoper[1]=ImageIO.read(getClass().getResource("img/shoper2.png"));
			shoper[2]=ImageIO.read(getClass().getResource("img/shoper3.png"));
			shoper[3]=ImageIO.read(getClass().getResource("img/shoper4.png"));
			shoper[4] = ImageIO.read(getClass().getResource("img/shopermad.png"));
			tip=ImageIO.read(getClass().getResource("img/tip.png"));
			table=ImageIO.read(getClass().getResource("img/tableImage.png"));
			// 道具组
			for(int i=0;i<toolNum;i++){
				toolSet[i].Image=ImageIO.read(getClass().getResource("img/tool"+i+".png"));
				toolSet[i].Image_=ImageIO.read(getClass().getResource("img/tool"+i+"_.jpeg"));
			}
		}catch(IOException e){
			e.printStackTrace();
			System.err.println("Image not found.");
		}	
		
		// 读入对道具的描述
		String path="tool_descriptions.txt";
		BufferedReader descriptions=null;
		try{
			descriptions=new BufferedReader(new FileReader(path));
		}catch(IOException ex){
			System.err.println("cannot find file: "+path);
		}
		try{
			for(int i=0;i<toolNum;i++){
				toolSet[i].description=descriptions.readLine();
			}
		}catch(Exception e){
			System.out.println("error");
		}

		// 鼠标点击事件 （购买道具）
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased (MouseEvent e){
				super.mouseReleased(e);
				int x=e.getX(),y=e.getY(); // 鼠标点击的坐标
				
				if(y>=toolY && y<=toolY+toolHeight){
					for(int p=0;p<toolChooseNum;p++){
						if(x>=toolX+p*space && x<=toolX+p*space+toolWidth){ // 如果点击了道具
							if(remain[p]){ // 如果道具还没被购买
								for(int i=0;i<toolNum;i++){
									if(toolSet[i].chosen && toolSet[i].pos==p && Main.money >= toolSet[i].price){
										remain[p]=false;  // 取消显示该道具的image & price
										Main.money -= toolSet[i].price;
										Main.tool_num[i]++;
										buy_num += 1;
									}
								}
							}
						}
					}
				}
				else if (x > 750 && x < 825 && y > 60 && y < 98)
					breakFlag = true;
			}
		});

		this.addMouseMotionListener(new MouseAdapter(){
			@Override
			public void mouseMoved(MouseEvent e){
				super.mouseMoved(e);
				int x=e.getX(),y=e.getY();
				for(int tmp=0;tmp<toolChooseNum;tmp++) highlight[tmp]=false; // 取消显示原来的介绍
				if(y>=toolY && y<=toolY+toolHeight){
					for(int p=0;p<toolChooseNum;p++){
						if(x>=toolX+p*space && x<=toolX+p*space+toolWidth){ // 如果悬浮在该道具上
							if(remain[p]){ // 如果道具还没被购买
								for(int i=0;i<toolNum;i++){
									if(toolSet[i].chosen && toolSet[i].pos==p){
										// 取消显示原来的介绍,只在被下一个介绍替代的时候。被购买后也仍有原介绍显示。
										for(int tmp=0;tmp<toolChooseNum;tmp++) describe[tmp]=false; 
										describe[p]=true; // 显示新的介绍
										highlight[p]=true;
									}
								}
							}
						}
					}
				}
			}
		});

	}

	
	// 画图
	public void paint(Graphics g){
		super.paint(g);
		
		// 背景图
		g.drawImage(shopback,0,0,FrameWidth,FrameHeight,null);

		if(!breakFlag)
			g.drawImage(shoper[shoper_num],FrameWidth/4*3,250,225,225,null);
		else if(buy_num == 0){
			//mad
			g.drawImage(shoper[4],FrameWidth/4*3,250,225,225,null);
		}
		else {
			g.drawImage(shoper[0],FrameWidth/4*3,250,225,225,null);
			buy_num = 0;
		}
		if(stop_fresh == false) {
			if(show_num == 0) {
				shoper_num = (shoper_num + 1) % 4;
				show_num = 10;
			}
			else {
				show_num --;
			}
		}
		else {
			shoper_num = 0;
		}
		g.drawImage(tip,160,187,525,135,null);
		g.drawImage(table,0,450,900,150,null);
		
		//下一关按钮
		g.drawRect(750, 60, 75, 38);
		g.setColor(Color.green);
        g.fillRect(750, 60, 75, 38);
		g.setColor(Color.black);
		g.setFont(new Font("黑体", Font.BOLD, 20));
		g.drawString("下一关", 756, 86);
		g.setColor(Color.yellow);
		g.setFont(new Font("黑体", Font.BOLD, 25));
		g.drawString("金钱: " + Main.money, 38, 60);
		g.drawString("目标钱数: " + Main.curtarget, 38, 105);
		
		// 画出道具
		for(int p=0;p<toolChooseNum;p++){
			if(remain[p]){
				for(int i=0;i<toolNum;i++){
					if(toolSet[i].chosen && toolSet[i].pos==p){
						if(highlight[p]) g.drawImage(toolSet[i].Image_,toolX+space*p,toolY,toolWidth,toolHeight,null);
						else g.drawImage(toolSet[i].Image,toolX+space*p,toolY,toolWidth,toolHeight,null);
					}
				}
			}
		}

		// 道具价格的笔画
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		g2.setStroke(new BasicStroke(3.0f)); // 线条粗细
		g.setFont(new Font("黑体", Font.BOLD, 25)); // 字体属性
		// 商店道具的随机价格
		for(int p=0;p<toolChooseNum;p++) {
			if(remain[p]){
				for(int i=0;i<toolNum;i++){
					if(toolSet[i].chosen && toolSet[i].pos==p){
						g.drawString("$"+toolSet[i].price, toolX+space*p, toolY+toolHeight+25);
					}
				}
			}
		}

		// 道具描述的画笔
		g2.setColor(Color.YELLOW);
		g2.setStroke(new BasicStroke(3.0f)); // 线条粗细
		g.setFont(new Font("黑体", Font.BOLD, 25)); // 字体属性
		// 鼠标悬浮在的道具的描述
		for(int p=0;p<toolChooseNum;p++) {
			if(describe[p]){
				for(int i=0;i<toolNum;i++){
					if(toolSet[i].chosen && toolSet[i].pos==p){
						int begin=0,end=desLen,n=0;
						while(true){
							if(end<=toolSet[i].description.length()){
								String line=toolSet[i].description.substring(begin, end); // 每行取子串
								g.drawString(new String(line), toolX, descriptionY+n*describeSpace);
								begin+=desLen;
								end+=desLen;
								n++;
							}
							else{
								String line=toolSet[i].description.substring(begin,toolSet[i].description.length());
								g.drawString(new String(line), toolX, descriptionY+n*describeSpace);
								break;
							}
						}
					}
				}
			}
		}
		
		// ”黄金矿工“大字
		g2.setColor(Color.DARK_GRAY); // 阴影
		g2.setStroke(new BasicStroke(3.0f)); 
		g.setFont(new Font("黑体", Font.BOLD, 70));
		int titleX=300,titleY=127,delta=-3; 
		g.drawString("黄 金 矿 工", titleX,titleY);
		g2.setColor(Color.YELLOW);
		g.drawString("黄 金 矿 工", titleX+delta,titleY+delta);
		
		// 逐字输出Tips
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3.0f)); // 线条粗细
		g.setFont(new Font("黑体", Font.BOLD, 25)); // 字体属性
		boolean flag=true;
		int T=0;  // 计算时间以便逐字输出
		if(flag){
			T=nowTime/100+1;
			T=Math.max(T, 1);
			if (T>tipLen) flag=false;
		}
		if(T<=18){
			String Sentence1=tipSentence.substring(0,T);
			g.drawString(Sentence1, 190, 225);
		}
		else{

			T=Math.min(T,tipSentence.length());
			String Sentence1=tipSentence.substring(0,18);
			String Sentence2=tipSentence.substring(18,T);
			g.drawString(Sentence1, 190, 225);
			g.drawString(Sentence2, 190, 263);
			stop_fresh = true;
		}
	}


	// 初始化
	public void initData(){
		nowTime=0;
		for(int i=0;i<toolNum;i++) {
			toolSet[i].chosen=false;
			toolSet[i].price_upper_bound=upperBound[i];
			toolSet[i].price_lower_bound=lowerBound[i];
		}
		random_tools();
		breakFlag = false;
	}

	// 随机选择该轮出现的道具及其价格
	public void random_tools(){

		int tmp1=new Random().nextInt(4)+1;
		int tmp2=new Random().nextInt(4)+1;
		toolChooseNum=(tmp1+tmp2+1)/2; // 道具数为1,2,3,4的概率为1:5:7:3
		space=(toolX2-toolX)/toolChooseNum;  // 道具的间距大小根据道具数而改变

		remain=new boolean[toolChooseNum];  // 根据道具数初始化数组
		describe=new boolean[toolChooseNum];  
		highlight = new boolean[toolChooseNum]; 
		for(int i=0;i<toolChooseNum;i++) {
			remain[i]=true;
			describe[i]=false;
			highlight[i]=false;
		}

		int cnt=0;
		// jy爸爸要求增加炸药出现的概率
		long t = System.currentTimeMillis();
		Random rand=new Random(t);
		double bomb=rand.nextInt(2);
		if(bomb<0.4){      // 额外增加0.4的概率出现炸弹(index 3)
			toolSet[3].chosen=true;
			toolSet[3].pos=cnt;
			int lwr=toolSet[3].price_lower_bound, upr=toolSet[3].price_upper_bound;
			toolSet[3].price=new Random().nextInt(upr-lwr)+1+lwr;  // 随机该道具的价格
			cnt++;
		}
		while(true){  // 选择toolChooseNum个道具
			if(cnt==toolChooseNum) break;
			int tmp=(int)(Math.random()*toolNum); // 生成[0,toolNum)中一个随机的int值

			if (toolSet[tmp].chosen==false){
				toolSet[tmp].chosen=true; // 该道具被选择
				toolSet[tmp].pos=cnt;
				int lwr=toolSet[tmp].price_lower_bound, upr=toolSet[tmp].price_upper_bound;
				toolSet[tmp].price=new Random().nextInt(upr-lwr)+lwr+1;  // 随机该道具的价格
				cnt++;
			}
		}
	}


	public void buy(){
		initData();

		requestFocus(); // 获取焦点
		
		while(!breakFlag){
			//刷新间隔为20ms刷新一次
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 刷新时间
			if(nowTime<100000){ nowTime+=20; }
			//刷新（重新绘图）
			repaint();
		}
		repaint();
		try {
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
