'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bottle', {
                parent: 'entity',
                url: '/bottle',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bottles'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bottle/bottles.html',
                        controller: 'BottleController'
                    }
                },
                resolve: {
                }
            })
            .state('bottleDetail', {
                parent: 'entity',
                url: '/bottle/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Bottle'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bottle/bottle-detail.html',
                        controller: 'BottleDetailController'
                    }
                },
                resolve: {
                }
            });
    });
