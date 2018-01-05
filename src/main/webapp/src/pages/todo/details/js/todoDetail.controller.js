(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoDetailCtrl", TodoDetailCtrl);

    TodoDetailCtrl.$inject = ['$scope', '$routeParams', 'TodoService'];

    function TodoDetailCtrl($scope, $routeParams, TodoService) {
        TodoService.get($routeParams.todoId)
            .then(function (res) {
                $scope.todo = res.data;
            }, function () {
                $scope.todo = {};
            });
    }

})();