package pro.biocontainers.readers.utilities.dockerfile.models;



import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static java.lang.Math.toIntExact;


@Getter
@Setter
public class Project {
    private final static String GITAPI = "https://api.github.com/";
    private final static String REPOS = "repos/";
    public final String gitUrl;

    private long id;

    private String localRepoPath;

    private String repositoryPath;

    private String ownerType;

    private int networkCount;

    private int opneIssues;

    private int forks;

    private int watchers;

    private int stargazers;

    private int subscribers;

    private int size;

    private long firstCommitDate;

    private long repo_id;

    private String dotGitUrl;

    private boolean isForked;


    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project", orphanRemoval = true)
    //private List<Dockerfile> dockerfiles = new ArrayList<>();

    private List<Dockerfile> dockerfiles;


    public Project(){
        this.gitUrl = "http://github.com/repo.git";
        this.repositoryPath = extractRepositoryName("repo/repo");

    }

//    public Project(String gitUrl) throws ParseException {
//        this.gitUrl = gitUrl;
//        this.repositoryPath = extractRepositoryName(gitUrl);
//        GitHubAPIMetaData gitHubAPIMetaData = this.fetchGitHubAPIMetaData();
//        mapGitHubAPIMetaDataToProject(gitHubAPIMetaData, this);
//    }


//    private GitHubAPIMetaData fetchGitHubAPIMetaData() throws ParseException {
//        return GitHubMinerService.getGitHubRepository(GITAPI + REPOS + this.repositoryPath);
//    }

    private String extractRepositoryName(String gitUrl) {
        String gitUrl1 = gitUrl.replaceAll("https://github.com/", "");
        String gitUrl2 = gitUrl1.replaceAll(".git", "");
        return gitUrl2;
    }

    private void mapGitHubAPIMetaDataToProject(GitHubAPIMetaData gitHubAPIMetaData, Project project) throws ParseException {
        //Date created_at = DateExtractor.getDateFromJsonString(gitHubAPIMetaData.created_at);
        project.setLocalRepoPath(gitHubAPIMetaData.name);
        project.setRepo_id(toIntExact(gitHubAPIMetaData.id));
        project.setFirstCommitDate(Long.valueOf(firstCommitDate));
        project.setForks(toIntExact(gitHubAPIMetaData.forks_count));
        project.setSize(toIntExact(gitHubAPIMetaData.size));
        project.setNetworkCount(toIntExact(gitHubAPIMetaData.network_count));
        project.setStargazers(toIntExact(gitHubAPIMetaData.stargazers_count));
        project.setOpneIssues(toIntExact(gitHubAPIMetaData.open_issues));
        project.setOwnerType(gitHubAPIMetaData.owner.type);
        project.setWatchers(toIntExact(gitHubAPIMetaData.watchers_count));
        project.setSubscribers(toIntExact(gitHubAPIMetaData.subscribers_count));
        project.setDotGitUrl(gitHubAPIMetaData.git_url);
        project.setForked(gitHubAPIMetaData.fork);
    }
}
