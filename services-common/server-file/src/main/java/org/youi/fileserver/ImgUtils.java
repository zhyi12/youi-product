/**
 * 
 */
package org.youi.fileserver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * @author zhyi_12
 *
 */
public class ImgUtils {

	
	public static void compress(File imgFile,OutputStream output,int width,int height) throws IOException{
		
		BufferedImage img = ImageIO.read(imgFile);
		
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );   
		
		image.getGraphics().drawImage(img, 0, 0, width, height, null); // 绘制缩小后的图  
		
		
		ImageIO.write(image, "jpeg", output);
		
		output.close();
	}
	
}
