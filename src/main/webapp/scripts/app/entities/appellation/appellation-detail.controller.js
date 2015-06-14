'use strict';

angular.module('mycellarApp')
    .controller('AppellationDetailController', function ($scope, $stateParams, Appellation, Country, Domain) {
        $scope.appellation = {};
        $scope.load = function (id) {
            Appellation.get({id: id}, function(result) {
              $scope.appellation = result;
            });
        };
        $scope.load($stateParams.id);
    });
