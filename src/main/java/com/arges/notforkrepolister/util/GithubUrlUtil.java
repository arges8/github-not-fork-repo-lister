package com.arges.notforkrepolister.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GithubUrlUtil {
    public static final String API_URL = "https://api.github.com";
    public static final String USERS_REPOS_TEMPLATE = "/users/{0}/repos";
    public static final String REPO_BRANCHES_TEMPLATE = "/repos/{0}/{1}/branches";

    public static final Function<String, String> userReposUrl =
            (userLogin) -> API_URL + MessageFormat.format(USERS_REPOS_TEMPLATE, userLogin);

    public static String getUrlForRepoBranches(String userLogin, String repositoryName) {
        return API_URL + MessageFormat.format(REPO_BRANCHES_TEMPLATE, userLogin, repositoryName);
    }
}
