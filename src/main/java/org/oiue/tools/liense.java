/**
 * 
 */
package org.oiue.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class liense implements Serializable {
	/**
	 * 获取机器IP地址
	 * @return map
	 */
	public static Map<Integer, String> getLocalIP() {
		Map<Integer, String> macList = new HashMap<Integer, String>();
		String os = System.getProperty("os.name");
		// System.out.println(os);
		if (os != null) {
			os = os.toLowerCase();
			if (os.indexOf("window") >= 0) {
				macList = getLocalIPOfWindows();
			} ;
			if (os.indexOf("linux") >= 0) {
				macList = getLocalIPOfLinux();
			} ;
		}
		return macList;
	}
	
	public static Map<Integer, String> getLocalIPOfWindows() {
		Map<Integer, String> macList = new HashMap<Integer, String>();
		String address = "";
		int inc = 0;
		try {
			ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.toLowerCase();
				if (line.indexOf("ip address") != -1) {
					int index = line.indexOf(":");
					address = line.substring(index + 1).trim();
					macList.put(++inc, address);
				}
			}
			br.close();
		} catch (IOException e) {}
		// System.out.println("win.macList="+macList);
		return macList;
	}
	
	public static Map<Integer, String> getLocalIPOfLinux() {
		Map<Integer, String> macList = new HashMap<Integer, String>();
		int inc = 0;
		String address = "", strFlag = "inet addr:";
		try {
			ProcessBuilder pb = new ProcessBuilder("ifconfig -a | grep \"inet addr\"");
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				if (line.indexOf(strFlag) != -1) {
					int index = line.indexOf(strFlag);
					address = line.substring(index + strFlag.length()).trim();
					address = address.substring(0, address.indexOf(" "));
					macList.put(++inc, address);
					System.out.println("address=" + address + "\t" + line);
					// break;
				}
			}
			br.close();
			return macList;
		} catch (IOException e) {}
		return macList;
	}
	
	/**
	 * 根据硬件中的网卡列表来进行配置获取
	 * @return map
	 */
	public static Map<Integer, String> getLocalMACAddress() {
		Map<Integer, String> macList = new HashMap<Integer, String>();
		String os = System.getProperty("os.name");
		// System.out.println(os);
		if (os != null) {
			os = os.toLowerCase();
			if (os.indexOf("window") >= 0) {
				macList = getLocalMACAddressOfWindows();
			} ;
			if (os.indexOf("linux") >= 0) {
				macList = getLocalMACAddressOfLinux();
			} ;
		}
		return macList;
	}
	
	public static Map<Integer, String> getLocalMACAddressOfWindows() {
		Map<Integer, String> macList = new HashMap<Integer, String>();
		String address = "";
		int inc = 0;
		try {
			ProcessBuilder pb = new ProcessBuilder("ipconfig", "/all");
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				line = line.toLowerCase();
				if (line.indexOf("physical address") != -1) {
					int index = line.indexOf(":");
					address = line.substring(index + 1).trim();
					address = address.replaceAll("-", ":");
					macList.put(++inc, address);
				}
			}
			br.close();
		} catch (IOException e) {}
		// System.out.println("win.macList="+macList);
		return macList;
	}
	
	public static Map<Integer, String> getLocalMACAddressOfLinux() {
		Map<Integer, String> macList = new HashMap<Integer, String>();
		int inc = 0;
		String address = "";
		try {
			ProcessBuilder pb = new ProcessBuilder("ifconfig", "-a");
			Process p = pb.start();
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
				if (line.indexOf("Link encap:Ethernet  HWaddr") != -1) {
					int index = line.indexOf("HWaddr");
					address = line.substring(index + 7).trim();
					address = address.replaceAll("-", ":");
					macList.put(++inc, address);
					// break;
				}
			}
			br.close();
			return macList;
		} catch (IOException e) {}
		return macList;
	}
}
