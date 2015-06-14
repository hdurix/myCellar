'use strict';

angular.module('mycellarApp')
    .controller('VinewardDetailController', function ($scope, $stateParams, Vineward, Domain, Category) {
        $scope.vineward = {};
        $scope.load = function (id) {
            Vineward.get({id: id}, function(result) {
              $scope.vineward = result;
            });
        };
        $scope.load($stateParams.id);
    });
