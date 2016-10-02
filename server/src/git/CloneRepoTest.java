package git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

public class CloneRepoTest {

	public static void main(String[] args) throws IOException {
		try {
			String repoName = "OPLTest";
			File outputDirectory = new File((new File("../").getAbsolutePath()) + "\\" + repoName);
			deleteDir(outputDirectory);
			Git.cloneRepository().setURI("https://github.com/FranckW/" + repoName + ".git")
					.setDirectory(outputDirectory).call();
		} catch (GitAPIException e) {
			e.printStackTrace();
		}
	}

	public static void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null)
			for (File f : contents)
				deleteDir(f);
		file.delete();
	}

}
