package com.arges.notforkrepolister.service;

import com.arges.notforkrepolister.model.GithubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubRepositoriesInfoService {
    List<GithubRepository> findRepositoriesByUserLogin(String userLogin);
}
