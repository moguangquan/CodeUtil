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
 * 使用dom4j来解析xml文档
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
	//删除
	public void delete(String fileName) throws Exception {
		// sax解析器
		SAXReader saxReader = new SAXReader();

		// Document 接口表示整个HTML或XML文档。从概念上讲,它是文档树的根,并提供对文档数据的基本访问
		// XML文档所有数据已经读入内存
		Document document = saxReader.read(this.getClass().getResourceAsStream(
				"/" + fileName));
		//将database的属性修改成oracle
		Element root=document.getRootElement();
		List<Element> databases_node=root.elements();
		for(Element database_node:databases_node){
			String str=(database_node.elementText("password"));
			//判断
			if( str.equals("root")){
				//删除password节点
				database_node.remove(database_node.element("password"));
			}
			//删除属性
			database_node.remove(database_node.attribute("version"));
		}
		//修改的是修改内存中的xml文件数据,如果需要持久化,需要重新写出去
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
		// XMLWriter xw=new XMLWriter(new FileOutputStream("db.xml"), of);
		// 因为FileOutputStream默认生成路径在根路径，如果要写入到src目录下，则加一个src路径
		XMLWriter xw = new XMLWriter(new FileOutputStream("src/"+fileName), of);
		// 将创建的xml文档写出
		xw.write(document);
		xw.close();
	}

	//修改
	public void update(String fileName) throws Exception {
		// sax解析器
		SAXReader saxReader = new SAXReader();

		// Document 接口表示整个HTML或XML文档。从概念上讲,它是文档树的根,并提供对文档数据的基本访问
		// XML文档所有数据已经读入内存
		Document document = saxReader.read(this.getClass().getResourceAsStream(
				"/" + fileName));
		//将database的属性修改成oracle
		Element root=document.getRootElement();
		List<Element> databases_node=root.elements();
		for(Element database_node:databases_node){
			//判断
			if(database_node.attributeValue("name").equalsIgnoreCase("mysql")){
				//将name属性修改成Oracle
				System.out.println("old:"+database_node.attributeValue("name"));
				database_node.attribute("name").setText("Oracle");
				System.out.println("update:"+database_node.attributeValue("name"));
				//修改成driver节点
				database_node.element("driver").setText("oracle.jdbc.driver.OracleDriver");
				
			}	
		}
		//修改的是修改内存中的xml文件数据,如果需要持久化,需要重新写出去
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
		// XMLWriter xw=new XMLWriter(new FileOutputStream("db.xml"), of);
		// 因为FileOutputStream默认生成路径在根路径，如果要写入到src目录下，则加一个src路径
		XMLWriter xw = new XMLWriter(new FileOutputStream("src/"+fileName), of);
		// 将创建的xml文档写出
		xw.write(document);
		xw.close();
	}

	// 写入
	public void add() throws Exception {
		// 创建一个Document
		Document document = DocumentHelper.createDocument();
		// 2.给Document添加对象
		Element root = document.addElement("DataSource");
		// 添加注释
		root.addComment("数据库列表");
		// 在root根节点下面添加一个子节点
		Element database = root.addElement("database");
		// 添加属性
		database.addAttribute("name", "mysql");
		database.addAttribute("version", "5.0");
		// 添加子节点
		database.addElement("driver").setText("com.mysql.jdbc.Driver");
		database.addElement("url")
				.setText("jdbc:mysql://localhost:3306/myjdbc");
		database.addElement("user").setText("root");
		database.addElement("password").setText("root");

		// 3.将Document写出文件
		// createPrettyPrint()美观的输出方式
		OutputFormat of = OutputFormat.createPrettyPrint();
		of.setEncoding("GBK");
		// XMLWriter xw=new XMLWriter(new FileOutputStream("db.xml"), of);
		// 因为FileOutputStream默认生成路径在根路径，如果要写入到src目录下，则加一个src路径
		XMLWriter xw = new XMLWriter(new FileOutputStream("src/db.xml"), of);
		// 将创建的xml文档写出
		xw.write(document);
		xw.close();
	}

	// 读并解析
	public void read(String fileName) throws DocumentException {
		// sax解析器
		SAXReader saxReader = new SAXReader();

		// Document 接口表示整个HTML或XML文档。从概念上讲,它是文档树的根,并提供对文档数据的基本访问
		// XML文档所有数据已经读入内存
		Document document = saxReader.read(this.getClass().getResourceAsStream(
				fileName));
		Element root = document.getRootElement();
		System.out.println("根节点:" + root.getName());
		// 返回整个元素的子节点
		// List<Element> childElements=root.elements();
		List<Element> childElements = root.elements("database");
		for (Element child : childElements) {
			// 获取属性,不知道属性名称时的遍历方法
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
			// 获取子节点,未知名称的遍历
			/*
			 * List<Element> childs=child.elements(); for(Element temp:childs){
			 * System.out.println(temp.getName()+":"+temp.getText()); }
			 */
			// 已知名称的查找
			System.out.println(child.elementText("driver"));
			System.out.println(child.element("url").getText());
			System.out.println(child.elementTextTrim("user"));
			System.out.println(child.element("password").getTextTrim());
		}
	}

}
