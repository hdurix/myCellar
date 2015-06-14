'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('country', {
                parent: 'entity',
                url: '/country',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Countrys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/countrys.html',
                        controller: 'CountryController'
                    }
                },
                resolve: {
                }
            })
            .state('countryDetail', {
                parent: 'entity',
                url: '/country/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Country'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/country/country-detail.html',
                        controller: 'CountryDetailController'
                    }
                },
                resolve: {
                }
            });
    });
