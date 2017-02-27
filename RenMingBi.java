package test2;

/**
 * 把数字转换为中文表达式
 * @author Kevin
 *
 */
public class RenMingBi {

	private static final char[] data=new char[]{
		'零','壹','貳','叁','肆','伍','陸','柒','捌','玖'};
	private static final char[] units=new char[]{
		'元','拾','佰','仟','万','拾','佰','仟','万','亿'};
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
		return sbf.toString().replaceAll("零+亿","亿").replaceAll("零+仟", "仟"
				).replaceAll("零+佰", "佰").replaceAll("零+元", "元");
	}

}
