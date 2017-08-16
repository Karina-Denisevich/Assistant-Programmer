package com.github.karina_denisevich.app.web.controller;


import com.github.karina_denisevich.app.git.model.GitRepo;
import com.github.karina_denisevich.app.git.service.GitRepoService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/git")
public class GitController {

    private final GitRepoService gitRepoService;

    public GitController(final GitRepoService gitRepoService) {
        this.gitRepoService = gitRepoService;
    }

    @RequestMapping(value = "/{repoName}", method = RequestMethod.GET)
    public ResponseEntity<?> getRepos(@PathVariable final String repoName,
                                      @RequestParam(value = "page", required = false) final Integer page,
                                      @RequestParam(value = "per_page", required = false) final Integer perPage,
                                      @RequestParam(value = "sort", required = false) final Boolean isSort) {
        List<GitRepo> repos = new ArrayList<>();
        try {
            repos = gitRepoService.getGitRepositories(repoName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer total = repos.size();
        Integer perPageCopy = perPage == null ? 10 : perPage;
        if (perPageCopy > total) {
            perPageCopy = total;
        }
        Integer lastPage = (int) Math.ceil((double) total / perPageCopy);
        String nextPageUrl = "";
        if (!Objects.equals(page, lastPage)) {
            nextPageUrl = "http://localhost:8082/api/git/" + repoName + "?page=" + (page + 1);
        }
        String prevPageUrl = "";
        if (page != 1) {
            prevPageUrl = "http://localhost:8082/api/git/" + repoName + "?page=" + (page - 1);
        }
        Integer from = perPageCopy * (page - 1) + 1;
        Integer to = from + perPageCopy - 1;

        repos = repos.subList(from - 1, to);

        JSONObject data = new JSONObject();
        JSONArray jArray = new JSONArray(repos);

        data.put("total", total);
        data.put("per_page", perPageCopy);
        data.put("current_page", page);
        data.put("last_page", lastPage);
        data.put("next_page_url", nextPageUrl);
        data.put("prev_page_url", prevPageUrl);
        data.put("from", from);
        data.put("to", to);

        data.put("data", jArray);

        return new ResponseEntity<>(data.toString(), HttpStatus.OK);
    }
}
