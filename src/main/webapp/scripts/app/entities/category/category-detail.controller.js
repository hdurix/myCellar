'use strict';

angular.module('mycellarApp')
    .controller('CategoryDetailController', function ($scope, $stateParams, Category, Vineward, Bottle) {
        $scope.category = {};
        $scope.load = function (id) {
            Category.get({id: id}, function(result) {
              $scope.category = result;
            });
        };
        $scope.load($stateParams.id);
    });
