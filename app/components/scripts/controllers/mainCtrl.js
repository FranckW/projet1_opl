'use strict';
angular.module('app').controller('MainCtrl', function ($scope, $rootScope, githubServices, javaAnalysisServices) {
    $scope.url = {};
    $scope.loading = false;
    $scope.repoName = "";
    $scope.originalFiles = {};
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
            githubServices.getContentOfPullRequest($scope.repoName, $scope.pullRequests[i].number).then(
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
                                var fileInfos = {
                                    "id": $scope.files[$scope.currentCall].sha,
                                    "filename": $scope.files[$scope.currentCall].filename,
                                    "content": fileContentData,
                                    "score": 1,
                                    "forkRepo": forkRepoUrl.substring(0, forkRepoUrl.length - 4)
                                };
                                $scope.filesContent[$scope.currentCall] = fileInfos;
                                var file = $scope.filesContent[$scope.currentCall];
                                if (file.filename.endsWith(".class"))
                                    $scope.filesContent[$scope.currentCall].score = 0;
                                $scope.currentCall++;
                                if (file.filename.endsWith(".java"))
                                    javaAnalysisServices.getScoreOfClass(file.filename, $scope.repoName, file.id).then(
                                        function (scoreOfClass) {
                                            $scope.loading = false;
                                            for (var k = 0; k < $scope.filesContent.length; k++)
                                                if ($scope.filesContent[k].id == scoreOfClass.id)
                                                    $scope.filesContent[k].score = scoreOfClass.value;
                                            $scope.filesContent.sort(sortFilesContentCompareMethod);
                                            //lever event pour traiter les couleurs des méthodes
                                            $rootScope.$broadcast('modifiedFileToAnalyseForHighlighting', { file: {file} });
                                        });
                            });
                    }
                });
        }
    };

    function setOriginalFiles(originalFiles) {
        $scope.originalFiles = originalFiles;
    };

    function sortFilesContentCompareMethod(fileA, fileB) {
        return fileB.score - fileA.score
    };


    $scope.submitUrl = function () {
        $scope.repoName = "";
        $scope.originalFiles = {};
        $scope.pullRequests = {};
        $scope.files = {};
        $scope.filesContent = [];
        $scope.currentCall = 0;
        $scope.loading = true;
        var githubUrl = 'https://github.com/';
        var urlStart = $scope.url.value.substring(0, githubUrl.length);
        if (urlStart == githubUrl) {
            $scope.repoName = $scope.url.value.substring(githubUrl.length, $scope.url.value.length);
            /*javaAnalysisServices.cloneRepo($scope.repoName).then(
                function (data) {
                    //should be empty
                });*/
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

    $rootScope.$on('modifiedFileToAnalyseForHighlighting', function(event, args) {
        console.log(args.file);
    });
    
});