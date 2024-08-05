package com.arges.notforkrepolister.web;

import com.arges.notforkrepolister.model.GithubRepository;
import com.arges.notforkrepolister.service.GithubRepositoriesInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GithubRepositoriesInfoController {

    private final GithubRepositoriesInfoService githubRepositoriesInfoService;

    // TODO: remove hardcode, change return type to List<GithubRepositoryResponse>
    @GetMapping("/")
    List<GithubRepository> getRepositories() {
        return githubRepositoriesInfoService.findRepositoriesByUserLogin("arges8");
    }
}
