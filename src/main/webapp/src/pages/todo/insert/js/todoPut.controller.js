(function () {

    'use strict';

    angular.module("todoApp")
        .controller("TodoPutCtrl", TodoPutCtrl);

    TodoPutCtrl.$inject = ['$scope', 'TodoService'];


    function TodoPutCtrl($scope,TodoService,$window) {

        $scope.myFunction= function()
        {
            TodoService.post12($scope.todo);
            window.location.href="/";
        }
    }
})();