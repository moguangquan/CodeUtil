import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

/**
 * EAN－13（标准版）商品条形码的生成
 */
public class ZxingEAN13EncoderHandler {
    /**
     * 编码
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
        // 1-3位：共3位，是中国的国家代码之一。（690--699都是中国的代码，由国际上分配）；
		//4-8位：共5位，代表着生产厂商代码，由厂商申请，国家分配；
		//9-12位：共4位，代表着厂内商品代码，由厂商自行确定；
		//第13位：共1位，是校验码，依据一定的算法(一般使用条码软件，由软件系统自动弹出最后一位校验码），由前面12位数字计算而得到。
        String contents = "6923450657713";

        int width = 105, height = 50;
        ZxingEAN13EncoderHandler handler = new ZxingEAN13EncoderHandler();
        handler.encode(contents, width, height, imgPath);

        System.out.println("Michael ,you have finished zxing EAN13 encode.");
    }
}
