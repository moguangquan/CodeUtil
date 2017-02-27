package com.dom4j;

import java.io.FileOutputStream;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * ʹ��dom4j������xml�ĵ�
 * 
 * @author Kevin
 * 
 */
public class Dom4jHandler {

	public static void main(String[] args) throws Exception {
		// new Dom4jHandler().read("data-sources.xml");
		//new Dom4jHandler().add();
		//new Dom4jHandler().update("db.xml");
		new Dom4jHandler().delete("db.xml");
	}
	//ɾ��
	public void delete(String fileName) throws Exception {
		// sax������
		SAXReader saxReader = new SAXReader();

		// Document �ӿڱ�ʾ����HTML��XML�ĵ����Ӹ����Ͻ�,�����ĵ����ĸ�,���ṩ���ĵ����ݵĻ�������
		// XML�ĵ����������Ѿ������ڴ�
		Document document = saxReader.read(this.getClass().getResourceAsStream(
				"/" + fileName));
		//��database�������޸ĳ�oracle
		Element root=document.getRootElement();
		List<Element> databases_node=root.elements();
		for(Element database_node:databases_node){
			String str=(database_node.elementText("password"));
			//�ж�
			if( str.equals("root")){
				//ɾ��password�ڵ�
				database_node.remove(database_node.element("password"));
			}
			//ɾ������
			database_node.remove(database_node.attribute("version"));
		}
		//�޸ĵ����޸��ڴ��е�xml�ļ�����,�����Ҫ�־û�,��Ҫ����д��ȥ
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
		// XMLWriter xw=new XMLWriter(new FileOutputStream("db.xml"), of);
		// ��ΪFileOutputStreamĬ������·���ڸ�·�������Ҫд�뵽srcĿ¼�£����һ��src·��
		XMLWriter xw = new XMLWriter(new FileOutputStream("src/"+fileName), of);
		// ��������xml�ĵ�д��
		xw.write(document);
		xw.close();
	}

	//�޸�
	public void update(String fileName) throws Exception {
		// sax������
		SAXReader saxReader = new SAXReader();

		// Document �ӿڱ�ʾ����HTML��XML�ĵ����Ӹ����Ͻ�,�����ĵ����ĸ�,���ṩ���ĵ����ݵĻ�������
		// XML�ĵ����������Ѿ������ڴ�
		Document document = saxReader.read(this.getClass().getResourceAsStream(
				"/" + fileName));
		//��database�������޸ĳ�oracle
		Element root=document.getRootElement();
		List<Element> databases_node=root.elements();
		for(Element database_node:databases_node){
			//�ж�
			if(database_node.attributeValue("name").equalsIgnoreCase("mysql")){
				//��name�����޸ĳ�Oracle
				System.out.println("old:"+database_node.attributeValue("name"));
				database_node.attribute("name").setText("Oracle");
				System.out.println("update:"+database_node.attributeValue("name"));
				//�޸ĳ�driver�ڵ�
				database_node.element("driver").setText("oracle.jdbc.driver.OracleDriver");
				
			}	
		}
		//�޸ĵ����޸��ڴ��е�xml�ļ�����,�����Ҫ�־û�,��Ҫ����д��ȥ
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
		// XMLWriter xw=new XMLWriter(new FileOutputStream("db.xml"), of);
		// ��ΪFileOutputStreamĬ������·���ڸ�·�������Ҫд�뵽srcĿ¼�£����һ��src·��
		XMLWriter xw = new XMLWriter(new FileOutputStream("src/"+fileName), of);
		// ��������xml�ĵ�д��
		xw.write(document);
		xw.close();
	}

	// д��
	public void add() throws Exception {
		// ����һ��Document
		Document document = DocumentHelper.createDocument();
		// 2.��Document��Ӷ���
		Element root = document.addElement("DataSource");
		// ���ע��
		root.addComment("���ݿ��б�");
		// ��root���ڵ��������һ���ӽڵ�
		Element database = root.addElement("database");
		// �������
		database.addAttribute("name", "mysql");
		database.addAttribute("version", "5.0");
		// ����ӽڵ�
		database.addElement("driver").setText("com.mysql.jdbc.Driver");
		database.addElement("url")
				.setText("jdbc:mysql://localhost:3306/myjdbc");
		database.addElement("user").setText("root");
		database.addElement("password").setText("root");

		// 3.��Documentд���ļ�
		// createPrettyPrint()���۵������ʽ
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
		// XMLWriter xw=new XMLWriter(new FileOutputStream("db.xml"), of);
		// ��ΪFileOutputStreamĬ������·���ڸ�·�������Ҫд�뵽srcĿ¼�£����һ��src·��
		XMLWriter xw = new XMLWriter(new FileOutputStream("src/db.xml"), of);
		// ��������xml�ĵ�д��
		xw.write(document);
		xw.close();
	}

	// ��������
	public void read(String fileName) throws DocumentException {
		// sax������
		SAXReader saxReader = new SAXReader();

		// Document �ӿڱ�ʾ����HTML��XML�ĵ����Ӹ����Ͻ�,�����ĵ����ĸ�,���ṩ���ĵ����ݵĻ�������
		// XML�ĵ����������Ѿ������ڴ�
		Document document = saxReader.read(this.getClass().getResourceAsStream(
				fileName));
		Element root = document.getRootElement();
		System.out.println("���ڵ�:" + root.getName());
		// ��������Ԫ�ص��ӽڵ�
		// List<Element> childElements=root.elements();
		List<Element> childElements = root.elements("database");
		for (Element child : childElements) {
			// ��ȡ����,��֪����������ʱ�ı�������
			/*
			 * List<Attribute> attributes=child.attributes(); for(Attribute
			 * attribute:attributes){
			 * System.out.println(attribute.getName()+":"+attribute.getValue());
			 * }
			 */
			String name = child.attributeValue("name");
			String version = child.attributeValue("version");
			// String version=child.attribute("version").getValue();
			System.out.println(name + ": " + version);
			// ��ȡ�ӽڵ�,δ֪���Ƶı���
			/*
			 * List<Element> childs=child.elements(); for(Element temp:childs){
			 * System.out.println(temp.getName()+":"+temp.getText()); }
			 */
			// ��֪���ƵĲ���
			System.out.println(child.elementText("driver"));
			System.out.println(child.element("url").getText());
			System.out.println(child.elementTextTrim("user"));
			System.out.println(child.element("password").getTextTrim());
		}
	}

}
