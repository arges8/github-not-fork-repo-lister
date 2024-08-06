package com.arges.notforkrepolister.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
public class GitBranch {

    @JsonAlias("name")
    private String branchName;
    private String lastCommitSha;

    @JsonProperty("commit")
    private void retrieveCommitSha(Map<String,Object> commit) {
        this.lastCommitSha = (String) commit.get("sha");
    }
}
