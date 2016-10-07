'use strict';
angular.module('app').controller('MainCtrl', function ($scope, $rootScope, githubServices, javaAnalysisServices, $sce) {
    $scope.url = {};
    $scope.loading = false;
    $scope.repoName = "";
    $scope.pullRequests = {};
    $scope.files = {};
    $scope.filesContent = [];
    $scope.currentCall = 0;

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
            javaAnalysisServices.cloneRepo(forkRepoName, $scope.pullRequests[i].number).then(
                function (data) {
                    //utiliser les events pour éviter l'asynchrone, tester avec des print côté java aussi que c'est bien devenu synchrone'
                    githubServices.getContentOfPullRequest($scope.repoName, data.pullRequestNumber).then(
                        function (filesContentData) {
                            $scope.files = filesContentData;
                            for (var j = 0; j < $scope.files.length; j++) {
                                var urlStart = 'https://raw.githubusercontent.com/' + $scope.repoName;
                                var indexOfRaw = $scope.files[j].raw_url.indexOf('/raw');
                                var urlGithubContent = urlStart + $scope.files[j].raw_url.substring(indexOfRaw + 4);
                                $scope.loading = true;
                                githubServices.getFileContent(urlGithubContent).then(
                                    function (fileContentData) {
                                        var score = 1;
                                        if ($scope.files[$scope.currentCall].status == 'added' || $scope.files[$scope.currentCall].status == 'removed')
                                            var content = fileContentData;
                                        else {
                                            var content = $scope.files[$scope.currentCall].patch;
                                            content = replaceAddAndRemove(content);
                                            content = $sce.trustAsHtml(linesAsString(content));
                                        }
                                        var fileInfos = {
                                            "id": $scope.files[$scope.currentCall].sha,
                                            "filename": $scope.files[$scope.currentCall].filename,
                                            "content": content,
                                            "score": 1,
                                            "forkRepo": forkRepoUrl.substring(0, forkRepoUrl.length - 4),
                                            "status": $scope.files[$scope.currentCall].status,
                                        };
                                        $scope.filesContent[$scope.currentCall] = fileInfos;
                                        var file = $scope.filesContent[$scope.currentCall];
                                        if (file.filename.endsWith(".class"))
                                            $scope.filesContent[$scope.currentCall].score = 0;
                                        $scope.currentCall++;
                                        if (file.filename.endsWith(".java"))
                                            javaAnalysisServices.getScoreOfClass(file.filename.substring(0, file.filename.length - 5), $scope.repoName, file.id).then(
                                                function (scoreOfClass) {
                                                    $scope.loading = false;
                                                    for (var k = 0; k < $scope.filesContent.length; k++)
                                                        if ($scope.filesContent[k].id == scoreOfClass.id && scoreOfClass.value > 0)
                                                            $scope.filesContent[k].score = scoreOfClass.value;
                                                    $scope.filesContent.sort(sortFilesContentCompareMethod);
                                                    prettyPrint();
                                                });
                                    });
                            }
                        });
                });
        }
    };

    function sortFilesContentCompareMethod(fileA, fileB) {
        return fileB.score - fileA.score
    };

    $scope.submitUrl = function () {
        $scope.repoName = "";
        $scope.pullRequests = {};
        $scope.files = {};
        $scope.filesContent = [];
        $scope.currentCall = 0;
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
});