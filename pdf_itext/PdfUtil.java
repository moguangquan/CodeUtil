package pdf_itext;

import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.TreeSet;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class PdfUtil {
    public static final String DEST = "D://helloworld.pdf";
    /**
     * ͨ���ַ�������pdf
     * @param content ǰ̨��������html����
     * @throws IOException �쳣
     * @throws DocumentException 
     */
    public static void createPdfByString(String content,OutputStream outputStream) throws IOException,
    DocumentException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        StringBuffer buff=new StringBuffer();
        buff.append("<html> <head> <style type='text/css'>body{font-family:SimSun}</style></head><body>");
        buff.append((content));
        buff.append(("</body> </html>"));
        System.out.println(buff.toString());
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
        		getStringStream(buff.toString()), Charset.forName("gbk"));
        document.close();
    }

    /**
     * ��ȡtxt�ļ�������
     * @param file ��Ҫ��ȡ���ļ�����
     * @return �����ļ�����
     */
    public static String txt2String(File file){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//����һ��BufferedReader������ȡ�ļ�
            String s = null;
            while((s = br.readLine())!=null){//ʹ��readLine������һ�ζ�һ��
                result = result + "\n" +s;
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    
    /**
    * ��һ���ַ���ת��Ϊ������
    */
    public static InputStream getStringStream(String sInputString){
	    if (sInputString != null && !sInputString.trim().equals("")){
		    try{
		    	ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
		    	return tInputStringStream;
		    }catch (Exception ex){
		    	ex.printStackTrace();
		    }
	    }
	    return null;
    }
    public static void getSystemFonts(){
    	  //��ȡϵͳ�п��õ����������  
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();  
        String[] fontName = e.getAvailableFontFamilyNames();  
        for(int i = 0; i<fontName.length ; i++)  
        {  
            System.out.println(fontName[i]);  
        }  
    }
    public static void getItextFonts(){
    	  Set<String> fonts = new TreeSet<String>(FontFactory.getRegisteredFamilies());
          for (String fontname : fonts) {
              System.out.println(fontname);  
          }
    }
  public static void main(String[] args) throws IOException, DocumentException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();
       String content=txt2String(new File("src/pdf_itext/content.txt"));
        FileOutputStream outputStream=new FileOutputStream(file);
        createPdfByString(content,outputStream);
    	
    }
    /*public static void main(String[] args) throws IOException, DocumentException {
    	getItextFonts();
    }*/
}
