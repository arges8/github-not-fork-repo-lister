package com.arges.notforkrepolister.service;

import com.arges.notforkrepolister.model.GithubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GithubNonForkRepositoriesInfoService implements GithubRepositoriesInfoService {

    private static final String USER_REPOS_TEMPLATE = "/users/{0}/repos";
    private static final String API_URL = "https://api.github.com";

    private static final Function<String, String> userReposUrl =
            (userLogin) -> API_URL + MessageFormat.format(USER_REPOS_TEMPLATE, userLogin);

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<GithubRepository> findRepositoriesByUserLogin(String userLogin) {
        GithubRepository[] repos = restTemplate.getForObject(userReposUrl.apply(userLogin), GithubRepository[].class);
        return Arrays.asList(repos);
    }
}
