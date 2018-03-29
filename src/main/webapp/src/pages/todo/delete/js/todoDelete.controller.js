(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoDeleteCtrl", TodoDeleteCtrl);

    TodoDeleteCtrl.$inject = ['$scope', '$routeParams', 'TodoService'];

    function TodoDeleteCtrl($scope, $routeParams, TodoService,$window) {
        TodoService.del($routeParams.todoId)
            .then(function (res) {
            $scope.todos = res.data;
                //window.location.href="/";
                window.setTimeout(function() {
                    window.location.href = "/";
                }, 500);
        }, function () {
            $scope.todos = [];
        });
    }



})();