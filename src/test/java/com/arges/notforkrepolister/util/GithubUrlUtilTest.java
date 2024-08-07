package com.arges.notforkrepolister.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.arges.notforkrepolister.util.GithubUrlUtil.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GithubUrlUtilTest {

    @ParameterizedTest
    @MethodSource("loginUrlPairs")
    void verifyUserReposUrlStringIsCorrect(String login, String expectedUrl) {
        assertEquals(expectedUrl, userReposUrl.apply(login));
    }

    @ParameterizedTest
    @MethodSource("loginRepoUrlArgs")
    void verifyRepoBranchesUrlStringIsCorrect(String login, String repo, String expectedUrl) {
        assertEquals(expectedUrl, getUrlForRepoBranches(login, repo));
    }

    private static Stream<Arguments> loginUrlPairs() {
        return Stream.of(
            Arguments.of("john", "https://api.github.com/users/john/repos"),
            Arguments.of("", "https://api.github.com/users//repos"),
            Arguments.of("kotlin", "https://api.github.com/users/kotlin/repos"),
            Arguments.of("atipera", "https://api.github.com/users/atipera/repos"),
            Arguments.of(".", "https://api.github.com/users/./repos"),
            Arguments.of("mar-cin", "https://api.github.com/users/mar-cin/repos"),
            Arguments.of("-test-", "https://api.github.com/users/-test-/repos")
        );
    }

    private static Stream<Arguments> loginRepoUrlArgs() {
        return Stream.of(
                Arguments.of("user1", "repo1", "https://api.github.com/repos/user1/repo1/branches"),
                Arguments.of("", "repo1", "https://api.github.com/repos//repo1/branches"),
                Arguments.of(".", "*", "https://api.github.com/repos/./*/branches"),
                Arguments.of("user123", "{}", "https://api.github.com/repos/user123/{}/branches"),
                Arguments.of("super-user", "repo_super", "https://api.github.com/repos/super-user/repo_super/branches"),
                Arguments.of("user1/user2", "repo1/repo2", "https://api.github.com/repos/user1/user2/repo1/repo2/branches")
        );
    }

}