package blockchain;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

public class NetworkInterfaceTool {

	/***
	 * 获取本机启动中tomcat端口号
	 * 
	 * @return
	 */
	public static int getServerPort() {
		int tomcatPort = 8080 ;
		try {
			MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
			Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"),
					Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			tomcatPort = Integer.valueOf(objectNames.iterator().next().getKeyProperty("port"));
		} catch (Exception e) {
			return tomcatPort;
		}
		return tomcatPort;
	}

	public static ArrayList<InetAddress> getLANAddressOnWindows() {
		ArrayList<InetAddress> localIpList = new ArrayList<>();
		try {
			Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
			while (nifs.hasMoreElements()) {
				NetworkInterface nif = nifs.nextElement();

				if (nif.getName().startsWith("wlan")) {
					Enumeration<InetAddress> addresses = nif.getInetAddresses();

					while (addresses.hasMoreElements()) {

						InetAddress addr = addresses.nextElement();
						if (addr.getAddress().length == 4) { // 速度快于 instanceof
							localIpList.add(addr);
						}
					}
				}
			}
			return localIpList;
		} catch (SocketException ex) {
			ex.printStackTrace(System.err);
		}
		return null;
	}

	/**
	 * 获取本机IP局域网前缀集合
	 * 
	 * @return
	 */
	public static ArrayList<String> getLocalIpPre() {
		ArrayList<InetAddress> ipList = getLANAddressOnWindows();
		ArrayList<String> localIpPreList = new ArrayList<>();
		for (InetAddress inetAddress : ipList) {
			String ipArr[] = inetAddress.toString().substring(1).split("\\.");
			String ip = ipArr[0] + "." + ipArr[1] + "." + ipArr[2] + ".";
			localIpPreList.add(ip);
		}
		return localIpPreList;
	}

	public static void main(String[] args) throws Exception {
		// 获得本机的所有网络接口
		Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();

		while (nifs.hasMoreElements()) {
			NetworkInterface nif = nifs.nextElement();

			// 获得与该网络接口绑定的 IP 地址，一般只有一个
			Enumeration<InetAddress> addresses = nif.getInetAddresses();
			while (addresses.hasMoreElements()) {
				InetAddress addr = addresses.nextElement();

				if (addr instanceof Inet4Address) { // 只关心 IPv4 地址
					System.out.println("网卡接口名称：" + nif.getName());
					System.out.println("网卡接口地址：" + addr.getHostAddress());
					System.out.println();
				}
			}
		}
	}
	 
}