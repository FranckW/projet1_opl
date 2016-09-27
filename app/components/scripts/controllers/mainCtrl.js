'use strict';
angular.module('app').controller('MainCtrl', function ($scope, githubServices, javaAnalysisServices) {
    $scope.url = {};
    $scope.loading = false;
    $scope.repoName = "";
    $scope.originalFiles = {};
    $scope.pullRequests = {};
    $scope.files = {};
    $scope.filesContent = {};
    $scope.currentCall = 0;

    function updatePullRequests(pullRequestsData) {
        console.log("user : " + pullRequestsData[0].user.login);
        console.log("repo name : " + pullRequestsData[0].head.repo.name);
        $scope.pullRequests = pullRequestsData;
        $scope.loading = true;
        getFilesContent();
    };

    function getFilesContent() {
        for (var i = 0; i < $scope.pullRequests.length; i++)
            githubServices.getContentOfPullRequest($scope.repoName, $scope.pullRequests[i].number).then(
                function (filesContentData) {
                    $scope.files = filesContentData;
                    for (var j = 0; j < $scope.files.length; j++) {
                        //console.log("patch : " + $scope.files[j].patch);
                        var urlStart = 'https://raw.githubusercontent.com/' + $scope.repoName;
                        var indexOfRaw = $scope.files[j].raw_url.indexOf('/raw');
                        var urlGithubContent = urlStart + $scope.files[j].raw_url.substring(indexOfRaw + 4);
                        $scope.loading = true;
                        githubServices.getFileContent(urlGithubContent).then(
                            function (fileContentData) {
                                $scope.loading = false;
                                var fileInfos = {
                                    "id": $scope.files[$scope.currentCall].sha,
                                    "filename": $scope.files[$scope.currentCall].filename,
                                    "path": $scope.files[$scope.currentCall].path,
                                    "content": fileContentData,
                                    "score": 0
                                };
                                $scope.filesContent[$scope.currentCall] = fileInfos;
                                var file = $scope.filesContent[$scope.currentCall];
                                $scope.currentCall++;
                                if (file.filename.endsWith(".java"))
                                    javaAnalysisServices.getScoreOfClass(file.filename, $scope.repoName, file.id).then(
                                        function (scoreOfClass) {
                                            $scope.loading = false;
                                            $scope.filesContent[scoreOfClass.id].score = scoreOfClass.value;
                                        });
                            });
                    }
                });
    };

    function setOriginalFiles(originalFiles) {
        $scope.originalFiles = originalFiles;
    };


    $scope.submitUrl = function () {
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

            githubServices.getOriginalRepoFiles($scope.repoName).then(
                function (originalFiles) {
                    setOriginalFiles(originalFiles);
                });
        }
    };
});