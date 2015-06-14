'use strict';

angular.module('mycellarApp')
    .controller('VinewardController', function ($scope, Vineward, Domain, Category, ParseLinks) {
        $scope.vinewards = [];
        $scope.domains = Domain.query();
        $scope.categorys = Category.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Vineward.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.vinewards = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Vineward.get({id: id}, function(result) {
                $scope.vineward = result;
                $('#saveVinewardModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.vineward.id != null) {
                Vineward.update($scope.vineward,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Vineward.save($scope.vineward,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Vineward.get({id: id}, function(result) {
                $scope.vineward = result;
                $('#deleteVinewardConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Vineward.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteVinewardConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveVinewardModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.vineward = {name: null, location: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
