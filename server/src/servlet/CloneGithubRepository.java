package servlet;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import utils.CrossDomainHandler;

@WebServlet(name = "/CloneGithubRepository", urlPatterns = { "/cloneRepo" })
public class CloneGithubRepository extends HttpServlet {
	private static final long serialVersionUID = -5370932161208638580L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}

	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CrossDomainHandler.handle(request, response, getServletContext());

		String repoName = request.getParameter("repoName");
		try {
			File outputDirectory = new File(
					(new File("../").getAbsolutePath()) + "\\" + repoName + (new Random().nextInt(50000)));
			deleteDir(outputDirectory);
			Git.cloneRepository().setURI("https://github.com/" + repoName + ".git").setDirectory(outputDirectory)
					.call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	public void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null)
			for (File f : contents)
				deleteDir(f);
		file.delete();
	}

}
