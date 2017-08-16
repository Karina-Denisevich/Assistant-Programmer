package com.github.karina_denisevich.app.git.service.impl;

import com.github.karina_denisevich.app.git.model.GitRepo;
import com.github.karina_denisevich.app.git.service.GitRepoService;
import org.eclipse.egit.github.core.SearchRepository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Karyna_Dzenisevich on 04-May-17.
 */
@Service
public class GitRepoServiceImpl implements GitRepoService {

    @Value("${git.token}")
    private String gitToken;

    @Override
    public List<GitRepo> getGitRepositories(String repoName) throws Exception {

        GitHubClient client = new GitHubClient();
        client.setOAuth2Token(gitToken);
        RepositoryService service = new RepositoryService(client);
        List<SearchRepository> searchRes = service.searchRepositories(repoName);
        List<GitRepo> repos = new ArrayList<>();
        Integer i = 1;
        for (SearchRepository searchRepository : searchRes) {
            GitRepo gitRepo = new GitRepo();
            gitRepo.setId(i);
            gitRepo.setName(searchRepository.getName());
            gitRepo.setOwner(searchRepository.getOwner());
            gitRepo.setUrl(searchRepository.getUrl());
            gitRepo.setLanguage(searchRepository.getLanguage());
            i++;

            repos.add(gitRepo);
        }

        return repos;

    }

    @Override
    public Optional<GitRepo> getGitRepository(String gitProfile, String repo) {
        return null;
    }
}
