package com.github.karina_denisevich.app.git.service;


import com.github.karina_denisevich.app.git.model.GitRepo;
import org.eclipse.egit.github.core.Repository;

import java.util.List;

public interface GitRepoService {

    List<GitRepo> getGitRepositories(String gitProfile) throws Exception;

    String getGitRepo(String owner, String name) throws Exception;

    String getGitTree(String owner, String name, String folderName) throws Exception;

    String getGitFile(String owner, String name, String folderName) throws Exception;
}
