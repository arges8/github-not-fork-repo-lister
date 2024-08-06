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

}