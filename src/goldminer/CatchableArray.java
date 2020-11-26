package goldminer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javazoom.jl.player.Player;

public class CatchableArray {
	static List<Catchable> catchableSet;
	public static void init() {
		catchableSet = new ArrayList<Catchable>();
		switch (Main.round_num) {
		
			case 1:	
				catchableSet.add(new Gold( 175, 170, 0)); 
				catchableSet.add(new Gold( 300, 320, 0)); 
				catchableSet.add(new Gold( 550, 320, 0));
				catchableSet.add(new Gold( 550, 350, 0));
				catchableSet.add(new Gold( 600, 180, 0));

				catchableSet.add(new Gold( 450, 220, 1));
				catchableSet.add(new Gold( 250, 400, 1));
				catchableSet.add(new Gold( 200, 450, 1));
				
				catchableSet.add(new Gold( 80, 275, 2));
				catchableSet.add(new Gold( 800, 400, 2));
				
				catchableSet.add(new Rock( 300, 200, 0));
				catchableSet.add(new Rock( 630, 200, 0));
				
				catchableSet.add(new Rock( 300, 500, 1));
				catchableSet.add(new Rock( 620, 350, 1));
				
				catchableSet.add(new Bag( 500, 500));
				
				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));


				break;
				
			case 2:
				catchableSet.add(new Gold( 500, 300, 0));
				catchableSet.add(new Gold( 600, 250, 0));
				catchableSet.add(new Gold( 700, 450, 0));
				catchableSet.add(new Gold( 750, 780, 0));
				catchableSet.add(new Gold( 600, 490, 0));
				catchableSet.add(new Gold( 700, 190, 0));

				catchableSet.add(new Gold( 550, 430, 1));
				catchableSet.add(new Gold( 750, 300, 1));
				catchableSet.add(new Gold( 630, 350, 1));
				
				catchableSet.add(new Gold( 50, 450, 2));
				catchableSet.add(new Gold( 750, 450, 2));
				
				catchableSet.add(new Rock( 175, 500, 0));
				catchableSet.add(new Rock( 200, 380, 0));
				catchableSet.add(new Rock( 320, 230, 0));
				
				catchableSet.add(new Rock( 10, 275, 1));
				catchableSet.add(new Rock( 50, 375, 1));
				catchableSet.add(new Rock( 90, 150, 1));
				catchableSet.add(new Rock( 290, 350, 1));
				
				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;
				
			case 3:
				catchableSet.add(new Gold( 200, 200, 0));
				catchableSet.add(new Gold( 200, 250, 0));
				catchableSet.add(new Gold( 250, 200, 0));
				catchableSet.add(new Gold( 600, 250, 0));
		
				catchableSet.add(new Gold( 100, 400, 1));
				catchableSet.add(new Gold( 275, 450, 1));
				catchableSet.add(new Gold( 800, 270, 1));
				
				catchableSet.add(new Gold( 420, 500, 2));

				catchableSet.add(new Rock( 375, 225, 0));
				catchableSet.add(new Rock( 550, 450, 0));
				catchableSet.add(new Rock( 600, 290, 0));

				catchableSet.add(new Rock( 175, 375, 1));
				catchableSet.add(new Rock( 500, 350, 1));
				catchableSet.add(new Rock( 750, 200, 1));
				catchableSet.add(new Rock( 750, 400, 1));

				catchableSet.add(new Bag( 100, 300));

				catchableSet.add(new Diamond( 600, 425));
				
				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;

			case 4:
				catchableSet.add(new Gold( 100, 200, 0));
				catchableSet.add(new Gold( 600, 200, 0));
				catchableSet.add(new Gold( 625, 225, 0));
				catchableSet.add(new Gold( 650, 275, 0));
		
				catchableSet.add(new Gold( 500, 275, 1));
				
				catchableSet.add(new Gold( 350, 450, 2));
				catchableSet.add(new Gold( 575, 425, 2));

				catchableSet.add(new Rock( 650, 325, 0));

				catchableSet.add(new Rock( 200, 475, 1));
				catchableSet.add(new Rock( 175, 225, 1));
				catchableSet.add(new Rock( 750, 225, 1));

				catchableSet.add(new Bag( 200, 375));
				catchableSet.add(new Bag( 450, 400));
				catchableSet.add(new Bag( 575, 335));
				catchableSet.add(new Bag( 700, 260));

				catchableSet.add(new Pig( 150, 325, 100, 300));
				catchableSet.add(new Pig( 300, 350, 250, 450));
				
				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;
				
