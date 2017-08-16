package com.github.karina_denisevich.app.git.service;


import com.github.karina_denisevich.app.git.model.GitRepo;

import java.util.List;

public interface GitRepoService {

    List<GitRepo> getGitRepositories(String gitProfile) throws Exception;
}
