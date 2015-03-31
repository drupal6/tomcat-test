package com.staticserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Response {

	private final OutputStream out;
	private final int BUFFSIZE = 1024;
	
	private Request request;
	
	public Response(OutputStream out) {
		this.out = out;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void sendStaticResponse() {
		FileInputStream fin = null;
		try {
			byte[] buff = new byte[BUFFSIZE];
			File file = new File(StaticURLServer.WEBROOT, request.getUrl());
			if(file.exists()) {
				fin = new FileInputStream(file);
				int ch = fin.read(buff, 0, BUFFSIZE);
				while(ch != -1) {
					out.write(buff);
					ch = fin.read(buff, 0, BUFFSIZE);
				}
			} else {
					String errorMessage = "HTTP/1.1 404 File not Found\r\n"
							+ "Content-Type: text/html\r\n"
							+ "Content-Length: 23\r\n"
							+ "\r\n"
							+ "<h1>404 File not Found</h1>";
					out.write(errorMessage.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}
