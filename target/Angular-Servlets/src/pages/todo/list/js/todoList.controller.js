(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoListCtrl", TodoListCtrl);

    TodoListCtrl.$inject = ['$scope', 'TodoService'];

    function TodoListCtrl($scope, TodoService) {
        $scope.name = "Adi";
        TodoService.list()
            .then(function (res) {
                $scope.todos = res.data;
            }, function () {
                $scope.todos = [];
            });
    }

})();