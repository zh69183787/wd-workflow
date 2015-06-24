package com.wonders.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


public class IpUtil {
	
	public static String getIpByStartString(String startStr) {
		String returnIp = "";
		
		String config_ip = PropertyUtil.getValueByKey("config.properties", "currentIp");
		
		if(config_ip.length()>0) return config_ip;
		
		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();

				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					String ip = ips.nextElement().getHostAddress();
					if(ip.startsWith(startStr)){
						returnIp = ip;
					}
				}
			}
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		return returnIp;
	}
}
