package com.dartcrap.datafeed.util;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebScrappUtil {
	private final static Logger logger = LoggerFactory.getLogger(WebScrappUtil.class);

	
	public static Document post(String url, Map<String, String> arg) {
		Document doc;
		try {
			Connection conn = Jsoup.connect(url).timeout(1000000);
//			for (Map.Entry<String, String> entry : arg.entrySet()) {
//				conn.data(entry.getKey(), entry.getValue());
//				logger.info("Entry : {},{}", entry.getKey(), entry.getValue());
//			}
//			doc = conn.post();
			doc = conn.data(arg).userAgent("Mozillar").post();
			return doc;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document post(String url, Map<String, String> arg, int timeOut) {
		Document doc;
		try {
			Connection conn = Jsoup.connect(url).timeout(timeOut);
			for (Map.Entry<String, String> entry : arg.entrySet()) {
				conn.data(entry.getKey(), entry.getValue());
			}
			doc = conn.post();
			return doc;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Document get(String url){
		Document doc;
		try {
			Connection conn = Jsoup.connect(url).timeout(1000000);
			doc = conn.get();
			return doc;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Document get(String url, Map<String, String> arg) {
		Document doc;
		try {
			Connection conn = Jsoup.connect(url).timeout(1000000);
			for (Map.Entry<String, String> entry : arg.entrySet()) {
				conn.data(entry.getKey(), entry.getValue());
			}
			doc = conn.get();
			return doc;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
