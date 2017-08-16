package com.github.karina_denisevich.app.git.service;


import com.github.karina_denisevich.app.git.model.GitRepo;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HttpGitRepoService implements GitRepoService {

    @Value("${git.token}")
    private String gitToken;

    private GitHubClient client;

    private RepositoryService repositoryService;

    @PostConstruct
    public void init() {
        this.client = new GitHubClient();
        client.setOAuth2Token(gitToken);
        repositoryService = new RepositoryService(client);
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
}
