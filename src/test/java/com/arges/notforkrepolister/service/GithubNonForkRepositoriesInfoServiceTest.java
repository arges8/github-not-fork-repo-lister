package com.arges.notforkrepolister.service;

import com.arges.notforkrepolister.model.GithubRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.arges.notforkrepolister.util.GithubUrlUtil.*;

@ExtendWith(MockitoExtension.class)
class GithubNonForkRepositoriesInfoServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpEntity httpEntity;

    @InjectMocks
    private GithubNonForkRepositoriesInfoService service;

    private static final String userLogin = "test-user";
    private static final String url = userReposUrl.apply(userLogin);

    @Test
    void verifyRestTemplateCallIsCorrect() {
        // given
        var repos = mockNonForkRepositories();
        var expectedRepos = Arrays.asList(repos);

        // when then
        when(restTemplate.exchange(url, HttpMethod.GET, httpEntity, GithubRepository[].class))
                .thenReturn(new ResponseEntity<>(repos, HttpStatus.OK));

        // verify
        var returnRepos = service.findRepositoriesByUserLogin(userLogin);

        assertEquals(returnRepos.size(), 6);
        assertTrue(expectedRepos.containsAll(returnRepos));
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(httpEntity),
                eq(GithubRepository[].class)
        );
    }

    @Test
    void verifyForkReposNotIncludedInOutput() {
        // given
        var repos = mockRepositoriesWithForks();

        // when then
        when(restTemplate.exchange(url, HttpMethod.GET, httpEntity, GithubRepository[].class))
                .thenReturn(new ResponseEntity<>(repos, HttpStatus.OK));

        // verify
        var returnRepos = service.findRepositoriesByUserLogin(userLogin);

        assertEquals(returnRepos.size(), 4);
        assertTrue(expectedReposWithoutForks()
                .containsAll(returnRepos)
        );


    }

    @Test
    void verifyEmptyListIsReturnedWhenNoRepos() {
        // given
        GithubRepository[] emptyReposArray = new GithubRepository[0];

        // when then
        when(restTemplate.exchange(url, HttpMethod.GET, httpEntity, GithubRepository[].class))
                .thenReturn(new ResponseEntity<>(emptyReposArray, HttpStatus.OK));

        // verify
        var returnRepos = service.findRepositoriesByUserLogin(userLogin);

        assertTrue(returnRepos.isEmpty());
    }

    @Test
    void verifyGithubUserNotFoundExceptionIsThrown() {
        // given
        String nonExistingUser = "i-do-not-exist";
        String nonExistUserUrl = userReposUrl.apply(nonExistingUser);

        // when then
        when(restTemplate.exchange(nonExistUserUrl, HttpMethod.GET, httpEntity, GithubRepository[].class))
                .thenThrow(HttpClientErrorException.NotFound.class);

        // verify
        assertThrows(GithubUserNotFoundException.class,
                () -> service.findRepositoriesByUserLogin(nonExistingUser));
    }

    private GithubRepository[] mockNonForkRepositories() {
        return new GithubRepository[]{
                new GithubRepository("repo1", false),
                new GithubRepository("not-fork-repo-lister", false),
                new GithubRepository("non-forked-repo", false),
                new GithubRepository("Repo Repo", false),
                new GithubRepository("test_repo", false),
                new GithubRepository("r", false)
        };
    }

    private GithubRepository[] mockRepositoriesWithForks() {
        return new GithubRepository[]{
                new GithubRepository("repo1", false),
                new GithubRepository("not-fork-repo-lister", false),
                new GithubRepository("forked-repo", true),
                new GithubRepository("Repo Repo", false),
                new GithubRepository("test_repo", false),
                new GithubRepository("fokred v2", true)
        };
    }

    private List<GithubRepository> expectedReposWithoutForks() {
        return List.of(
                new GithubRepository("repo1", false),
                new GithubRepository("not-fork-repo-lister", false),
                new GithubRepository("Repo Repo", false),
                new GithubRepository("test_repo", false)
        );
    }

}