package pdf_pdfbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.TIFFEncodeParam;
/**
 * pdf转换为图片的工具类，基于jdk1.6+
 * @author moguangquan
 */
public class PDF2ImageUtil {
	private static final int STARTPAGE = 0; //开始的页数 
	private static final String COLOR = "rgb";//图片的色彩模式,有： bilevel, gray, rgb, rgba
	private static final int DPI = 160; //图片的像素

	public PDF2ImageUtil() {
	}
	/**
	 * 检查输出的参数是否为空
	 * @param pdfFile：pdf文件路径
	 * @param imageFormat：生成图片的后缀
	 */
	private static void checkParam(String pdfFile, String imageFormat){
		if (pdfFile == null) {
			System.out.println("输入的pdf文件路径不能为空！");
			System.exit(1);
		}
		if("".equals(imageFormat)){
			System.out.println("输入的图片后缀名不能为空！");
			System.exit(1);
		}
	}
	/**
	 * pdf转换为图片的方法
	 * @param pdfFile：pdf文件路径
	 * @param imageFormat：生成图片的后缀
	 */
	public static void pdf2Image(String pdfFile, String imageFormat) {
		checkParam(pdfFile, imageFormat);
		
		String outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));//输出的路径和pdf所在的路径相同
		ImageType imageType = getImageType(COLOR);//根据图片的色彩模式获取图片类型
		String fileName = outputPrefix + "." + imageFormat;//文件的完整路径
		
		PDDocument document = null;//pdf文档对象
		FileOutputStream output=null;//文件输出流
		try {
			//从读取pdf文件到PDDocument对象中
			document = PDDocument.load(new File(pdfFile));	
			//根据PDDocument对象创建pdf渲染器			
			PDFRenderer renderer = new PDFRenderer(document);

			//创建jai图片对象
			//1.获取pdf文档的页数
			int endPage = document.getNumberOfPages();
			//2.创建存放jai的图片对象的列表
			List pages = new ArrayList(endPage);
			//3.创建第一页的jai图片对象
			//3.1通过pdf渲染器渲染pdf为图片对象
			BufferedImage first_image = 
			renderer.renderImageWithDPI(STARTPAGE, DPI,imageType);
			//3.2通过JAI的create()方法实例化jai的图片对象
			PlanarImage firstpage = JAI.create("mosaic", first_image);

			//4.创建其它页的jai图片对象
			for (int i = STARTPAGE+1; i < endPage; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, DPI,
			imageType);
				PlanarImage page = JAI.create("mosaic", image);
				pages.add(page);
			}

			//根据jai图片对象生成tiff格式的图片
			//1.创建tiff编码参数类
			TIFFEncodeParam param = new TIFFEncodeParam();
			//压缩参数	    param.setCompression(TIFFEncodeParam.COMPRESSION_DEFLATE);
			//设置图片的迭代器
			param.setExtraImages(pages.iterator());
			//2.根据文件路径创建文件输入流
			output = new FileOutputStream(fileName);
			//3.创建图像编码器
			ImageEncoder enc = 
			ImageCodec.createImageEncoder(imageFormat,output,param);    	    
			//4.指定第一个进行编码的jai图片对象
			enc.encode(firstpage);
			//5.清空元素
			pages.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//释放资源
			try {
				if(output!=null){
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(document!=null){
					document.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 获取图片的类型
	 * @param color 颜色的字符串
	 * @return ImageType:pdfbox的图片类型
	 */
	private static ImageType getImageType(String color) {
		ImageType imageType = null;
		if ("bilevel".equalsIgnoreCase(color)) {
			imageType = ImageType.BINARY;
		} else if ("gray".equalsIgnoreCase(color)) {
			imageType = ImageType.GRAY;
		} else if ("rgb".equalsIgnoreCase(color)) {
			imageType = ImageType.RGB;
		} else if ("rgba".equalsIgnoreCase(color)) {
			imageType = ImageType.ARGB;
		}
		if (imageType == null) {
			System.err.println("Error: Invalid color.");
			System.exit(2);
		}
		return imageType;
	}
	
	public static void main(String []args) {
		pdf2Image("D:\\demo.pdf","tiff");
	}

}
