'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bottleLife', {
                parent: 'entity',
                url: '/bottleLife',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BottleLifes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bottleLife/bottleLifes.html',
                        controller: 'BottleLifeController'
                    }
                },
                resolve: {
                }
            })
            .state('bottleLifeDetail', {
                parent: 'entity',
                url: '/bottleLife/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'BottleLife'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bottleLife/bottleLife-detail.html',
                        controller: 'BottleLifeDetailController'
                    }
                },
                resolve: {
                }
            });
    });
