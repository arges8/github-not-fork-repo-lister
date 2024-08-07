package com.arges.notforkrepolister.web;

import com.arges.notforkrepolister.model.GithubRepository;
import com.arges.notforkrepolister.model.GithubRepositoryResponse;
import com.arges.notforkrepolister.service.GithubBranchInfoService;
import com.arges.notforkrepolister.service.GithubRepositoriesInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubRepositoriesInfoController {

    private final GithubRepositoriesInfoService githubRepositoriesInfoService;
    private final GithubBranchInfoService githubBranchInfoService;

    @GetMapping(value = "/{userLogin}", produces = "application/json")
    List<GithubRepositoryResponse> getRepositories(@PathVariable String userLogin) {
        try {
            var repos = githubRepositoriesInfoService.findRepositoriesByUserLogin(userLogin);

            return repos.stream()
                    .filter(repo -> !repo.fork())
                    .map(repo -> mapToResponse(userLogin, repo))
                    .toList();
        } catch (HttpClientErrorException.NotFound exception) {
            throw new GithubUserNotFoundException(userLogin);
        }
    }

    private GithubRepositoryResponse mapToResponse(String userLogin, GithubRepository repo) {
        var branches = githubBranchInfoService.getBranches(userLogin, repo.name());
        return new GithubRepositoryResponse(repo.name(), userLogin, branches);
    }
}
