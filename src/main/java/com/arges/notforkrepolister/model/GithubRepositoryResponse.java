package com.arges.notforkrepolister.model;

import java.util.List;

public record GithubRepositoryResponse(String repositoryName,
                                       String ownerLogin, List<GitBranchResponse> branches) {}
