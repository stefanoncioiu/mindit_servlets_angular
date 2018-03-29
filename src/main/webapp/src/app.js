(function () {
    'use strict';

    angular.module('todoApp', ['ngRoute'])
        .config(Config);

    Config.$inject = ['$routeProvider', '$locationProvider'];

    function Config($routeProvider, $locationProvider) {
        $locationProvider.hashPrefix('');

        $routeProvider
            .when('/',
            {
                templateUrl: 'src/pages/todo/list/list.html',
                controller: 'TodoListCtrl'

            })
            .when('/todoDetail/:todoId',
                {
                    templateUrl: 'src/pages/todo/details/detail.html',
                    controller: 'TodoDetailCtrl'
                })
            .when('/todoDelete/:todoId',
                {
                    templateUrl: 'src/pages/todo/delete/delete.html',
                    controller: 'TodoDeleteCtrl'
                })
            .when('/todoPut',
                {
                    templateUrl: 'src/pages/todo/insert/insert.html',
                    controller: 'TodoPutCtrl'
                })
            .when('/todoUpdate/:todoId',
                {
                    templateUrl: 'src/pages/todo/update/update.html',
                    controller: 'TodoUpdtCtrl'
                })
            .otherwise({redirectTo: '/'});
    }

})();
