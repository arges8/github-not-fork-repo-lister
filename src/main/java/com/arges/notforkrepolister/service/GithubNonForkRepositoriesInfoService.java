package com.arges.notforkrepolister.service;

import static com.arges.notforkrepolister.util.GithubUrlUtil.*;

import com.arges.notforkrepolister.model.GithubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GithubNonForkRepositoriesInfoService implements GithubRepositoriesInfoService {

    private final RestTemplate restTemplate;

    @Override
    public List<GithubRepository> findRepositoriesByUserLogin(String userLogin) {
        GithubRepository[] repos = restTemplate.getForObject(userReposUrl.apply(userLogin), GithubRepository[].class);
        return Arrays.asList(repos);
    }
}
