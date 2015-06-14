'use strict';

angular.module('mycellarApp')
    .controller('DomainDetailController', function ($scope, $stateParams, Domain, Appellation, Vineward) {
        $scope.domain = {};
        $scope.load = function (id) {
            Domain.get({id: id}, function(result) {
              $scope.domain = result;
            });
        };
        $scope.load($stateParams.id);
    });
