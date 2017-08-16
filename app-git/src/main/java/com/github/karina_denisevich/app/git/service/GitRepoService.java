package com.github.karina_denisevich.app.git.service;

import com.github.karina_denisevich.app.git.model.GitRepo;

import java.util.List;
import java.util.Optional;

/**
 * Created by Karyna_Dzenisevich on 04-May-17.
 */
public interface GitRepoService {

    List<GitRepo> getGitRepositories(String gitProfile) throws Exception;

    Optional<GitRepo> getGitRepository(String gitProfile, String repo);
}
