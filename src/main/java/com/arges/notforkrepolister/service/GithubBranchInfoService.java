package com.arges.notforkrepolister.service;

import com.arges.notforkrepolister.model.GitBranch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubBranchInfoService {
    List<GitBranch> getBranches(String userLogin, String repositoryName);
}
