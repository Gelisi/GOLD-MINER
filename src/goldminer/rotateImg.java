package goldminer;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;


public class rotateImg {
	public static BufferedImage rotateImage(BufferedImage bufferedImage, double angel, int centerX, int centerY) {
		if (bufferedImage == null) {
			return null;
		}
		if (angel < 0) {
			angel = angel + 2*Math.PI;
		}
		int imageWidth = bufferedImage.getWidth(null);
		int imageHeight = bufferedImage.getHeight(null);
		// 获取原始图片的透明度
		int type = bufferedImage.getColorModel().getTransparency();
		BufferedImage newImage = null;
		newImage = new BufferedImage(imageWidth, imageHeight, type);
		Graphics2D graphics = newImage.createGraphics();
		// 旋转角度
		graphics.rotate(-angel, centerX, centerY);
	
		// 绘图
		graphics.drawImage(bufferedImage, null, null);
		return newImage;
	}
	
}
