'use strict';

angular.module('mycellarApp')
    .controller('BottleDetailController', function ($scope, $stateParams, Bottle, Category, BottleLife) {
        $scope.bottle = {};
        $scope.load = function (id) {
            Bottle.get({id: id}, function(result) {
              $scope.bottle = result;
            });
        };
        $scope.load($stateParams.id);
    });
