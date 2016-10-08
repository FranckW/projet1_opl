package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.json.simple.JSONObject;

import spoon.RankingProcessor;
import spoon.ClassRanking;
import spoon.Launcher;
import spoon.Pwd;
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

	@SuppressWarnings("unchecked")
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		CrossDomainHandler.handle(request, response, getServletContext());

		String repoName = request.getParameter("repoName");
		String pullRequestNumber = request.getParameter("pullRequestNumber");
		PrintWriter out = response.getWriter();
		try {
			File outputDirectory = new File(
					(new File("../").getAbsolutePath()) + "\\" + repoName + (new Random().nextInt(50000)));
			deleteDir(outputDirectory);
			System.out.println("Before clone");
			Git.cloneRepository().setURI("https://github.com/" + repoName + ".git").setDirectory(outputDirectory)
					.call();
			System.out.println("After clone");
			final Launcher launcher = new Launcher();
			final String repositoryPath = outputDirectory.getAbsolutePath();
			final String outputDir = Pwd.getOutputPath();

			launcher.addInputResource(repositoryPath);        
	        launcher.setSourceOutputDirectory(outputDirectory);
	        
	        RankingProcessor rankProcessor = new RankingProcessor();
	        
	        launcher.addProcessor(rankProcessor);
	        launcher.run();
	        
	        
	        System.out.println("Before analyse : ");
	        ClassRanking rank = rankProcessor.getRanking();
	        System.out.println(rank.toString());
	        
	        System.out.println("After analyse : ");
	        rankProcessor.analyse();
	        System.out.println(rank.toString());
	        rankProcessor.analyse();
	        System.out.println(rank.toString());
	        
			out.flush();
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
