'use strict';

angular.module('mycellarApp')
    .controller('BottleController', function ($scope, Bottle, Category, BottleLife, ParseLinks) {
        $scope.bottles = [];
        $scope.categorys = Category.query();
        $scope.bottlelifes = BottleLife.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Bottle.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bottles = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Bottle.get({id: id}, function(result) {
                $scope.bottle = result;
                $('#saveBottleModal').modal('show');
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

        $scope.delete = function (id) {
            Bottle.get({id: id}, function(result) {
                $scope.bottle = result;
                $('#deleteBottleConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Bottle.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteBottleConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveBottleModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.bottle = {year: null, price: null, image: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });