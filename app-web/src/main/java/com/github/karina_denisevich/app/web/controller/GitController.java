package com.github.karina_denisevich.app.web.controller;


import com.github.karina_denisevich.app.common.exception.model.DuplicateEntityException;
import com.github.karina_denisevich.app.common.exception.model.UserNotFoundException;
import com.github.karina_denisevich.app.datamodel.Authority;
import com.github.karina_denisevich.app.datamodel.User;
import com.github.karina_denisevich.app.git.model.GitRepo;
import com.github.karina_denisevich.app.git.service.GitRepoService;
import com.github.karina_denisevich.app.web.dto.LinesInfoDto;
import com.github.karina_denisevich.app.web.dto.UserDTO;
import com.github.karina_denisevich.app.web.dto.UserDTOAdmin;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.egit.github.core.Repository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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

    @RequestMapping(value = {"/{owner}/{name}"}, method = RequestMethod.GET)
    public ResponseEntity<?> getRepo(@PathVariable String owner, @PathVariable String name) {
        String gitRepo = "Error";
        try {
            gitRepo = gitRepoService.getGitRepo(owner, name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();

        data.put("repoContent", gitRepo);

        return new ResponseEntity<>(data.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{owner}/{name}/tree/master/**"}, method = RequestMethod.GET)
    public ResponseEntity<?> getRepoTree(@PathVariable String owner, @PathVariable String name, HttpServletRequest req) {
        String gitRepo = "Error";
        try {
            gitRepo = gitRepoService.getGitTree(owner, name, req.getRequestURI().substring(req.getRequestURI().indexOf("/tree/master/") + "/tree/master/".length()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();

        data.put("repoContent", gitRepo);

        return new ResponseEntity<>(data.toString(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/{owner}/{name}/blob/master/**"}, method = RequestMethod.GET)
    public ResponseEntity<?> getRepoFile(@PathVariable String owner, @PathVariable String name, HttpServletRequest req) {
        String gitRepo = "Error";
        try {
            gitRepo = gitRepoService.getGitFile(owner, name, req.getRequestURI().substring(req.getRequestURI().indexOf("/blob/master/") + "/blob/master/".length()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();

        data.put("repoContent", gitRepo);

        return new ResponseEntity<>(data.toString(), HttpStatus.OK);
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
