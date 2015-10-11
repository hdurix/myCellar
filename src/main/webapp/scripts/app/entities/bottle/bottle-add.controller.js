'use strict';

angular.module('mycellarApp')
    .controller('BottleAddController', function ($scope, $state, Appellation, Domain, Vineward,
            Bottle, Country, Category, BottleLife, ParseLinks, MycellarOptions) {

        $scope.options = MycellarOptions;

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
                // Ajout de la nouvelle appellation
                $scope.appellations.push(data);
                $scope.appellation = data;
                $scope.domains = [];
            });
        };

        $scope.saveDomain = function () {
            Domain.save($scope.domain, function (data) {
                $('#saveDomainModal').modal('hide');
                // Ajout du nouveau domain
                $scope.domains.push(data);
                $scope.domain = data;
                $scope.vinewards = [];
            });
        };

        $scope.saveVineward = function () {
            Vineward.save($scope.vineward, function (data) {
                $('#saveVinewardModal').modal('hide');
                // Ajout du nouveau vigneron
                $scope.vinewards.push(data);
                $scope.vineward = data;
                $scope.categories = [];
            });
        };

        $scope.saveCategory = function () {
            Category.save($scope.category, function (data) {
                $('#saveCategoryModal').modal('hide');
                // Ajout de la nouvelle catégorie
                $scope.categories.push(data);
                $scope.category = data;
            });
        };

        $scope.save = function () {
            $scope.bottle.category = $scope.category;
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
            $scope.countryDisabled = true;
            $scope.appellation = new Appellation();
            $scope.appellation.country = _.findWhere($scope.countries, {id : $scope.country.id});
            $('#saveAppellationModal').modal("show");
        }

        $scope.addDomain = function() {
            $scope.appellationDisabled = true;
            $scope.domain = new Domain();
            $scope.domain.appellation = _.findWhere($scope.appellations, {id : $scope.appellation.id});
            $('#saveDomainModal').modal("show");
        }

        $scope.addVineward = function() {
            $scope.domainDisabled = true;
            $scope.vineward = new Vineward();
            $scope.vineward.domain = _.findWhere($scope.domains, {id : $scope.domain.id});
            $('#saveVinewardModal').modal("show");
        }

        $scope.addCategory = function() {
            $scope.vinewardDisabled = true;
            $scope.category = new Category();
            $scope.category.vineward = _.findWhere($scope.vinewards, {id : $scope.vineward.id});
            $('#saveCategoryModal').modal("show");
        }

        $scope.loadAppellations = function() {
            $scope.appellations = Appellation.query({countryId : $scope.country.id});
        }

        $scope.loadDomains = function() {
            if (angular.isDefined($scope.appellation) && $scope.appellation !== null) {
                $scope.domains = Domain.query({appellationId : $scope.appellation.id});
            } else {
                $scope.domains = [];
            }
        }

        $scope.loadVinewards = function() {
            if (angular.isDefined($scope.domain) && $scope.domain !== null) {
                $scope.vinewards = Vineward.query({domainId : $scope.domain.id});
            } else {
                $scope.vinewards = [];
            }
        }

        $scope.loadCategories = function() {
            if (angular.isDefined($scope.vineward) && $scope.vineward !== null) {
                $scope.categories = Category.query({vinewardId : $scope.vineward.id});
            } else {
                $scope.vinewards = [];
            }
        }

    });
