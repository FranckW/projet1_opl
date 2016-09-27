'use strict';
angular.module('app').controller('MainCtrl', function ($scope, githubServices) {
    $scope.url = {};
    $scope.loading = false;
    $scope.repoName = "";
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
        githubServices.getContentOfPullRequest($scope.repoName, 1).then(
            function (filesContentData) {
                $scope.files = filesContentData;
                console.log($scope.files[1].raw_url);
                for (var i = 0; i < $scope.files.length; i++) {
                    console.log($scope.files[i].raw_url);
                    var urlStart = 'https://raw.githubusercontent.com/' + $scope.repoName;
                    var indexOfRaw = $scope.files[i].raw_url.indexOf('/raw');
                    var urlGithubContent = urlStart + $scope.files[i].raw_url.substring(indexOfRaw + 4);
                    //$scope.files[i].filename
                    githubServices.getFileContent(urlGithubContent).then(
                        function (fileContentData) {
                            $scope.loading = false;
                            console.log(fileContentData);
                            var fileInfos = { "filename": $scope.files[$scope.currentCall].filename, "content": fileContentData };
                            $scope.filesContent[$scope.currentCall] = fileInfos;
                            $scope.currentCall ++;
                        });
                }
            });
    };

    $scope.submitUrl = function () {
        $scope.loading = true;
        var githubUrl = 'https://github.com/';
        var urlStart = $scope.url.value.substring(0, githubUrl.length);
        if(urlStart == githubUrl) {
            $scope.repoName = $scope.url.value.substring(githubUrl.length, $scope.url.value.length);
            githubServices.getAllPullRequests($scope.repoName).then(
                function (pullRequestsData) {
                    $scope.loading = false;
                    updatePullRequests(pullRequestsData);
                });
        }
    };
});