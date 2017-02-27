package pdf_itext;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 实现与前台的交互
 * @author moguangquan
 */
public class PdfServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PdfServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         // 路径 注意linux和windows 反斜线的区别
         OutputStream outputStream = response.getOutputStream();
         response.setContentType("application/pdf");
         String pdfName = "本地下载.pdf";
         response.addHeader("Content-Disposition", "attachment;filename="+ new String (pdfName.getBytes("gb2312"),"iso-8859-1"));
         String content=request.getParameter("content");
         try {
			PdfUtil.createPdfByString(content,outputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
         outputStream.flush();
         outputStream.close();
	}

}