			case 5:
				catchableSet.add(new Gold( 75, 190, 0));
				catchableSet.add(new Gold( 200, 400, 0));
				catchableSet.add(new Gold( 125, 200, 0));
				catchableSet.add(new Gold( 500, 380, 0));
	
				catchableSet.add(new Gold( 75, 300, 1));
				catchableSet.add(new Gold( 300, 300, 1));
				catchableSet.add(new Gold( 720, 250, 1));
				
				catchableSet.add(new Gold( -50, 450, 2));
				catchableSet.add(new Gold( 300, 480, 2));
				catchableSet.add(new Gold( 850, 350, 2));

				catchableSet.add(new Rock( 800, 450, 0));

				catchableSet.add(new Rock( 450, 250, 1));
				catchableSet.add(new Rock( 600, 275, 1));
				catchableSet.add(new Rock( 680, 525, 1));
				
				catchableSet.add(new Bag( 550, 425));
				
				catchableSet.add(new Diamond( 150, 425));
				catchableSet.add(new Diamond( 650, 475));
				catchableSet.add(new Diamond( 775, 475));
				catchableSet.add(new Diamond( 800, 375));

				catchableSet.add(new Pig( 200, 275, 100, 300));
				catchableSet.add(new Pig( 750, 400, 600, 750));
				
				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;
				
			case 6:
				catchableSet.add(new Gold( 200, 400, 0));
				catchableSet.add(new Gold( 250, 500, 0));
				catchableSet.add(new Gold( 500, 400, 0));
				catchableSet.add(new Gold( 600, 425, 0));
				catchableSet.add(new Gold( 650, 400, 0));
				catchableSet.add(new Gold( 625, 500, 0));
				catchableSet.add(new Gold( 700, 400, 0));

				catchableSet.add(new Gold( 50, 450, 1));
				catchableSet.add(new Gold( 175, 500, 1));
				catchableSet.add(new Gold( 600, 530, 1));
				
				catchableSet.add(new Gold( 300, 500, 2));

				catchableSet.add(new Rock( 200, 300, 0));
				catchableSet.add(new Rock( 425, 300, 0));
				catchableSet.add(new Rock( 600, 200, 0));

				catchableSet.add(new Rock( 75, 175, 1));
				catchableSet.add(new Rock( 475, 425, 1));
				catchableSet.add(new Rock( 800, 475, 1));
				
				catchableSet.add(new Bag( 650, 425));
				
				catchableSet.add(new DiamondPig( 175, 250, 100, 300));
				catchableSet.add(new DiamondPig( 425, 425, 200, 400));
				catchableSet.add(new DiamondPig( 450, 500, 400, 550));
				catchableSet.add(new DiamondPig( 650, 275, 600, 800));

				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				
				break;
				
			case 7:
				catchableSet.add(new Gold( 100, 200, 0));
				catchableSet.add(new Gold( 450, 200, 0));
				
				catchableSet.add(new Gold( 50, 200, 1));
				catchableSet.add(new Gold( 800, 200, 1));
				
				catchableSet.add(new Gold( 200, 450, 2));
				catchableSet.add(new Gold( 400, 500, 2));
				catchableSet.add(new Gold( 600, 450, 2));

				catchableSet.add(new Bag( 700, 250));

				catchableSet.add(new Pig( 50, 400, 50, 200));
				catchableSet.add(new Pig( 200, 300, 100, 300));
				catchableSet.add(new Pig( 200, 200, 200, 300));
				catchableSet.add(new Pig( 650, 350, 600, 750));
				
				catchableSet.add(new Bone( 250, 250));
				catchableSet.add(new Bone( 600, 275));

				catchableSet.add(new Skull( 620, 260));
				catchableSet.add(new Skull( 700, 425));

				catchableSet.add(new Bomb( 325, 425));
				catchableSet.add(new Bomb( 500, 425));

				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;
				
			case 8:

				catchableSet.add(new Bag( 20, 450));
				catchableSet.add(new Bag( 820, 450));

				catchableSet.add(new Bomb( 425, 250));
				catchableSet.add(new Bomb( 325, 350));
				catchableSet.add(new Bomb( 525, 350));
				catchableSet.add(new Bomb( 225, 450));
				catchableSet.add(new Bomb( 625, 450));

				catchableSet.add(new DiamondPig( 175, 400, 100, 300));
				catchableSet.add(new DiamondPig( 300, 300, 200, 400));
				catchableSet.add(new DiamondPig( 600, 250, 550, 750));

