package com.arges.notforkrepolister.service;

import com.arges.notforkrepolister.model.GitBranch;
import com.arges.notforkrepolister.util.GithubUrlUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubBranchInfoServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpEntity httpEntity;

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;

    @InjectMocks
    private GithubBranchInfoServiceImpl service;

    private static final String userLogin = "test-user";
    private static final String repo = "test-repo";
    private static final String url = GithubUrlUtil.getUrlForRepoBranches(userLogin, repo);

    @Test
    void verifyRestTemplateCallIsCorrect() {
        // given
        String branchesJson = """
     [
        {
            "name": "branch1",
            "commit": {
                "sha": "sha1",
                "url": "url1"
            },
            "protected": false
        },
        {
            "name": "branch2",
            "commit": {
                "sha": "sha2",
                "url": "url2"
            },
            "protected": false
        }
    ]""";

        // when then
        when(restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class))
                .thenReturn(new ResponseEntity<>(branchesJson, HttpStatus.OK));

        // verify
        var returnBranches = service.getBranches(userLogin, repo);

        assertTrue(expectedBranches().containsAll(returnBranches));
        verify(restTemplate, times(1)).exchange(
                anyString(),
                eq(HttpMethod.GET),
                eq(httpEntity),
                eq(String.class)
        );
    }

    @Test
    void verifyEmptyListIsReturnedWhenNoBranches() {
        // given
        String branchesJson = "[]";

        // when then
        when(restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class))
                .thenReturn(new ResponseEntity<>(branchesJson, HttpStatus.OK));

        // verify
        var returnBranches = service.getBranches(userLogin, repo);
        assertTrue(returnBranches.isEmpty());
    }

    @Test
    void verifyJsonParseExceptionIsThrown() throws JsonProcessingException {
        // given
        String faultyJson = "[]";

        // when then
        when(restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class))
                .thenReturn(new ResponseEntity<>(faultyJson, HttpStatus.OK));
        when(objectMapper.readValue(faultyJson, GitBranch[].class))
                .thenThrow(JsonProcessingException.class);

        // verify
        assertThrows(JsonParseException.class, () -> service.getBranches(userLogin, repo));
    }

    private List<GitBranch> expectedBranches() {
        return List.of(
          new GitBranch("branch1", "sha1"),
          new GitBranch("branch2", "sha2")
        );
    }

}