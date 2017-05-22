package com.test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.poxiao.protobuf.UserProtobuf;

public class Main {

	private static int 哈哈$$ = 0;
	private static String 嘿嘿 = "嘿嘿"; 
	
	public static void main(String[] args) {
//		System.out.println("\u65f6\u95f4\u5f02\u5e38");
//		System.out.println(System.getProperty("sun.arch.data.model"));;
//		
//		
//		System.out.println("qqq".equalsIgnoreCase("QQQ"));;
//		
//		System.out.println(嘿嘿 + 哈哈$$);
		
		
		// 序列化过程
		// UserProtobuf是生成类的名字，即proto文件中的java_outer_classname
		// userBuf是里面某个序列的名字，即proto文件中的message testBuf
		UserProtobuf.userBuf.Builder builder = UserProtobuf.userBuf.newBuilder();
		builder.setID(777);
		builder.setUrl("shiqi");
		builder.setIsSD(true);

		// testBuf
		UserProtobuf.userBuf info = builder.build();

		byte[] result = info.toByteArray();

		// 反序列化过程
		try {
			UserProtobuf.userBuf testBuf = UserProtobuf.userBuf.parseFrom(result);
			System.out.println(testBuf);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
	}
	
}
