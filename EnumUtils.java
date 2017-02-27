package com.mo.basic.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
 
/**
 * 枚举转换为多种列表类型
 */
public class EnumUtils {
	/**
	 * 将枚举转为以索引组成的列表
	 * @param clz
	 * @return
	 */
	public static List<Integer> enum2Ordinal(Class<? extends Enum> clz){
		if(!clz.isEnum())
			return null;
		Enum[] enums=clz.getEnumConstants();
		List<Integer> rels=new ArrayList<Integer>();
		for(Enum en:enums){
			rels.add(en.ordinal());
		}
		return rels;
	}
	/**
	 * 将枚举转为以枚举变量的名称组成的列表
	 * @param clz
	 * @return
	 */
	public static List<String> enum2Name(Class<? extends Enum> clz){
		if(!clz.isEnum())
			return null;
		Enum[] enums=clz.getEnumConstants();
		List<String> rels=new ArrayList<String>();
		for(Enum en:enums){
			rels.add(en.name());
		}
		return rels;
	}
	/**
	 * 枚举转为以索引为Key，以枚举变量的名称为value的Map集合
	 * @param clz
	 * @return
	 */
	public static Map<Integer,String> enum2BasicMap(Class<? extends Enum> clz){
		if(!clz.isEnum())
			return null;
		Enum[] enums=clz.getEnumConstants();
		Map<Integer,String> rels=new HashMap<Integer,String>();
		for(Enum en:enums){
			rels.put(en.ordinal(),en.name());
		}
		return rels;
	}
	/**
	 * 枚举转换为枚举变量的名称的列表
	 * @param clz
	 * @param propName某个属性值
	 * @return
	 * 使用BeanUtils工具
	 */
	public static List<String> enumProp2List(Class<? extends Enum> clz,String propName) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			List<String> rels = new ArrayList<String>();
			for(Enum en:enums) {
				//通过枚举对象和枚举对象里的属性名称，获取对应的枚举名称
				rels.add((String)PropertyUtils.getProperty(en, propName));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将枚举转换为索引和枚举变量的名称的列表
	 * @param clz
	 * @param propName某个属性值
	 * @return
	 */
	public static Map<Integer,String> enumProp2OrdinalMap(Class<? extends Enum> clz,String propName) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			Map<Integer,String> rels = new HashMap<Integer,String>();
			for(Enum en:enums) {
				//(索引，枚举名称)
				rels.put(en.ordinal(),(String)PropertyUtils.getProperty(en, propName));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将枚举中的两个属性转换为map
	 * @param clz
	 * @param keyProp 要转化的key的属性名称
	 * @param valueProp 要转换的value的属性名称
	 * @return
	 */
	public static Map<String,String> enumProp2Map(Class<? extends Enum> clz,String keyProp,String valueProp) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			Map<String,String> rels = new HashMap<String,String>();
			for(Enum en:enums) {
				rels.put((String)PropertyUtils.getProperty(en,keyProp),(String)PropertyUtils.getProperty(en,valueProp));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将枚举转换为枚举变量的值和枚举变量的名称
	 * @param clz
	 * @param propName某个属性值
	 * @return
	 */
	public static Map<String,String> enumProp2NameMap(Class<? extends Enum> clz,String propName) {
		if(!clz.isEnum()) return null;
		try {
			Enum[] enums = clz.getEnumConstants();
			Map<String,String> rels = new HashMap<String,String>();
			for(Enum en:enums) {
				rels.put(en.name(),(String)PropertyUtils.getProperty(en, propName));
			}
			return rels;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
