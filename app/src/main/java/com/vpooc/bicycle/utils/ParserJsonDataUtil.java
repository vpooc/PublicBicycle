package com.vpooc.bicycle.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vpooc.bicycle.entity.BicycleInfomation;

public class ParserJsonDataUtil {

	public static List<BicycleInfomation> Parser(String response) {
		Map<String, BicycleInfomation> info = null;
		Gson gson = new Gson();

		try {
			JSONObject s = new JSONObject(response);
			JSONArray a = s.getJSONArray("result");
			log("�������JSONArray", a.toString());
			List<Map<String, BicycleInfomation>> in = gson.fromJson(
					a.toString(),
					new TypeToken<List<Map<String, BicycleInfomation>>>() {
					}.getType());
			info = in.get(0);
			log("�������data size ", in.get(0).size());
			log("�������data ��һ��map ", in.get(0).get("1"));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<BicycleInfomation> list = new ArrayList<BicycleInfomation>();

		for (int i = 1; i <= info.size(); i++) {
			log("forѭ��", info.get(String.valueOf(i)).toString());
			list.add(info.get(String.valueOf(i)));

		}

		return list;
	}

	private static void log(String msg, Object param) {

		Log.d("Parser ", msg + param.toString());

	}
}
