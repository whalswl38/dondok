package com.trip.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class KaKaoApi {

	public static String KaKaoRestKey = "a925c68fbb98e7d9a901a2ab01848b94";

	
	public static String searchPlace(String searchValue, String category) throws IOException {
		return searchPlace(searchValue, category,null);
	}
	
	public static String searchPlace(String searchValue, String category, String page) throws IOException {

		String encodeValue = URLEncoder.encode(searchValue,"UTF-8");
		String locationCode = null;
		if(category.equals("명소")) {
			locationCode = "AT4";
		} else if (category.equals("맛집")) {
			locationCode = "FD6";
		} else if (category.equals("숙소")) {
			locationCode = "AD5";
		}
		
		String requestURL = "https://dapi.kakao.com/v2/local/search/keyword.json?size=4&category_group_code="+ locationCode + "&query=" + encodeValue + "&page=" + ((page != null)? page : 1);
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Authorization", "KakaoAK " + KaKaoRestKey);
		con.setRequestProperty("X-Requested-With", "curl");
		con.setRequestMethod("GET");
		
		InputStream in = con.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 8];
			int length = 0;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
		}
		String searchJson = new String(out.toByteArray(), "UTF-8");	
		
		con.disconnect();

		return searchJson;
	}
	
	
	
	public static String localSearch(String whereRest, String searchValue, String x, String y) throws IOException {
		String encodeValue = URLEncoder.encode(searchValue,"UTF-8");
		
		String requestURL = "https://dapi.kakao.com/v2/local/search/keyword.json?x=" + x + "&y=" + y + "&query=" + encodeValue;
		
		return localSearch(requestURL);
	}
	
	
	public static String localSearch(String whereRest, String searchValue, int page, int size) throws IOException {
		String encodeValue = URLEncoder.encode(searchValue,"UTF-8");
		
		String requestURL = "https://dapi.kakao.com/v2/local/search/"+ whereRest +"?page="+page+"&size="+size+"&query=" + encodeValue;
		
		return localSearch(requestURL);
	}
	
	public static String localSearch(String requestURL) throws IOException {
		
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Authorization", "KakaoAK " + KaKaoRestKey);
		con.setRequestProperty("X-Requested-With", "curl");
		con.setRequestMethod("GET");
		
		InputStream in = con.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 8];
			int length = 0;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
		}
		String searchJson = new String(out.toByteArray(), "UTF-8");	
		
		con.disconnect();

		return searchJson;
	}
	
	
	
	public static String search(String whereRest, String searchValue, int page, int size) throws IOException {
		String encodeValue = URLEncoder.encode(searchValue,"UTF-8");
		
		String requestURL = "https://dapi.kakao.com/v2/search/"+ whereRest +"?page="+page+"&size="+size+"&query=" + encodeValue;
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Authorization", "KakaoAK " + KaKaoRestKey);
		con.setRequestProperty("X-Requested-With", "curl");
		con.setRequestMethod("GET");
		
		InputStream in = con.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 8];
			int length = 0;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
		}
		String searchJson = new String(out.toByteArray(), "UTF-8");	
		
		con.disconnect();

		return searchJson;
	}

	
	public static void main(String[] args) throws IOException {
		
		String locationID = "18577297";
		
		String location = Crawler.crawlingMeta("https://map.kakao.com/?itemId=18577297", "meta[property=og:description]").get(0).attr("content");
		
		String encodeValue = URLEncoder.encode(location,"UTF-8");
		String requestURL = "https://dapi.kakao.com/v2/local/search/address.json?query=" + encodeValue;
		URL url = new URL(requestURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Authorization", "KakaoAK " + KaKaoRestKey);
		con.setRequestProperty("X-Requested-With", "curl");
		con.setRequestMethod("GET");
		
		InputStream in = con.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024 * 8];
			int length = 0;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
		}
		String searchJson = new String(out.toByteArray(), "UTF-8");	
		
		System.out.println(searchJson);
		con.disconnect();
		
		
		JSONObject jsonObj = null;

		JSONParser paser = new JSONParser();
		try {
			jsonObj = (JSONObject) paser.parse(searchJson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray array = (JSONArray) jsonObj.get("documents");
		jsonObj = (JSONObject) array.get(0);
		String x = (String) jsonObj.get("x");
		String y = (String) jsonObj.get("y");
		
		System.out.println(x);
		System.out.println(y);
		
		String keyword = Crawler.crawlingMeta("https://map.kakao.com/?itemId=18577297", "meta[property=og:title]").get(0).attr("content");
		
		encodeValue = URLEncoder.encode(keyword,"UTF-8");
		requestURL = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + encodeValue + "&x=" + x +"&y=" + y;
		url = new URL(requestURL);
		con = (HttpURLConnection) url.openConnection();
		con.addRequestProperty("Authorization", "KakaoAK " + KaKaoRestKey);
		con.setRequestProperty("X-Requested-With", "curl");
		con.setRequestMethod("GET");
		
		in = con.getInputStream();
		out = new ByteArrayOutputStream();
			buf = new byte[1024 * 8];
			length = 0;
			while ((length = in.read(buf)) != -1) {
				out.write(buf, 0, length);
		}
		searchJson = new String(out.toByteArray(), "UTF-8");	
		
		System.out.println(searchJson);
		con.disconnect();
		
		
		jsonObj = null;

		paser = new JSONParser();
		try {
			jsonObj = (JSONObject) paser.parse(searchJson);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		array = (JSONArray) jsonObj.get("documents");
		JSONObject resultJSON = null;
		for(int i = 0 ; i < array.size() ; i++) {
			JSONObject jOut = (JSONObject) array.get(i);
			
			if(jOut.get("id").equals(locationID)) {
				resultJSON = (JSONObject) array.get(i);
				break;
			}
			
		}
		
		System.out.println(resultJSON.toString());
	}
}
