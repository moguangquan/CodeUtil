import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * EAN��13����׼�棩��Ʒ�����������
 */
public class ZxingEAN13EncoderHandler {
    /**
     * ����
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    @SuppressWarnings("deprecation")
	public void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.EAN_13, codeWidth, height, null);

            MatrixToImageWriter
                    .writeToFile(bitMatrix, "png", new File(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String imgPath = "images/zxing_EAN13.png";
        // 1-3λ����3λ�����й��Ĺ��Ҵ���֮һ����690--699�����й��Ĵ��룬�ɹ����Ϸ��䣩��
		//4-8λ����5λ���������������̴��룬�ɳ������룬���ҷ��䣻
		//9-12λ����4λ�������ų�����Ʒ���룬�ɳ�������ȷ����
		//��13λ����1λ����У���룬����һ�����㷨(һ��ʹ����������������ϵͳ�Զ��������һλУ���룩����ǰ��12λ���ּ�����õ���
        String contents = "6923450657713";

        int width = 105, height = 50;
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();
        handler.encode(contents, width, height, imgPath);

        System.out.println("Michael ,you have finished zxing EAN13 encode.");
    }
}
