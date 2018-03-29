(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoUpdtCtrl", TodoUpdtCtrl);

    TodoUpdtCtrl.$inject = ['$scope', '$routeParams', 'TodoService'];

    function TodoUpdtCtrl($scope,$routeParams, TodoService) {
        TodoService.get($routeParams.todoId)
            .then(function (res) {
                $scope.todo = res.data;
            }, function () {
                $scope.todo = [];
            });

        $scope.myFunction = function () {

            TodoService.put($routeParams.todoId,$scope.todo)
                .then(function (res) {
                    window.location.href="/";
                });

        }

    }

})();