package com.arges.notforkrepolister.web;

import com.arges.notforkrepolister.model.GitBranch;
import com.arges.notforkrepolister.model.GithubRepository;
import com.arges.notforkrepolister.model.GithubRepositoryResponse;
import com.arges.notforkrepolister.service.GithubBranchInfoService;
import com.arges.notforkrepolister.service.GithubRepositoriesInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubRepositoriesInfoControllerTest {

    @Mock
    private GithubRepositoriesInfoService githubRepositoriesInfoService;

    @Mock
    private GithubBranchInfoService githubBranchInfoService;

    @InjectMocks
    private GithubRepositoriesInfoController controller;

    private static final String userLogin = "test-user";

    @Test
    void verifyGithubRepositoryResponsesAreCreatedCorrectly() {
        // given
        var repos = mockRepositories();

        // when then
        when(githubRepositoriesInfoService.findRepositoriesByUserLogin(userLogin))
                .thenReturn(repos);
        when(githubBranchInfoService.getBranches(userLogin, repos.get(0).name()))
                .thenReturn(mockBranchesForRepo1());
        when(githubBranchInfoService.getBranches(userLogin, repos.get(1).name()))
                .thenReturn(mockBranchesForRepo2());

        // verify
        var returnRepos = controller.getRepositories(userLogin);

        assertTrue(expectedOutput().containsAll(returnRepos));
        verify(githubRepositoriesInfoService, times(1))
                .findRepositoriesByUserLogin(userLogin);
        verify(githubBranchInfoService, times(2))
                .getBranches(anyString(), anyString());
    }

    @Test
    void verifyOutputListIsEmptyWhenNoRepositories() {
        // when then
        when(githubRepositoriesInfoService.findRepositoriesByUserLogin(userLogin))
                .thenReturn(emptyList());

        // verify
        var returnRepos = controller.getRepositories(userLogin);

        assertTrue(returnRepos.isEmpty());
        verify(githubRepositoriesInfoService, times(1))
                .findRepositoriesByUserLogin(userLogin);
        verify(githubBranchInfoService, times(0))
                .getBranches(anyString(), anyString());
    }

    private List<GithubRepository> mockRepositories() {
        return List.of(
                new GithubRepository("repo1", false),
                new GithubRepository("repo2", false)
        );
    }

    private List<GitBranch> mockBranchesForRepo1() {
        return List.of(
                new GitBranch("branch1_1", "sha1_1"),
                new GitBranch("branch1_2", "sha1_2")
        );
    }

    private List<GitBranch> mockBranchesForRepo2() {
        return List.of(
                new GitBranch("branch2_1", "sha2_1"),
                new GitBranch("branch2_2", "sha2_2")
        );
    }

    private List<GithubRepositoryResponse> expectedOutput() {
        return List.of(
                new GithubRepositoryResponse("repo1", userLogin, mockBranchesForRepo1()),
                new GithubRepositoryResponse("repo2", userLogin, mockBranchesForRepo2())
        );
    }

}