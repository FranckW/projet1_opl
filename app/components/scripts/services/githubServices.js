'use strict';
angular.module('app').service(
    "githubServices",
    function ($http, $q) {
        return ({
            getAllPullRequests: getAllPullRequests,
            getContentOfPullRequest: getContentOfPullRequest,
            getFileContent: getFileContent,
            getOriginalRepoFiles: getOriginalRepoFiles
        });


        function getAllPullRequests(repoName) {
            var request = $http({
                method: "get",
                url: 'https://api.github.com/repos/' + repoName + '/pulls'
            });
            return (request.then(handleSuccess, handleError));
        }


        /*var deferred = $q.defer();
           this.getAccount = function () {
               return $http.get('https://api.github.com/username/repo')
                   .then(function (response) {
                       // promise is fulfilled
                       deferred.resolve(response.data);
                       // promise is returned
                       return deferred.promise;
                   }, function (response) {
                       // the following line rejects the promise 
                       deferred.reject(response);
                       // promise is returned
                       return deferred.promise;
                   })
               ;
           };*/

        function getContentOfPullRequest(repoName, id) {
            var request = $http({
                method: "get",
                url: 'https://api.github.com/repos/' + repoName + '/pulls/' + id + '/files',
            });
            return (request.then(handleSuccess, handleError));
        }

        function getFileContent(url) {
            var request = $http({
                method: "get",
                headers: {
                    'Accept': 'text/html; charset=utf-8'
                },
                url: url
            });
            return (request.then(handleSuccess, handleError));
        }

        function getOriginalRepoFiles(repoName) {
            var request = $http({
                method: "get",
                url: 'https://api.github.com/repos/' + repoName + '/contents'
            });
            return (request.then(handleSuccess, handleError));
        }

        function handleError(response) {
            if (
                !angular.isObject(response.data) ||
                !response.data.message
            ) {
                return ($q.reject("An unknown error occurred."));
            } return ($q.reject(response.data.message));
        }

        function handleSuccess(response) {
            return (response.data);
        }
    }
);
