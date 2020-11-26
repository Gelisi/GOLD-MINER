package goldminer;

public class Claw{
	//爪子当前坐标
	private double clawX;
	private double clawY;
	//伸缩速度
	double stretchSpeed;
	//旋转方向
	private int spinDirection;
	//角度
	private double angle;
	
	//三种状态 旋转 下降 上升
	private int stateFlag;
	
	//当前抓到的东西
	private Catchable Item;
	
	//构造函数
	public Claw() {
		inition();
	}
	public double getAngle() {
		return angle;
	}
	public double getspeed() {
		return stretchSpeed;
	}
	public int getstateFlag() {
		return stateFlag;
	}
	public void setItem(Catchable item) {
		Item = item;
	}
	public void inition() {
		//初始化位置坐标
		clawX = Main.getStartX()+9;
		clawY = Main.getStartY()+7;

		//初始化伸缩速度
		stretchSpeed = 5;
		spinDirection = 1;
		stateFlag = 0;
		Item = null;
	}
	
	//用于键盘监听事件 如果在旋转则出钩
	public void stretch() {
		if (stateFlag == 0)
			stateFlag = 1;
		else
			return;
		angle = Math.atan((clawX-Main.getStartX())/(clawY-Main.getStartY()));
	}
	public int getX() {
		return (int)clawX;
	}
	public int getY() {
		return (int)clawY;
	}
	public Catchable getItem() {
		return Item;
	}
	
	public void move() {
		//钩子旋转
		
		if (stateFlag==0){
			//调整速度
			double circleSize = 15;
			double spinSpeed = (clawY - Main.getStartY()+1)*(clawY - Main.getStartY()+1)/500;
			//更新坐标
			clawX -= spinSpeed*spinDirection;
			clawY = Math.sqrt(Math.max(circleSize*circleSize - ((clawX - Main.getStartX()) * (clawX - Main.getStartX())),0))
					+ Main.getStartY();
			angle = Math.atan((clawX-Main.getStartX())/(clawY-Main.getStartY()));
			
			//临界换方向
			if (clawX < Main.getStartX()-circleSize+1 && spinDirection > 0) {
				spinDirection *= -1;
			}
			if (clawX > Main.getStartX()+circleSize-1 && spinDirection < 0) {
				spinDirection *= -1;
			}
		}
		//钩子下降
		else if (stateFlag==1){
			//更新坐标
			clawX += stretchSpeed * Math.sin(angle);
			clawY += stretchSpeed * Math.cos(angle);
			//判断越界
			if (clawY >= 575 || clawX <= 0 || clawX >= Main.getWeight()) {
				stateFlag = 2;
			}
			// 判断抓取
			for (int i = 0; i < CatchableArray.catchableSet.size(); ++i) {
				Catchable c = CatchableArray.catchableSet.get(i);
				if (clawX >= c.X && clawX <= c.X + c.Size && clawY >= c.Y && clawY <= c.Y + c.Size) {
					//更新状态和坐标
					c.touch(clawX, clawY, angle);
				
					stateFlag = 2;
					Item = c;
					
					// 生力水加速作用
					if (Main.tool_num[0] != 0)
						stretchSpeed = c.Speed+2;
					else
						stretchSpeed = c.Speed;
					
					break;
				}
			}
		}
		//钩子上升
		else{
			//更新坐标
			clawX -= stretchSpeed * Math.sin(angle);
			clawY -= stretchSpeed * Math.cos(angle);
			
			//钩子到达起点 更新状态和金钱
			if ((int)clawY < Main.getStartY()+7) {
				stateFlag=0;
								
				// 抓回来后更新金钱
				// 更新道具也写在getVal内了
				if (Item != null) 
				{
					Main.money += Item.getVal();
					Item.deleteFlag = true;
					Item = null;
				}		
	
				//重置钩子
				inition();
			}
		}
	}
}
