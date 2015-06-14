'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('domain', {
                parent: 'entity',
                url: '/domain',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Domains'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domain/domains.html',
                        controller: 'DomainController'
                    }
                },
                resolve: {
                }
            })
            .state('domainDetail', {
                parent: 'entity',
                url: '/domain/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Domain'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/domain/domain-detail.html',
                        controller: 'DomainDetailController'
                    }
                },
                resolve: {
                }
            });
    });
