'use strict';

angular.module('mycellarApp')
    .controller('BottleAddController', function ($scope, $state, Bottle, Country, Category, BottleLife, ParseLinks, MycellarOptions) {

        $scope.bottle = {};
        $scope.country = {};

        $scope.allData = Country.withDependencies();

        $scope.saveCountry = function () {
            Country.save($scope.country, function (data) {
                $('#saveCountryModal').modal('hide');
                // Ajout du nouveau pays
                $scope.allData.push(data);
                $scope.country = data;
            });
        };

        $scope.save = function () {
            Bottle.createFromDto($scope.bottle,
                function () {
                    $state.go("bottle");
                });
        };

        $scope.changeYear = function() {
            if (angular.isUndefined($scope.bottle.category)) {
                return;
            }
            var existing = _.findWhere($scope.bottle.category.bottles, {year: $scope.bottle.year});
            if(angular.isDefined(existing)) {
                $scope.bottle.price = existing.price;
                $scope.priceDisabled = angular.isDefined(existing.price);
            } else {
                $scope.priceDisabled = false;
            }
        }

    });
