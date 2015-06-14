'use strict';

angular.module('mycellarApp')
    .controller('BottleLifeDetailController', function ($scope, $stateParams, BottleLife, Bottle, User) {
        $scope.bottleLife = {};
        $scope.load = function (id) {
            BottleLife.get({id: id}, function(result) {
              $scope.bottleLife = result;
            });
        };
        $scope.load($stateParams.id);
    });
