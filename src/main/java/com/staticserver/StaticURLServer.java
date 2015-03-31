package com.staticserver;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class StaticURLServer {

	private ServerSocket serverSocket;
	
	private boolean shutDown = false;
	
	public static final String WEBROOT = "." + File.separator + "webroot";
	
	private static final String SHUTDOWN = "SHUTDOWN";
	
	public void await() {
		try {
			serverSocket = new ServerSocket(8081, 1, InetAddress.getByName("127.0.0.1"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		while (false == shutDown) {
			Socket socket = null;
			InputStream in = null;
			OutputStream out = null;
			try {
				socket = serverSocket.accept();
				in = socket.getInputStream();
				out = socket.getOutputStream();
				Request request = new Request(in);
				request.parse();
				Response response = new Response(out);
				response.setRequest(request);
				response.sendStaticResponse();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
	
	public static void main(String[] arg) {
		StaticURLServer server = new StaticURLServer();
		server.await();
	}
}
