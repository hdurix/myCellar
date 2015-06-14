'use strict';

angular.module('mycellarApp')
    .controller('BottleLifeController', function ($scope, BottleLife, Bottle, User, ParseLinks) {
        $scope.bottleLifes = [];
        $scope.bottles = Bottle.query();
        $scope.users = User.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            BottleLife.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bottleLifes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            BottleLife.get({id: id}, function(result) {
                $scope.bottleLife = result;
                $('#saveBottleLifeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.bottleLife.id != null) {
                BottleLife.update($scope.bottleLife,
                    function () {
                        $scope.refresh();
                    });
            } else {
                BottleLife.save($scope.bottleLife,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            BottleLife.get({id: id}, function(result) {
                $scope.bottleLife = result;
                $('#deleteBottleLifeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            BottleLife.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBottleLifeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBottleLifeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bottleLife = {boughtDate: null, drinkedDate: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
