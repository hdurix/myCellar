'use strict';

angular.module('mycellarApp')
    .controller('BottleController', function ($scope, $filter, Bottle, Country, Category, BottleLife,
            ParseLinks, Principal, MycellarOptions) {
        $scope.bottles = [];
        $scope.filteredBottles = [];
        $scope.page = 1;

        $scope.options = MycellarOptions;

        $scope.config = {
            itemsPerPage: 12,
            fillLastPage: true
        }

        $scope.search = {category :{}};

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

        $scope.loadAll = function() {
            Bottle.dto(function(data) {
                $scope.bottles = data;
                Principal.identity().then(function(account) {
                    $scope.user = account.login;
                    var f = _.findWhere($scope.bottles, {user : $scope.user});
                    if (angular.isDefined(f)) {
                        $scope.search.user = $scope.user;
                    }
                    $scope.filterBottles();
                });
            });
        };
        $scope.loadAll();

        $scope.drinkBottle = function(bottle) {
            var b = {
                id:             bottle.id,
                drinkedDate:    new Date(),
                number:     1
            };
            Bottle.drink(b, $scope.loadAll);
        }

        $scope.buyBottle = function(bottle) {
            var b = {
                price:      bottle.price,
                year:       bottle.year,
                category:   bottle.category,
                number:     1
            };
            Bottle.createFromDto(b, $scope.loadAll);
        }

        $scope.summary = function(bottle) {
            var dates = "";
            _.each(_.pluck(bottle.bottleLifes, 'drinkedDate'), function(b) {
                if(b != null) {
                    dates += moment(b).format('L') + "<br/> ";
                }
            });
            return dates;
        }
    });
