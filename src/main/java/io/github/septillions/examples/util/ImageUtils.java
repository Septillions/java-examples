package io.github.septillions.examples.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

public class ImageUtils {

    /**
     * PNG 图片填充短边为透明 处理成正方形
     */
    public static void main(String[] args) throws Exception {
        // 文件路径
        String filePath = "";
        // 文件名称
        String fileName = "";
        // 全路径
        String fullPath = filePath + fileName;
        BufferedImage image = ImageIO.read(new FileInputStream(fullPath));
        // 判断宽高最大的一个值
        int max = Math.max(image.getHeight(), image.getWidth());
        BufferedImage outImage = new BufferedImage(max, max, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = outImage.createGraphics();
        outImage = graphics2D.getDeviceConfiguration().createCompatibleImage(max, max, Transparency.TRANSLUCENT);
        graphics2D.dispose();
        graphics2D = outImage.createGraphics();
        // 原图高度
        int oldheight = image.getHeight();
        // 原图宽度
        int oldwidth = image.getWidth();
        // 设置图片居中显示
        graphics2D.drawImage(image, (max - oldwidth) / 2, (max - oldheight) / 2, null);
        graphics2D.dispose();
        // 生成新的图片
        ImageIO.write(outImage, "png", new File(filePath + "j-" + fileName));
    }
}
