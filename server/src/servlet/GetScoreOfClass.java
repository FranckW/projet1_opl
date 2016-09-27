package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import utils.CrossDomainHandler;

@WebServlet(name = "/GetScoreOfClass", urlPatterns = { "/getScoreOfClass" })
public class GetScoreOfClass extends HttpServlet {
	private static final long serialVersionUID = 580623158385656254L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	@SuppressWarnings("unchecked")
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CrossDomainHandler.handle(request, response, getServletContext());

		String id = request.getParameter("id");
		String classContent = request.getParameter("classContent");
		String repoName = request.getParameter("repoName");

		PrintWriter out = response.getWriter();
		int score = 10;

		JSONArray ja = new JSONArray();
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("value", score);
		ja.add(jo);

		JSONObject mainObj = new JSONObject();
		mainObj.put("postits", ja);
		out.write(mainObj.toJSONString());

		out.flush();
	}
}
