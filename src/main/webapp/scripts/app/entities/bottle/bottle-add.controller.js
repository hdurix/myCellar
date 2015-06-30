'use strict';

angular.module('mycellarApp')
    .controller('BottleAddController', function ($scope, Bottle, Country, Category, BottleLife, ParseLinks, MycellarOptions) {

        $scope.bottle = {};
        $scope.country = {};

        $scope.allData = Country.withDependencies();

        $scope.saveCountry = function () {
            Country.save($scope.country, function (data) {
                $('#saveCountryModal').modal('hide');
                // Ajout du nouveau pays
                $scope.allData.push(data);
                $scope.bottle.country = data;
            });
        };

        $scope.save = function () {
            if ($scope.bottle.id != null) {
                Bottle.update($scope.bottle,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Bottle.save($scope.bottle,
                    function () {
                        $scope.refresh();
                    });
            }
        };

    });
