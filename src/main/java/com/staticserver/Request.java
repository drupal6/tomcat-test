package com.staticserver;

import java.io.IOException;
import java.io.InputStream;

public class Request {

	private final InputStream in;
	private String url;
	
	
	public Request(InputStream in) {
		this.in = in;
	}
	
	public void parse() {
		StringBuffer request = new StringBuffer();
		byte[] buff = new byte[2048];
		int i;
		try {
			i = in.read(buff);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
			return;
		}
		for(int j = 0; j < i; j ++) {
			request.append((char)buff[j]);
		}
		url = parseUrl(request.toString());
		
	}
	
	private String parseUrl(String requestString) {
		System.out.println(requestString);
		int index1 = requestString.indexOf(" ");
		if(index1 == -1) {
			return null;
		}
		index1++;
		int index2 = requestString.indexOf(" ", index1);
		if(index2 < index1) {
			return null;
		}
		return requestString.substring(index1, index2);
	}
	
	public String getUrl() {
		return url;
	}
	
}
