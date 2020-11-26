package goldminer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Catchable {
    // x和y代表元素的横纵坐标，val表示元素的价值，size表示元素的显示大小
	double X;
	double Y;
	int Val;
	double Speed;
	public int Size;
    BufferedImage ItemImg;
    BufferedImage CaughtImg;
    public boolean caughtFlag;
    public boolean deleteFlag;
    //0表示不被炸，1表示物品被炸，2表示炸弹被炸
    int bombFlag;
    //构造函数
    public Catchable(int x, int y, String ImgPath, String ImgPath2, int val, int size, double speed) {
        X = x;
        Y = y;
        Val = val;
        Size = size;
        Speed = speed;
        caughtFlag = false;
        deleteFlag = false;
        bombFlag = 0; 
        try {
			ItemImg = ImageIO.read(getClass().getResource(ImgPath));
			CaughtImg = ImageIO.read(getClass().getResource(ImgPath2));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public BufferedImage getImg(double d) {
    	if (caughtFlag)
    		return rotateImg.rotateImage(CaughtImg, d, 100, 41);		
    	else
    		return ItemImg;
    }
    public int getX() {
    	return (int)X;
    }
    public int getY() {
    	return (int)Y;
    }

    //手动更新坐标 用于抓到东西后的更新
    public void update(double delta_x, double delta_y) {
    	X += delta_x;
    	Y += delta_y;
    }
    
    //自动更新坐标 只用于猪
    public void update() {
    	
    }

    // 用于让猪是长方形
    public int size2() {
    	return Size;
	}
    
    //抓回来时返回价值或者更新道具情况
    public int getVal() {
    	return Val;
    }
    //抓到即触发
    public void touch(double clawX, double clawY, double angle) {
    	X = clawX-75+4.5*Math.sin(angle);Y = clawY-26-4*Math.cos(angle);
    	Size = 150;
    	caughtFlag = true;
    }
    //被炸掉
    public void bomb() {
    	bombFlag = 1;
    	deleteFlag = true;
    }
}

class Moveable extends Catchable{
	private int dir;// 0向左，1向右
	private int left;
	private int right;
	BufferedImage ItemImgAnother;
	BufferedImage CaughtImgAnother;
	public Moveable(int xx, int yy, String ImgPath, String ImgPath2, String ImgPath3,String ImgPath4, int val, int size, int l, int r, double speed) {
		super(xx,yy, ImgPath, ImgPath2, val, size, speed);
		dir = (int)(Math.random() * 2);
		left = l;
		right = r;
		try {
			ItemImgAnother = ImageIO.read(getClass().getResource(ImgPath3));
			CaughtImgAnother = ImageIO.read(getClass().getResource(ImgPath4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//每次刷新移动
	public void update() {
        if (dir == 0) {
            X -= 1;
            if (X < left)
                dir = 1;
        }
        else {
            X += 1;
            if (X > right)
                dir = 0;
        }
    }
	public BufferedImage getImg(double d) {
		if (caughtFlag)
		{
			System.out.println(Size);
			System.out.println(d);
			if (dir == 1)
				return rotateImg.rotateImage(CaughtImg, d, 100, 41);
			else
				return rotateImg.rotateImage(CaughtImgAnother, d, 100, 41);
		}
		if (dir == 1)
			return ItemImg;
		else
			return ItemImgAnother;
    }
    public int size2() {
    	if (caughtFlag)
    		return Size;
    	else
    		return (int)(1.5 * Size);
	}
}
// 金块
class Gold extends Catchable {
    // kind表示金块的大小，0表示最小的，2表示最大的
	private static int[] val_ = {100, 250, 500};
	private static int[] size_ = {22, 45, 90};
	private static String[] imgpath = {"img/clawgold1.png","img/clawgold2.png","img/clawgold3.png"};
	private static double[] speed_ = {6, 3, 1};
    public Gold(int xx, int yy, int kind) {
        super(xx, yy, "img/gold.png", imgpath[kind], val_[kind], size_[kind], speed_[kind]);
    }
}

// 钻石
class Diamond extends Catchable {
    public Diamond(int xx, int yy) {
        super(xx, yy, "img/diamond.png","img/clawdiamond.png", 600, 19, 6);
    }
  //重载 优质钻石
    public int getVal() {
    	if (Main.tool_num[4] == 1)
    		return Val+300;
    	else
    		return Val;
    }
}

// 石头
class Rock extends Catchable {
    // kind表示石头大小，0是小石头，1是大石头
	private static int[] val_ = {11, 20};
	private static int[] size_ = {40, 60};
	private static double[] speed_ = {1, 0.8};
	private static String[] imgpath = {"img/clawstone1.png","img/clawstone2.png"};
	
    public Rock(int xx, int yy, int kind) {
        super(xx, yy, "img/stone.png", imgpath[kind], val_[kind], size_[kind],speed_[kind]);
    }
    //重载 石头收藏
    public int getVal() {
    	if (Main.tool_num[2] == 1)
    		return Val*5;
    	else
    		return Val;
    }
}

// 猪
class Pig extends Moveable {
    public Pig(int xx, int yy, int Left, int Right) {
        super(xx,yy,"img/pig.png","img/clawpig.png", "img/pig_.png","img/clawpig_.png", 2, 30,Left,Right, 6);
    }
    
}

//钻石猪
class DiamondPig extends Moveable {
	
	public DiamondPig(int xx, int yy, int Left, int Right) {
        super(xx,yy,"img/pigdiamond.png","img/clawpigdiamond.png","img/pigdiamond_.png","img/clawpigdiamond_.png",602, 30,Left,Right, 6);
    }
	//重载 优质钻石
    public int getVal() {
    	if (Main.tool_num[4]==1)
    		return Val+300;
    	else
    		return Val;
    }
   
}

// 色袋，kind随机
// ********如果认为kind需要指定的话就修改一下***********
class Bag extends Catchable {
    int kind;  // 道具or钱
    public Bag(int xx, int yy) {
        super(xx, yy,"img/bag.png","img/clawbag.png", 0, 40, (6-Math.random() * 5));
        
        if ((int)(Math.random() * 4) == 3) 
            kind = 1;
        else 
            kind = 0;
        
    }
    //重载 幸运草
    public int getVal() {
    	//幸运草加钱
    	if (Main.tool_num[1] == 1 && kind == 0)
    		return (int) (Math.random() * 400)+400;
    	//更新道具
    	else if (kind == 1) {
    		if ((int) (Math.random() * 5) == 0)
    		{
    			Main.tool_num[0]++;
    			Main.powernum = 5;
    		}
    		else
   				Main.tool_num[3]++;
    	}
    	//正常钱
    	return (int) (Math.random() * 500)+50;
    }

}

// 炸药桶
class Bomb extends Catchable {
	int img_num;
	BufferedImage bombImg[] = new BufferedImage[5];
    public Bomb(int xx, int yy) {
        super(xx, yy, "img/tnt.png","img/clawtnt.png", 2, 60, 6);
        img_num = 0;
        
		try {
			bombImg[0] = ImageIO.read(getClass().getResource("img/tnt.png"));
			bombImg[1] = ImageIO.read(getClass().getResource("img/boom1.png"));
			bombImg[2] = ImageIO.read(getClass().getResource("img/boom2.png"));
			bombImg[3] = ImageIO.read(getClass().getResource("img/boom3.png"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
    
    // 用来判断可抓取元素是否在炸药桶爆炸范围，设置的爆炸范围为150
    public boolean inRange(Catchable c) {
    	int bombrange=100;
        int left = (int) (X + Size / 2 - bombrange), right = (int) (X + Size / 2 + bombrange);
        int top = (int) (Y + Size / 2 - bombrange), bottom = (int) (Y + Size / 2 + bombrange);
        boolean fl = c.X + c.Size / 2 >= left && c.X + c.Size / 2 <= right
                && c.Y + c.Size / 2 >= top && c.Y + c.Size / 2 <= bottom;
        //System.out.print(fl);
        return fl;
    }
    
    //重载 触碰爆炸
    public void touch(double clawX, double clawY, double angle) {
    	X = clawX-75+4.5*Math.sin(angle);Y = clawY-26-4*Math.cos(angle);
    	caughtFlag = true;
    	bombFlag = 2;
    }
    //重载 连环爆炸惨案
    public void bomb() {
    	if (bombFlag == 0)
    		bombFlag = 3;
    }
    //重载 爆炸图片
    public BufferedImage getImg(double d) {
		switch (bombFlag) {
			case 0:
			case 1:
				break;
			case 2:
				if (img_num < 8)
				{
					img_num ++;
					Size = 200;
				}
				else
				{
					bombFlag = 1;
					Size = 150;
				}
				break;
			case 3:
				if (img_num < 7)
				{
					img_num ++;
					Size = 200;
				}
				else
					deleteFlag = true;
				break;
			default:
				System.out.println("There is something wrong with tnt in Catchable.java!");
		}
		if (img_num < 8) 
			return bombImg[(img_num+1)/2];
		else
			return rotateImg.rotateImage(CaughtImg, d, 100, 41);	
    }
}

// 头骨
class Skull extends Catchable {
	public Skull(int xx, int yy) {
        super(xx, yy, "img/skull.png", "img/clawskull.png", 7, 34, 6);
    }
	public int getVal() {
    	if (Main.tool_num[5] == 1)
    		return Val*5;
    	else
    		return Val;
    }
}

// 骨头
class Bone extends Catchable {
	public Bone(int xx, int yy) {
        super(xx, yy, "img/bone.png", "img/clawbone.png", 2, 34, 6);
    }
	public int getVal() {
    	if (Main.tool_num[5] == 1)
    		return Val*5;
    	else
    		return Val;
    }
}

// 硬币
class Coin extends Catchable {
	public Coin(int xx, int yy) {
        super(xx, yy,"img/coin.png","img/clawcoin.png",0,22, 6);
    }
	public int getVal() {
		/*
		 *    1/50 : 500,
		 *    4/50 : 100,
		 *    45/50: 10
		 */
		int num = (int)(Math.random() * 50);
		if(num < 45) {
			Main.tool_num[6]+= 10;
		}
		else if(num != 49) {
			Main.tool_num[6]+= 100;
		}
		else {
			Main.tool_num[6] += 500;
		}
		return Val;
	}
}
