'use strict';

angular.module('mycellarApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('category', {
                parent: 'entity',
                url: '/category',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Categorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/category/categorys.html',
                        controller: 'CategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('categoryDetail', {
                parent: 'entity',
                url: '/category/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Category'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/category/category-detail.html',
                        controller: 'CategoryDetailController'
                    }
                },
                resolve: {
                }
            });
    });
