'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('vineward', {
                parent: 'entity',
                url: '/vineward',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Vinewards'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vineward/vinewards.html',
                        controller: 'VinewardController'
                    }
                },
                resolve: {
                }
            })
            .state('vinewardDetail', {
                parent: 'entity',
                url: '/vineward/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Vineward'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/vineward/vineward-detail.html',
                        controller: 'VinewardDetailController'
                    }
                },
                resolve: {
                }
            });
    });
