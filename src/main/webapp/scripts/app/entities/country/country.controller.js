'use strict';

angular.module('mycellarApp')
    .controller('CountryController', function ($scope, Country, Appellation) {
        $scope.countries = [];
        $scope.appellations = Appellation.query();
        $scope.loadAll = function() {
            Country.query(function(result) {
               $scope.countries = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Country.get({id: id}, function(result) {
                $scope.country = result;
                $('#saveCountryModal').modal('show');
            });
        };

        $scope.saveCountry = function () {
            if ($scope.country.id != null) {
                Country.update($scope.country,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Country.save($scope.country,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Country.get({id: id}, function(result) {
                $scope.country = result;
                $('#deleteCountryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Country.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCountryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveCountryModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.country = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
