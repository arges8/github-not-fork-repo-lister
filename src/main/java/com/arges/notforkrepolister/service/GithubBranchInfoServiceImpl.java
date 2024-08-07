package com.arges.notforkrepolister.service;

import static com.arges.notforkrepolister.util.GithubUrlUtil.*;

import com.arges.notforkrepolister.model.GitBranch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.JsonParseException;
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
    private final ObjectMapper objectMapper;

    @Override
    public List<GitBranch> getBranches(String userLogin, String repositoryName) {
        String branchesJson = restTemplate.exchange(
                getUrlForRepoBranches(userLogin, repositoryName),
                HttpMethod.GET,
                httpEntity,
                String.class
        ).getBody();
        try {
            var branches = objectMapper.readValue(branchesJson, GitBranch[].class);
            return Arrays.asList(branches);
        } catch (JsonProcessingException e) {
            throw new JsonParseException(e);
        }
    }
}
