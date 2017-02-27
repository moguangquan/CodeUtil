package excel;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * 要求：
 * 输入栏位或条件名称
 * 1.如果有标题，标题的名称和sql的栏位或条件名称要对应，大小写可不一致
 * 2.如果没有标题，就要输入标题所有的列索引
 * 3.不允许标题重复
 * 4.目前操作的栏位值只支持字符串的
 * @author moguangquan
 */
public class ExcelUtil {
	/**
	 * ${table} :表名
	 * ${column}: 栏位名
	 * ${condition}:条件名
	 */
	private static final String UPDATE_SQL="UPDATE ${table} SET ${columns} WHERE ${conditions} ;";   
	private static StringBuffer strbuff=new StringBuffer();
	private static Workbook workbook = null;
	private static Sheet current_sheet=null;
	private static String table_regex="\\$\\{table\\}";//sql模板中表的正则表达式
	private static String column_regrex="\\$\\{columns\\}";//sql模板中栏位的正则表达式
	private static String condition_regrex="\\$\\{conditions\\}";//sql模板中条件的正则表达式
	
	public static void readExcel(){
		// 获取一个xls文件
		try {
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setEncoding("UTF-8"); //解决中文乱码
			workbook = Workbook.getWorkbook(new FileInputStream("D://law.xls"),workbookSettings);
			// 获取xls文件里面的表,下标从0开始
			current_sheet = workbook.getSheet(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 生成update的sql语句
	 * excel有标题，在第一行,从标题所在行的下一行开始获取数据
	 */
	public static void CreateUpdateSQl(String sql_temp,String table_replacement,
			String[] columns,String[] conditions,Map types){
		readExcel();	
		
		sql_temp=replaceSql_table(sql_temp,table_regex,table_replacement);//替换${table}
		String sql=sql_temp;
		
		int title_row=0;//第一行为标题行
		int rowNum =getRowNum();
		int colNum =getColNum();
		int columns_size=columns.length;
		int conditions_size=conditions.length;
		boolean isSearch=true;
		String col_title="";//excel对应sql的栏位名或条件字段名
		String cell_content="";//excel单元格的值
		
		for (int i = title_row+1; i < rowNum; i++) {
			Map columns_values=new HashMap();//栏位对应的值
			Map conditions_values=new HashMap();//条件对应的值
			
			for (int j = 0; j < colNum; j++) {
				col_title=getContentByCell(title_row, j);//excel对应sql的栏位名或条件字段名
				cell_content=getContentByCell(i, j);//excel单元格的值
				
				for(int columns_index=0;columns_index<columns_size&&isSearch;columns_index++){
					if((columns[columns_index].toLowerCase()).equals(col_title.toLowerCase())){//找到对应标题下的单元格--栏位
						columns_values.put(col_title,cell_content); 
						isSearch=false;
					}
				}
				for(int conditions_index=0;conditions_index<conditions_size&&isSearch;conditions_index++){
					if((conditions[conditions_index].toLowerCase()).equals(col_title.toLowerCase())){//找到对应标题下的单元格-条件
						conditions_values.put(col_title,cell_content);
						isSearch=false;
					}
				}
				isSearch=true;
			}
			sql_temp=replaceSql_column(sql_temp, columns_values,types);//替换${column}
			sql_temp=replaceSql_condition(sql_temp, conditions_values,types);//替换${condition}
			columns_values=null;
			conditions_values=null;
			strbuff.append(sql_temp+"\n");
			sql_temp=sql;
		}
		System.out.println(strbuff.toString());
	}
	/**
	 * 替换sql的table,暂不支持关联表sql的table替换
	 * @param oldSql
	 * @param regex
	 * @param replacement
	 * @return
	 */
	public static String replaceSql_table(String oldSql,String regex, String replacement){
		String newSql=oldSql.replaceFirst(regex, replacement);
		return newSql;
	}
	/**
	 * 替换sql的条件,暂不支持关联表sql的table替换
	 * @param oldSql 
	 * @param columns 栏位和其值组成的map 
	 * @return " AND "分隔
	 */
	public static String replaceSql_condition(String oldSql,Map conditions,Map types){
		StringBuffer strBuff=new StringBuffer();
		Iterator it=conditions.keySet().iterator();
		String condition_key=null;//条件的key
		Object condition_value=null;//条件的value
		while(it.hasNext()){
			condition_key=(String) it.next();
			condition_value=conditions.get(condition_key);
			if((boolean) types.get(condition_key)){
				strBuff.append(" "+condition_key+"='"+condition_value+"' AND");
			}else{
				strBuff.append(" "+condition_key+"="+condition_value+" AND");
			}
		}
		strBuff.delete(strBuff.length()-3,strBuff.length());
		String newSql=oldSql.replaceFirst(condition_regrex,strBuff.toString());
		return newSql;
	}
	/**
	 * 替换sql中的set的新值
	 * @param oldSql 
	 * @param conditions 条件和其值组成的map
	 * @return " , "分隔
	 */
	public static String replaceSql_column(String oldSql,Map columns,Map types){
		StringBuffer strBuff=new StringBuffer();
		Iterator it=columns.keySet().iterator();
		String column_key=null;//栏位的key
		Object column_value=null;//栏位的value
		while(it.hasNext()){
			column_key=(String) it.next();
			column_value=columns.get(column_key);
			if((boolean) types.get(column_key)){
				strBuff.append(" "+column_key+"='"+column_value+"' ,");
			}else{
				strBuff.append(" "+column_key+"="+column_value+" ,");
			}
		}
		strBuff.delete(strBuff.length()-1,strBuff.length());
		String newSql=oldSql.replaceFirst(column_regrex,strBuff.toString());
		return newSql;
	}
	/**
	 * 获取excel的行数
	 * @return int
	 */
	public static int getRowNum(){
		int rowNum=current_sheet.getRows();//行数
		return rowNum;
	}
	/**
	 * 获取excel的列数
	 * @return int
	 */
	public static int getColNum(){
		int colNum=current_sheet.getColumns();//列数
		return colNum;
	}
	/**
	 * 根据行和列获取单元格内容
	 * @param row
	 * @param col
	 * @return
	 */
	public static String getContentByCell(int row,int col){
		Cell cell = current_sheet.getCell(col, row);
		return cell.getContents();
		
	}
	public static void main(String[] args) {
		
		String table_replacement="DICTIONARY";
		String[] conditions={"kind","code"};
		String[] columns={"note"};
		
		//条件类型或栏目类型，true为字符串，false为非字符串,必须保持大写
		Map types=new HashMap();
		types.put("KIND", true);
		types.put("CODE",true);
		types.put("NOTE",true);
		
		CreateUpdateSQl(UPDATE_SQL,table_replacement,columns,conditions,types);
		
		if(workbook!=null){
			workbook.close();
		}
	}
}
