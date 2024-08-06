package com.arges.notforkrepolister.service;

import static com.arges.notforkrepolister.util.GithubUrlUtil.*;

import com.arges.notforkrepolister.model.GithubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubNonForkRepositoriesInfoService implements GithubRepositoriesInfoService {

    private final RestTemplate restTemplate;
    private final HttpEntity httpEntity;

    @Override
    public List<GithubRepository> findRepositoriesByUserLogin(String userLogin) {
        var repos = restTemplate.exchange(
                userReposUrl.apply(userLogin),
                HttpMethod.GET,
                httpEntity,
                GithubRepository[].class
        ).getBody();
        return Arrays.asList(repos);
    }
}
