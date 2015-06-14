'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('appellation', {
                parent: 'entity',
                url: '/appellation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Appellations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/appellation/appellations.html',
                        controller: 'AppellationController'
                    }
                },
                resolve: {
                }
            })
            .state('appellationDetail', {
                parent: 'entity',
                url: '/appellation/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Appellation'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/appellation/appellation-detail.html',
                        controller: 'AppellationDetailController'
                    }
                },
                resolve: {
                }
            });
    });
