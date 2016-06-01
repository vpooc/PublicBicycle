package com.vpooc.bicycle.utils;

public class URLUtil {
	/**
	 * �����ַ
	 */
	private static String host = "http://web.juhe.cn:8080/bike/state/";

	/**
	 * 自行车的key
	 */
	public static String key = "1f63c1e4d89a255e081239874908c25a";

	/**
	 * 
	 * @param district
	 *            ����ļ�д������ƴ������ĸ (�磺sz�����ݣ�)
	 * @param state
	 *            վ̨��ַ(�磺ʱ��㳡�ϣ�
	 * @return ĳ������ĳվ̨��URL
	 */
	public static String getStateInfomation(String district) {

		return host + district;
	}

}
