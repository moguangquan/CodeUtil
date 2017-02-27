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
 * Ҫ��
 * ������λ����������
 * 1.����б��⣬��������ƺ�sql����λ����������Ҫ��Ӧ����Сд�ɲ�һ��
 * 2.���û�б��⣬��Ҫ����������е�������
 * 3.����������ظ�
 * 4.Ŀǰ��������λֵֻ֧���ַ�����
 * @author moguangquan
 */
public class ExcelUtil {
	/**
	 * ${table} :����
	 * ${column}: ��λ��
	 * ${condition}:������
	 */
	private static final String UPDATE_SQL="UPDATE ${table} SET ${columns} WHERE ${conditions} ;";   
	private static StringBuffer strbuff=new StringBuffer();
	private static Workbook workbook = null;
	private static Sheet current_sheet=null;
	private static String table_regex="\\$\\{table\\}";//sqlģ���б��������ʽ
	private static String column_regrex="\\$\\{columns\\}";//sqlģ������λ��������ʽ
	private static String condition_regrex="\\$\\{conditions\\}";//sqlģ����������������ʽ
	
	public static void readExcel(){
		// ��ȡһ��xls�ļ�
		try {
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setEncoding("UTF-8"); //�����������
			workbook = Workbook.getWorkbook(new FileInputStream("D://law.xls"),workbookSettings);
			// ��ȡxls�ļ�����ı�,�±��0��ʼ
			current_sheet = workbook.getSheet(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����update��sql���
	 * excel�б��⣬�ڵ�һ��,�ӱ��������е���һ�п�ʼ��ȡ����
	 */
	public static void CreateUpdateSQl(String sql_temp,String table_replacement,
			String[] columns,String[] conditions,Map types){
		readExcel();	
		
		sql_temp=replaceSql_table(sql_temp,table_regex,table_replacement);//�滻${table}
		String sql=sql_temp;
		
		int title_row=0;//��һ��Ϊ������
		int rowNum =getRowNum();
		int colNum =getColNum();
		int columns_size=columns.length;
		int conditions_size=conditions.length;
		boolean isSearch=true;
		String col_title="";//excel��Ӧsql����λ���������ֶ���
		String cell_content="";//excel��Ԫ���ֵ
		
		for (int i = title_row+1; i < rowNum; i++) {
			Map columns_values=new HashMap();//��λ��Ӧ��ֵ
			Map conditions_values=new HashMap();//������Ӧ��ֵ
			
			for (int j = 0; j < colNum; j++) {
				col_title=getContentByCell(title_row, j);//excel��Ӧsql����λ���������ֶ���
				cell_content=getContentByCell(i, j);//excel��Ԫ���ֵ
				
				for(int columns_index=0;columns_index<columns_size&&isSearch;columns_index++){
					if((columns[columns_index].toLowerCase()).equals(col_title.toLowerCase())){//�ҵ���Ӧ�����µĵ�Ԫ��--��λ
						columns_values.put(col_title,cell_content); 
						isSearch=false;
					}
				}
				for(int conditions_index=0;conditions_index<conditions_size&&isSearch;conditions_index++){
					if((conditions[conditions_index].toLowerCase()).equals(col_title.toLowerCase())){//�ҵ���Ӧ�����µĵ�Ԫ��-����
						conditions_values.put(col_title,cell_content);
						isSearch=false;
					}
				}
				isSearch=true;
			}
			sql_temp=replaceSql_column(sql_temp, columns_values,types);//�滻${column}
			sql_temp=replaceSql_condition(sql_temp, conditions_values,types);//�滻${condition}
			columns_values=null;
			conditions_values=null;
			strbuff.append(sql_temp+"\n");
			sql_temp=sql;
		}
		System.out.println(strbuff.toString());
	}
	/**
	 * �滻sql��table,�ݲ�֧�ֹ�����sql��table�滻
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
	 * �滻sql������,�ݲ�֧�ֹ�����sql��table�滻
	 * @param oldSql 
	 * @param columns ��λ����ֵ��ɵ�map 
	 * @return " AND "�ָ�
	 */
	public static String replaceSql_condition(String oldSql,Map conditions,Map types){
		StringBuffer strBuff=new StringBuffer();
		Iterator it=conditions.keySet().iterator();
		String condition_key=null;//������key
		Object condition_value=null;//������value
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
	 * �滻sql�е�set����ֵ
	 * @param oldSql 
	 * @param conditions ��������ֵ��ɵ�map
	 * @return " , "�ָ�
	 */
	public static String replaceSql_column(String oldSql,Map columns,Map types){
		StringBuffer strBuff=new StringBuffer();
		Iterator it=columns.keySet().iterator();
		String column_key=null;//��λ��key
		Object column_value=null;//��λ��value
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
	 * ��ȡexcel������
	 * @return int
	 */
	public static int getRowNum(){
		int rowNum=current_sheet.getRows();//����
		return rowNum;
	}
	/**
	 * ��ȡexcel������
	 * @return int
	 */
	public static int getColNum(){
		int colNum=current_sheet.getColumns();//����
		return colNum;
	}
	/**
	 * �����к��л�ȡ��Ԫ������
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
		
		//�������ͻ���Ŀ���ͣ�trueΪ�ַ�����falseΪ���ַ���,���뱣�ִ�д
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
