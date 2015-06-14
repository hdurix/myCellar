'use strict';

angular.module('mycellarApp')
    .controller('CountryDetailController', function ($scope, $stateParams, Country, Appellation) {
        $scope.country = {};
        $scope.load = function (id) {
            Country.get({id: id}, function(result) {
              $scope.country = result;
            });
        };
        $scope.load($stateParams.id);
    });
