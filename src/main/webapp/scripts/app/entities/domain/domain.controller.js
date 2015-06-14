'use strict';

angular.module('mycellarApp')
    .controller('DomainController', function ($scope, Domain, Appellation, Vineward) {
        $scope.domains = [];
        $scope.appellations = Appellation.query();
        $scope.vinewards = Vineward.query();
        $scope.loadAll = function() {
            Domain.query(function(result) {
               $scope.domains = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Domain.get({id: id}, function(result) {
                $scope.domain = result;
                $('#saveDomainModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.domain.id != null) {
                Domain.update($scope.domain,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Domain.save($scope.domain,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Domain.get({id: id}, function(result) {
                $scope.domain = result;
                $('#deleteDomainConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Domain.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDomainConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveDomainModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.domain = {name: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
