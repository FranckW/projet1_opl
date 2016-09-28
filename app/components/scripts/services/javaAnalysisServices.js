'use strict';
angular.module('app').service(
    "javaAnalysisServices",
    function ($http, $q) {
        return ({
            getScoreOfClass: getScoreOfClass
        });

        function getScoreOfClass(classContent, repoName, id) {
            var request = $http({
                method: "get",
                url: 'http://localhost:8080/server/getScoreOfClass',
                params: {
                    classContent: classContent,
                    repoName: repoName,
                    id: id
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