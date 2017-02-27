package test2;

/**
 * ������ת��Ϊ���ı��ʽ
 * @author Kevin
 *
 */
public class RenMingBi {

	private static final char[] data=new char[]{
		'��','Ҽ','�E','��','��','��','�','��','��','��'};
	private static final char[] units=new char[]{
		'Ԫ','ʰ','��','Ǫ','��','ʰ','��','Ǫ','��','��'};
	public static void main(String[] args) {
		System.out.println(covert(1351068912));
	}
	public static String covert(int money){
		StringBuffer sbf=new StringBuffer();
		int unit=0;
		while(money!=0){
			sbf.insert(0, units[unit++]);
			int number=money%10;
			System.out.println("number:"+number);
			sbf.insert(0, data[number]);
			money/=10;
		}
		return sbf.toString().replaceAll("��+��","��").replaceAll("��+Ǫ", "Ǫ"
				).replaceAll("��+��", "��").replaceAll("��+Ԫ", "Ԫ");
	}

}
