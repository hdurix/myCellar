'use strict';

angular.module('mycellarApp')
    .factory('Domain', function ($resource, DateUtils) {
        return $resource('api/domains/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
