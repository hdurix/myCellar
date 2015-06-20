'use strict';

angular.module('mycellarApp')
    .factory('Bottle', function ($resource, DateUtils) {
        return $resource('api/bottles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'dto': { method: 'GET', params: {dto: 'true'}, isArray: true},
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
