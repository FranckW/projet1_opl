'use strict';
angular.module('app').service(
    "javaAnalysisServices",
    function ($http, $q) {
        return ({
            getScoreOfClass: getScoreOfClass,
            cloneRepo: cloneRepo
        });

        function getScoreOfClass(className, repoName, id) {
            var request = $http({
                method: "get",
                url: 'http://localhost:8080/server/getScoreOfClass',
                params: {
                    className: className,
                    repoName: repoName,
                    id: id
                }
            });
            return (request.then(handleSuccess, handleError));
        }

        function cloneRepo(repoName) {
            var request = $http({
                method: "get",
                url: 'http://localhost:8080/server/cloneRepo',
                params: {
                    repoName: repoName
                }
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