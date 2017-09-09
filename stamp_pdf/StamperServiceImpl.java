import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
* 需要使用到itextpdf的jar包来完成pdf文档的盖章,本例子是将默认的图片添加到指定pdf文档的最后一页，将结果写到指定输出流。
*/
public class StamperServiceImpl implements IStamperService {
    StamperServiceImpl() {
        super();
    }

    @Override
    public Image createImage(byte[] imageContent, float width, float height, float left,
            float bottom) {
        Image image = null;
        try {
            image = Image.getInstance(imageContent);
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.scaleAbsolute(width, height);
        image.setAbsolutePosition(left, bottom);

        return image;
    }

    @Override
    public Image createImage(byte[] imageContent) {
        Image image = createImage(imageContent, DEFAULT_WIDTH, DEFAULT_HEIGHT,
                DEFAULT_LEFT, DEFAULT_BOTTOM);
        return image;
    }

    @Override
    public void addImage(Image image, InputStream is, OutputStream os) {
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            reader = new PdfReader(is);
            int nop = reader.getNumberOfPages();
            stamper = new PdfStamper(reader, os);
            PdfContentByte content = stamper.getOverContent(nop);
            content.addImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stamper != null) stamper.close();
            } catch (DocumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (reader != null) reader.close();
        }
    }

    /** 测试图片，为了不把文字遮挡住，一般需要对盖章图片背景进行透明化 */
    private static final String PATH_IMAGE = "ckeditor/stamper/signature.gif";
    @Override
    public void testAddDefaultImage(InputStream is, OutputStream os) {
        InputStream imageIs = StamperServiceImpl.class.getClassLoader().getResourceAsStream(PATH_IMAGE);
        byte[] imageContent = null;
        try {
            int length = imageIs.available();
            imageContent = new byte[length];
            imageIs.read(imageContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                imageIs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Image img = createImage(imageContent);
        addImage(img, is, os);
    }
}