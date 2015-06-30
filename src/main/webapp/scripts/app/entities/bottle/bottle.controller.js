'use strict';

angular.module('mycellarApp')
    .controller('BottleController', function ($scope, $filter, Bottle, Country, Category, BottleLife, ParseLinks, MycellarOptions) {
        $scope.bottles = [];
        $scope.filteredBottles = [];
        $scope.categorys = Category.query();
        $scope.bottlelifes = BottleLife.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Bottle.dto(function(data) {
                $scope.bottles = data;
                $scope.filteredBottles = data;
            });
        };
        $scope.loadAll();

        $scope.options = MycellarOptions;

        $scope.config = {
            itemsPerPage: 12,
            fillLastPage: true
        }

        $scope.search = {};

        $scope.allData = Country.withDependencies();

        $scope.showUpdate = function (id) {
            Bottle.get({id: id}, function(result) {
                $scope.bottle = result;
                $('#saveBottleModal').modal('show');
            });
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

        $scope.filterBottles = function() {
            $scope.filteredBottles = $filter('filter')($scope.bottles, $scope.search);
        }
    });
