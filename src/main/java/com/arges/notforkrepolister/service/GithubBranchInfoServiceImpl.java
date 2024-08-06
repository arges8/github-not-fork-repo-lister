package com.arges.notforkrepolister.service;

import static com.arges.notforkrepolister.util.GithubUrlUtil.*;

import com.arges.notforkrepolister.model.GitBranch;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubBranchInfoServiceImpl implements GithubBranchInfoService {

    private final RestTemplate restTemplate;
    private final HttpEntity httpEntity;

    @SneakyThrows // TODO: handle(?)
    @Override
    public List<GitBranch> getBranches(String userLogin, String repositoryName) {
        String branchesJson = restTemplate.exchange(
                    getUrlForRepoBranches(userLogin, repositoryName),
                    HttpMethod.GET,
                    httpEntity,
                    String.class
                ).getBody();
        var branches = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .readValue(branchesJson, GitBranch[].class);
        return Arrays.asList(branches);
    }
}
