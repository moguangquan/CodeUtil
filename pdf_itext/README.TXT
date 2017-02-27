简单使用iText结合xmlworker生成pdf文件：
1.导入itextpdf-5.5.7.jar、xmlworker-5.5.6.jar等包
itextpdf包：http://sourceforge.net/projects/itext/files/
xmlworker包：http://sourceforge.net/projects/xmlworker/files/

2.创建 HTML文档，或一段

2.生成pdf文件的基本步骤：
1.使用Document来构建文档对象，
Document document = new Document();
2.把输出流通过PdfWriter类转换为Document对象，file对象是将要生成的pdf文件对象
PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
3.打开文档，进行编辑操作
document.open();
4.通过XMLWorkerHelper类的parseXHtml()方法把HTML文件输出到指定的document对象中
XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(HTML), Charset.forName("gbk"));
5.最后要关闭document对象
document.close();