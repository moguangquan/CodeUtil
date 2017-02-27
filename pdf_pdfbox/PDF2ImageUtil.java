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
 * pdfת��ΪͼƬ�Ĺ����࣬����jdk1.6+
 * @author moguangquan
 */
public class PDF2ImageUtil {
	private static final int STARTPAGE = 0; //��ʼ��ҳ�� 
	private static final String COLOR = "rgb";//ͼƬ��ɫ��ģʽ,�У� bilevel, gray, rgb, rgba
	private static final int DPI = 160; //ͼƬ������

	public PDF2ImageUtil() {
	}
	/**
	 * �������Ĳ����Ƿ�Ϊ��
	 * @param pdfFile��pdf�ļ�·��
	 * @param imageFormat������ͼƬ�ĺ�׺
	 */
	private static void checkParam(String pdfFile, String imageFormat){
		if (pdfFile == null) {
			System.out.println("�����pdf�ļ�·������Ϊ�գ�");
			System.exit(1);
		}
		if("".equals(imageFormat)){
			System.out.println("�����ͼƬ��׺������Ϊ�գ�");
			System.exit(1);
		}
	}
	/**
	 * pdfת��ΪͼƬ�ķ���
	 * @param pdfFile��pdf�ļ�·��
	 * @param imageFormat������ͼƬ�ĺ�׺
	 */
	public static void pdf2Image(String pdfFile, String imageFormat) {
		checkParam(pdfFile, imageFormat);
		
		String outputPrefix = pdfFile.substring(0, pdfFile.lastIndexOf('.'));//�����·����pdf���ڵ�·����ͬ
		ImageType imageType = getImageType(COLOR);//����ͼƬ��ɫ��ģʽ��ȡͼƬ����
		String fileName = outputPrefix + "." + imageFormat;//�ļ�������·��
		
		PDDocument document = null;//pdf�ĵ�����
		FileOutputStream output=null;//�ļ������
		try {
			//�Ӷ�ȡpdf�ļ���PDDocument������
			document = PDDocument.load(new File(pdfFile));	
			//����PDDocument���󴴽�pdf��Ⱦ��			
			PDFRenderer renderer = new PDFRenderer(document);

			//����jaiͼƬ����
			//1.��ȡpdf�ĵ���ҳ��
			int endPage = document.getNumberOfPages();
			//2.�������jai��ͼƬ������б�
			List pages = new ArrayList(endPage);
			//3.������һҳ��jaiͼƬ����
			//3.1ͨ��pdf��Ⱦ����ȾpdfΪͼƬ����
			BufferedImage first_image = 
			renderer.renderImageWithDPI(STARTPAGE, DPI,imageType);
			//3.2ͨ��JAI��create()����ʵ����jai��ͼƬ����
			PlanarImage firstpage = JAI.create("mosaic", first_image);

			//4.��������ҳ��jaiͼƬ����
			for (int i = STARTPAGE+1; i < endPage; i++) {
				BufferedImage image = renderer.renderImageWithDPI(i, DPI,
			imageType);
				PlanarImage page = JAI.create("mosaic", image);
				pages.add(page);
			}

			//����jaiͼƬ��������tiff��ʽ��ͼƬ
			//1.����tiff���������
			TIFFEncodeParam param = new TIFFEncodeParam();
			//ѹ������	    param.setCompression(TIFFEncodeParam.COMPRESSION_DEFLATE);
			//����ͼƬ�ĵ�����
			param.setExtraImages(pages.iterator());
			//2.�����ļ�·�������ļ�������
			output = new FileOutputStream(fileName);
			//3.����ͼ�������
			ImageEncoder enc = 
			ImageCodec.createImageEncoder(imageFormat,output,param);    	    
			//4.ָ����һ�����б����jaiͼƬ����
			enc.encode(firstpage);
			//5.���Ԫ��
			pages.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//�ͷ���Դ
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
	 * ��ȡͼƬ������
	 * @param color ��ɫ���ַ���
	 * @return ImageType:pdfbox��ͼƬ����
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
