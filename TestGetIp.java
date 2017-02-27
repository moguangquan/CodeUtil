package testnetIp;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TestGetIp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for(String ip:getLoaclIpList()){
			System.out.println(ip);
		}
	}
	/**
	 * 获取本地ip地址
	 * @return
	 */
	private static List<String> getLoaclIpList() {
		List<String> ipList = new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> netInterfaces = null;
			try{
				netInterfaces = NetworkInterface.getNetworkInterfaces();
			}catch(Throwable e){
				netInterfaces = null;
				java.net.InetAddress localhost = java.net.InetAddress.getLocalHost();
				if(localhost!=null){
					ipList.add(localhost.getHostAddress());
				}
			}
			InetAddress ip = null;
			if(netInterfaces!=null){
				while (netInterfaces.hasMoreElements()) {
					NetworkInterface ni = netInterfaces.nextElement();
					Enumeration<InetAddress> address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						ip = address.nextElement();
						if (!ip.isLoopbackAddress() && ip.isSiteLocalAddress()) {
							ipList.add(ip.getHostAddress());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ipList;
	}
}
