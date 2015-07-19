'use strict';

angular.module('mycellarApp')
    .controller('AppellationController', function ($scope, Appellation, Country, Domain) {
        $scope.appellations = [];
        $scope.countries = Country.query();
        $scope.domains = Domain.query();
        $scope.loadAll = function() {
            Appellation.query(function(result) {
               $scope.appellations = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Appellation.get({id: id}, function(result) {
                $scope.appellation = result;
                $('#saveAppellationModal').modal('show');
            });
        };

        $scope.saveAppelation = function () {
            if ($scope.appellation.id != null) {
                Appellation.update($scope.appellation,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Appellation.save($scope.appellation,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Appellation.get({id: id}, function(result) {
                $scope.appellation = result;
                $('#deleteAppellationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Appellation.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAppellationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveAppellationModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.appellation = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
