# Github Not Fork Repository Lister

## Summary

API Consumer App for retrieving information about GitHub user's non-fork repositories.  
The application leverages GitHub REST API for gathering data.  
*You can read more about GitHub API [here](https://developer.github.com/v3).*

## System Requirements

* JDK 21
* Maven 3
* Git
* Postman (or other tool, like `curl`, for making the REST requests)

## Other Prerequisites

Although it's not mandatory, it's recommended to have and use [GitHub Personal Access Token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens), 
since there is a short rate limit for non-authenticated GitHub API users. The rate limit is significantly higher for authenticated users.  
Instructions on how to set up the `github-not-fork-repo-lister` with the token are listed below, in the following paragraph.

## Building and Running Locally

1. Create folder for the project and clone the project using command:

>git clone https://github.com/arges8/github-not-fork-repo-lister.git
2. If you have GitHub Personal Access Token, place it in `application.properties`, as `github.token` property. 
It should look like this: 

>github.token=<YOUR_TOKEN>

where `<YOUR_TOKEN>` is the real value of your token.

3. To build the project, run from the root directory of the project: 
> mvn clean package
4. And then run the app:
>mvn spring-boot:run

## Using the app
The app is running on `http://localhost:8080`.  
There is only one endpoint that you can use, which is:
>http://localhost:8080/{userLogin}

Where `{userLogin}` is a path variable for github usernames. Example:
>http://localhost:8080/arges8

Creating the request, add `Accept: application/json` header to it.  
Sample response:
```dtd
[
    {
        "repositoryName": "github-not-fork-repo-lister",
        "ownerLogin": "arges8",
        "branches": [
            {
                "branchName": "master",
                "lastCommitSha": "29e582fed042787f10e4fee1a279e16c73f375cc"
            }
        ]
    },
    {
        "repositoryName": "YutubePlaylistDowlnloader",
        "ownerLogin": "arges8",
        "branches": [
            {
                "branchName": "master",
                "lastCommitSha": "a7d96002ed3b1803640b2a714d3d57a09f45256a"
            }
        ]
    }
]
```