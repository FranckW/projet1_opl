package utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CrossDomainHandler {

	public static void handle(HttpServletRequest request, HttpServletResponse response, ServletContext context) {
		List<String> incomingURLs = new ArrayList<String>();
		incomingURLs.add("http://localhost:9000");
		incomingURLs.add("http://localhost:8080");
		String clientOrigin = request.getHeader("origin");
		String ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-control", "no-cache, no-store");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");
		int myIndex = incomingURLs.indexOf(clientOrigin);
		if (myIndex != -1) {
			response.setHeader("Access-Control-Allow-Origin", clientOrigin);
			response.setHeader("Access-Control-Allow-Methods", "GET POST OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "Content-Type");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Max-Age", "86400");
		}
	}

}