				catchableSet.add(new Diamond( 400, 250));
				catchableSet.add(new Diamond( 505, 225));
				catchableSet.add(new Diamond( 625, 325));
				catchableSet.add(new Diamond( 200, 425));
				catchableSet.add(new Diamond( 650, 425));
				catchableSet.add(new Diamond( 465, 525));
				catchableSet.add(new Diamond( 390, 525));
				catchableSet.add(new Diamond( 540, 525));

				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;
				
			case 9:

				catchableSet.add(new Bag( 5, 250));
				catchableSet.add(new Bag( 400, 500));
				catchableSet.add(new Bag( 800, 400));


				catchableSet.add(new Bomb( 200, 200));
				catchableSet.add(new Bomb( 650, 200));
				catchableSet.add(new Bomb( 300, 350));
				catchableSet.add(new Bomb( 400, 350));
				catchableSet.add(new Bomb( 500, 350));

				catchableSet.add(new DiamondPig( 100, 275, 50, 200));
				catchableSet.add(new DiamondPig( 700, 275, 600, 800));
				catchableSet.add(new DiamondPig( 600, 350, 550, 750));
				catchableSet.add(new DiamondPig( 200, 375, 150, 250));


				catchableSet.add(new Diamond( 350, 300));
				catchableSet.add(new Diamond( 450, 300));
				catchableSet.add(new Diamond( 325, 550));
				catchableSet.add(new Diamond( 475, 550));
				catchableSet.add(new Diamond( 100, 375));
				catchableSet.add(new Diamond( 800, 525));
				catchableSet.add(new Diamond( 540, 225));

				catchableSet.add(new Gold( 150, 450, 2));
				catchableSet.add(new Gold( 650, 450, 2));
				
				catchableSet.add(new Bone( 400, 450));
				
				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				break;
				
			case 10:

				catchableSet.add(new Gold( 100, 170, 0)); 
				catchableSet.add(new Gold( 350, 320, 0)); 
				catchableSet.add(new Gold( 600, 270, 0));
				catchableSet.add(new Gold( 550, 350, 0));
				catchableSet.add(new Gold( 600, 180, 0));
				catchableSet.add(new Gold( 800, 280, 0));


				catchableSet.add(new Gold( 450, 325, 1));
				catchableSet.add(new Gold( 250, 450, 1));
				catchableSet.add(new Gold( 200, 300, 1));
				catchableSet.add(new Gold( 500, 250, 1));
				catchableSet.add(new Gold( 300, 400, 1));
				catchableSet.add(new Gold( 150, 450, 1));
				catchableSet.add(new Gold( 400, 350, 1));
				catchableSet.add(new Gold( 100, 200, 1));
				catchableSet.add(new Gold( 700, 450, 1));
				catchableSet.add(new Gold( 800, 220, 1));

				catchableSet.add(new Gold( 50, 400, 2));
				catchableSet.add(new Gold( 350, 475, 2));
				catchableSet.add(new Gold( 600, 350, 2));
				catchableSet.add(new Gold( 800, 350, 2));
				catchableSet.add(new Gold( 780, 500, 2));
				
				catchableSet.add(new Rock( 200, 400, 1));
				catchableSet.add(new Rock( 500, 400, 1));
				catchableSet.add(new Rock( 400, 250, 1));
				catchableSet.add(new Rock( 200, 200, 1));
				catchableSet.add(new Rock( 700, 200, 1));

				catchableSet.add(new Coin( 0, 555));
				catchableSet.add(new Coin( 880, 555));
				
				break;
				
				
		}
		
	}
	
	//更新物品 对应catchable类的两个函数
	//带参数的是被抓住物品的移动
	//不带参的是物品自己的移动
	//同时删除物品
	public static void update(double speed, double angle) {
		//处理爆炸
		for(int i = 0; i < catchableSet.size(); i++) {
			Catchable tmp = catchableSet.get(i);
			if (tmp.bombFlag > 1)
			{
				
				new Thread(new Runnable() {
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
				}).start();
				for(int j = 0; j < catchableSet.size(); j++) {
					Catchable tmp2 = catchableSet.get(j);
					if (j == i)
						continue;
					if (((Bomb)tmp).inRange(tmp2))
						tmp2.bomb();
				}
			}
				
		}
		
		for(int i = 0; i < catchableSet.size(); i++) {
			Catchable tmp = catchableSet.get(i);
			//删除物品
			if(tmp.deleteFlag == true) {
				catchableSet.remove(i);
				continue;
			}
			//更新物品
			if (tmp.caughtFlag == true) 
				tmp.update(-speed*Math.sin(angle),-speed*Math.cos(angle));
			else
				tmp.update();
		}
	}
	
}
