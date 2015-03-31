package com.staticserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.common.Config;
public class StaticURLServer {

	private ServerSocket serverSocket;
	
	private boolean shutDown = false;
	
	private static final String CONFIGFILE = "." + File.separator + "config/config";
	
	
	public void await() {
		try {
			serverSocket = new ServerSocket(Config.getConfig().getPort(), 1, 
					InetAddress.getByName(Config.getConfig().getIp()));
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
	
	
	public static void main(String[] arg) throws FileNotFoundException, IOException {
		StaticURLServer server = new StaticURLServer();
		Config.getConfig(CONFIGFILE);
		server.await();
	}
	
	
}
