package com.crm.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
/**
 * �����ࣺ������ת��Ϊƴ��
 * û��ʶ�������
 * @author Kevin
 *
 */
public class PingyinUtils {
	
	/**   
     * ����ת��Ϊ����ƴ������ĸ��Ӣ���ַ�����   
     * @param chines ����   
     * @return ƴ��
     */      
    public static String converterToFirstSpell(String chines){              
         String pinyinName = "";   
         
         //ת��Ϊ�ַ�
         char[] nameChar = chines.toCharArray();
//         for(int i=0;i<nameChar.length;i++){
//        	 System.out.println(nameChar[i]);
//         }
         
         //����ƴ����ʽ�����   
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
         
         //�������,��Сд,���귽ʽ��   
         defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);       
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);       
         
         for (int i = 0; i < nameChar.length; i++) {       
             //���������
        	 if (nameChar[i] > 128) {
                try {       
                     pinyinName += 
                    	   PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);       
                 } catch (BadHanyuPinyinOutputFormatCombination e) {       
                     e.printStackTrace();       
                 }       
             }else{//ΪӢ���ַ�    
                 pinyinName += nameChar[i];       
             }       
         }       
        return pinyinName;       
     }       
        
    /**   
     * ����ת��λ����ƴ����Ӣ���ַ�����   
     * @param chines ����   
     * @return ƴ��   
     */      
    public static String converterToSpell(String chines){               
        String pinyinName = "";       
        char[] nameChar = chines.toCharArray();       
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();       
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);       
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);       
        for (int i = 0; i < nameChar.length; i++) {       
            if (nameChar[i] > 128) {       
                try {       
                     pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];       
                 } catch (BadHanyuPinyinOutputFormatCombination e) {       
                     e.printStackTrace();       
                 }       
             }else{       
                 pinyinName += nameChar[i];       
             }       
         }       
        return pinyinName;       
     }       
           
    public static void main(String[] args) {       
        System.out.println(converterToFirstSpell("�����������޹�˾"));
    	
        System.out.println(converterToSpell("ŷ����"));
    	//System.out.println(converterToFirstSpell("ŷ����"));
     }       
}
