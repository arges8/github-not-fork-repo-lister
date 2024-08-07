package com.arges.notforkrepolister.web;

import com.arges.notforkrepolister.model.GithubRepository;
import com.arges.notforkrepolister.model.GithubRepositoryResponse;
import com.arges.notforkrepolister.service.GithubBranchInfoService;
import com.arges.notforkrepolister.service.GithubRepositoriesInfoService;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubRepositoriesInfoController {

    private final GithubRepositoriesInfoService githubRepositoriesInfoService;
    private final GithubBranchInfoService githubBranchInfoService;

    // github restricts username to be at most 39 characters long
    @GetMapping(value = "/{userLogin}", produces = "application/json")
    List<GithubRepositoryResponse> getRepositories(@PathVariable @Size(max = 39) @Pattern(regexp = "[^.\\s*]\\S*") String userLogin) {
        var repos = githubRepositoriesInfoService.findRepositoriesByUserLogin(userLogin);

        return repos.stream()
                .map(repo -> mapToResponse(userLogin, repo))
                .toList();
    }

    private GithubRepositoryResponse mapToResponse(String userLogin, GithubRepository repo) {
        var branches = githubBranchInfoService.getBranches(userLogin, repo.name());
        return new GithubRepositoryResponse(repo.name(), userLogin, branches);
    }
}
