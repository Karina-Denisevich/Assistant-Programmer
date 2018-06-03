package com.github.karina_denisevich.app.git.service;


import com.github.karina_denisevich.app.git.model.GitRepo;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.ContentsService;
import org.eclipse.egit.github.core.service.DataService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HttpGitRepoService extends RepositoryService implements GitRepoService {

    @Value("${git.token}")
    private String gitToken;

    private GitHubClient client;

    private RepositoryService repositoryService;
    private ContentsService contentsService;
    private DataService dataService;

    @PostConstruct
    public void init() {
        this.client = new GitHubClient();
        client.setOAuth2Token(gitToken);
        repositoryService = new RepositoryService(client);
        contentsService = new ContentsService(client);
        dataService = new DataService(client);
    }

    @Override
    public String getGitRepo(String owner, String name) throws Exception {
        Repository repository = repositoryService.getRepository(owner, name);
        String webPage = repository.getHtmlUrl();
        Document document = Jsoup.connect(webPage).get();
        refactorDoc(document, owner, name);

        return document.html();
    }

    @Override
    public String getGitTree(String owner, String name, String folderName) throws Exception {
        Repository repository = repositoryService.getRepository(owner, name);

        String webPage = repository.getHtmlUrl() + "/tree/master/" + folderName;

        Document document = Jsoup.connect(webPage).get();
        refactorDoc(document, owner, name);

        return document.html();
    }

    @Override
    public String getGitFile(String owner, String name, String folderName) throws Exception {
        Repository repository = repositoryService.getRepository(owner, name);

        String webPage = repository.getHtmlUrl() + "/blob/master/" + folderName;

        Document document = Jsoup.connect(webPage).get();

        refactorDoc(document, owner, name);

        return document.html();
    }

    @Override
    public List<GitRepo> getGitRepositories(final String repoName) throws Exception {
        final List<SearchRepository> searchResult
                = Optional.ofNullable(repositoryService.searchRepositories(repoName))
                .orElse(Collections.emptyList());

        return searchResult.stream()
                .map(repository -> GitRepo.builder()
                        .withId(repository.getId())
                        .withName(repository.getName())
                        .withOwner(repository.getOwner())
                        .withLanguage(repository.getLanguage())
                        .withUrl(repository.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private void refactorDoc(Document document, String owner, String name) {
        document.getElementsByTag("header").remove();
        document.getElementsByClass("footer").remove();

        document.getElementById("js-repo-pjax-container").children().get(0).remove();
        for (Element element : document.getElementById("js-repo-pjax-container").children().get(0).children().get(0).children()) {
            if (!element.hasClass("file-wrap") && !element.hasClass("file")) {
                element.remove();
            } else {
                element.getElementsByClass("file-header").remove();
            }
        }

        for (Element element : document.getElementsByAttribute("href")) {
            if (element.attr("href").contains(owner + '/' + name)) {
                element.attr("href", "/index.html#!/repo".concat(element.attr("href")));
            }
        }

    }
}
