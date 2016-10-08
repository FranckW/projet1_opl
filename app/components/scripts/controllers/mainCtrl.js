'use strict';
angular.module('app').controller('MainCtrl', function ($scope, $rootScope, githubServices, javaAnalysisServices, $sce) {
    $scope.url = {};
    $scope.loading = false;
    $scope.repoName = "";
    $scope.pullRequests = {};
    $scope.files = {};
    $scope.filesContent = [];
    $scope.currentIndex = 0;
    $scope.forkRepos = {};

    function updatePullRequests(pullRequestsData) {
        $scope.pullRequests = pullRequestsData;
        $scope.loading = true;
        getFilesContent();
    };

    function getFilesContent() {
        for (var i = 0; i < $scope.pullRequests.length; i++) {
            var forkRepoUrl = $scope.pullRequests[i].head.repo.clone_url;
            var githubUrl = 'https://github.com/';
            var forkRepoName = forkRepoUrl.substring(githubUrl.length, forkRepoUrl.length - 4);
            // we map the fork repos with their pull request number to be accessed later even if it is asynchronous 
            $scope.forkRepos[$scope.pullRequests[i].number] = forkRepoUrl.substring(0, forkRepoUrl.length - 4);
            cloneRepo(forkRepoName, i);
        }
    };

    function cloneRepo(forkRepoName, i) {
        javaAnalysisServices.cloneRepo(forkRepoName, $scope.pullRequests[i].number).then(
            function (data) {
                githubServices.getContentOfPullRequest($scope.repoName, data.pullRequestNumber).then(
                    function (filesContentData) {
                        for (var j = $scope.currentIndex; j < $scope.currentIndex + filesContentData.length; j++) {
                            $scope.files[j] = filesContentData[j - $scope.currentIndex];
                            var urlStart = 'https://raw.githubusercontent.com/' + $scope.repoName;
                            var indexOfRaw = $scope.files[j].raw_url.indexOf('/raw');
                            var urlGithubContent = urlStart + $scope.files[j].raw_url.substring(indexOfRaw + 4);
                            $scope.loading = true;
                            getContentFile(urlGithubContent, j, data.pullRequestNumber);
                        }
                        // we need to increase the current index to add files without overwriting files handled
                        // in another loop since it is asynchronous 
                        $scope.currentIndex += filesContentData.length;
                    });
            });
    }

    function getContentFile(urlGithubContent, index, pullRequestNumber) {
        githubServices.getFileContent(urlGithubContent).then(
            function (fileContentData) {
                var score = 1;
                if ($scope.files[index].status == 'added' || $scope.files[index].status == 'removed')
                    var content = fileContentData;
                else {
                    var content = $scope.files[index].patch;
                    content = replaceAddAndRemove(content);
                    content = $sce.trustAsHtml(linesAsString(content));
                }
                var fileInfos = {
                    "id": $scope.files[index].sha,
                    "filename": $scope.files[index].filename,
                    "content": content,
                    "score": 1,
                    "forkRepo": $scope.forkRepos[pullRequestNumber],
                    "status": $scope.files[index].status,
                    "pullRequestNumber": pullRequestNumber,
                };
                $scope.filesContent[index] = fileInfos;
                var file = $scope.filesContent[index];
                scoreTheFile(file, index);
            });
    }

    function scoreTheFile(file, index) {
        if (file.filename.endsWith(".class"))
            $scope.filesContent[index].score = 0;
        if (file.filename.endsWith(".java")) {
            var begin = file.filename.lastIndexOf('/') > -1 ? file.filename.lastIndexOf('/') + 1 : 0;
            javaAnalysisServices.getScoreOfClass(file.filename.substring(begin, file.filename.length - 5), $scope.repoName, file.id).then(
                function (scoreOfClass) {
                    $scope.loading = false;
                    for (var k = 0; k < $scope.filesContent.length; k++)
                        if ($scope.filesContent[k].id == scoreOfClass.id && scoreOfClass.value > 0)
                            $scope.filesContent[k].score = scoreOfClass.value;
                    $scope.filesContent.sort(sortFilesContentCompareMethod);
                    // trigger pretty printing to highlight changes on the file
                    prettyPrint();
                });
        }
    }

    function sortFilesContentCompareMethod(fileA, fileB) {
        return fileB.score - fileA.score
    };

    $scope.styleClass = function (fileContent) {
        if (fileContent.status == "added")
            return 'classStyleAdded';
        if (fileContent.status == "removed")
            return 'classStyleRemoved';
    };

    function replaceAddAndRemove(fileContent) {
        var searchStr = '-\t';
        var searchStrLen = searchStr.length;
        var lines;
        if (searchStrLen == 0) {
            return [];
        }
        var startIndex = 0, index, indices = [];
        while ((index = fileContent.indexOf(searchStr, startIndex)) > -1) {
            indices.push(index);
            startIndex = index + searchStrLen;
        }
        lines = difflib.stringAsLines(fileContent);
        for (var i = 0; i < lines.length; i++) {
            if (lines[i].startsWith('-')) {
                lines[i] = '<span class="classStyleLineRemoved">' + lines[i] + '</span>';
            }
            if (lines[i].startsWith('+')) {
                lines[i] = '<span class="classStyleLineAdded">' + lines[i] + '</span>';
            }
        }
        return lines;
    };

    function linesAsString(lines) {
        var str = '';
        for (var i = 0; i < lines.length; i++)
            str += lines[i] + '\n';
        return str;
    };

    $scope.submitUrl = function () {
        $scope.repoName = "";
        $scope.pullRequests = {};
        $scope.files = {};
        $scope.filesContent = [];
        $scope.currentIndex = 0;
        $scope.loading = true;
        var githubUrl = 'https://github.com/';
        var urlStart = $scope.url.value.substring(0, githubUrl.length);
        if (urlStart == githubUrl) {
            $scope.repoName = $scope.url.value.substring(githubUrl.length, $scope.url.value.length);
            githubServices.getAllPullRequests($scope.repoName).then(
                function (pullRequestsData) {
                    $scope.loading = false;
                    updatePullRequests(pullRequestsData);
                });
        }
    };
});