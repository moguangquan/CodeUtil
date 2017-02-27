package mo.pss.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;
/**
 * 验证码
 * @author Kevin
 */
public class Captcha {
	private int width;
	private int height;
	private int num;
	private String code;
	private static final Random ran = new Random();
	private static Captcha captcha; //单例
	
	private Captcha(){
		code = "0123456789";
		num = 4;
	}
	public static Captcha getInstance() {
		if(captcha==null) captcha = new Captcha();
		return captcha;
	}
	public int getWidth() {
		return width;
	}
	public void set(int width,int height,int num,String code) {
		this.width = width;
		this.height = height;
		this.setNum(num);
		this.setCode(code);
	}
	
	public void set(int width,int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 生成验证码字符
	 * @return
	 */
	public String generateCheckcode() {
		StringBuffer cc = new StringBuffer();
		for(int i=0;i<num;i++) {
			cc.append(code.charAt(ran.nextInt(code.length())));
		}
		return cc.toString();
	}
	/**
	 * 生成验证码图片
	 * @param checkcode 验证码字符
	 * @return
	 */
	public BufferedImage generateCheckImg(String checkcode){
		//创建一个图片对象
		BufferedImage img=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图片对象的画笔
		Graphics2D graphic=img.createGraphics();
		graphic.setColor(Color.WHITE);//白色背景
		graphic.fillRect(0, 0, width, height);
		graphic.setColor(Color.BLACK);//蓝色边框
		graphic.drawRect(0, 0, width-1, height-1);
		Font font=new Font("宋体",Font.BOLD+Font.ITALIC,(int)(height*0.8));//设置字体
		graphic.setFont(font);
		for(int i=0;i<num;i++){
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));//每一个字符的颜色随机
			graphic.drawString(String.valueOf(checkcode.charAt(i)),i*(width/num)+4,(int)(height*0.8));
		}
		//在图片中加一些小点
		for(int i=0;i<(width+height);i++){
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));//每一个字符的颜色随机
			graphic.drawOval(ran.nextInt(width), ran.nextInt(height), 1, 1);
		}
		//在图片中加一些线条
		for(int i=0;i<6;i++){
			graphic.setColor(new Color(ran.nextInt(255),ran.nextInt(255),ran.nextInt(255)));//每一个字符的颜色随机
			graphic.drawLine(ran.nextInt(width), ran.nextInt(height), ran.nextInt(width), ran.nextInt(height));
		}
		return img;
	}
}
