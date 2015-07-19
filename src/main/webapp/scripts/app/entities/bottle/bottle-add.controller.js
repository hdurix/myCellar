'use strict';

angular.module('mycellarApp')
    .controller('BottleAddController', function ($scope, $state, Appellation,
            Bottle, Country, Category, BottleLife, ParseLinks, MycellarOptions) {

        $scope.bottle = {};
        $scope.country = {};
        $scope.countries = Country.query();

        $scope.saveCountry = function () {
            Country.save($scope.country, function (country) {
                $('#saveCountryModal').modal('hide');
                // Ajout du nouveau pays
                $scope.countries.push(country);
                $scope.country = country;
                $scope.appellations = [];
            });
        };

        $scope.saveAppellation = function () {
            Appellation.save($scope.appellation, function (data) {
                $('#saveAppellationModal').modal('hide');
                // Ajout du nouveau pays
                $scope.country.appellations.push(data);
                $scope.appellation = data;
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

        $scope.addCountry = function() {
            $scope.country = new Country();
            $('#saveCountryModal').modal("show");
        }

        $scope.addAppellation = function() {
            $scope.appellation = new Appellation();
            $scope.appellation.country = _.findWhere($scope.countries, {id : $scope.country.id});
            $('#saveAppellationModal').modal("show");
        }

        $scope.loadAppellations = function() {
            $scope.appellations = Appellation.query({countryId : $scope.country.id});
        }

    });
