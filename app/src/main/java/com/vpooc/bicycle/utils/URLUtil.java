package com.vpooc.bicycle.utils;

public class URLUtil {
	/**
	 * �����ַ
	 */
	private static String host = "http://web.juhe.cn:8080/bike/state/";

	/**
	 * 自行车的key
	 */
	public static String key = "c2337a80ceb6ed922cb6940b063824c1";

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
