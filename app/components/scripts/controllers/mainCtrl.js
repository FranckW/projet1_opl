'use strict';
angular.module('app').controller('MainCtrl', function ($scope, githubServices) {
    $scope.url = {};
    $scope.loading = false;
    $scope.repoName = "";
    $scope.pullRequests = {};
    $scope.files = {};
    $scope.filesContent = {};

    function updatePullRequests(pullRequestsData) {
        console.log("user : " + pullRequestsData[0].user.login);
        console.log("repo name : " + pullRequestsData[0].head.repo.name);
        $scope.pullRequests = pullRequestsData;
        $scope.loading = true;
        githubServices.getContentOfPullRequest($scope.repoName, 1).then(
                function (filesContentData) {
                    $scope.files = filesContentData;
                    console.log($scope.files[1].raw_url);
                    for (var i = 0; i < $scope.files.length; i++) {
                        console.log($scope.files[i].raw_url);
                        githubServices.getFileContent($scope.files[i].raw_url).then(
                            function (fileContentData) {
                                $scope.loading = false;
                                console.log(fileContentData);
                                $scope.filesContent.push(filesContentData);
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