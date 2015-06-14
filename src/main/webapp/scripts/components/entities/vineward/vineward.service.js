'use strict';

angular.module('mycellarApp')
    .factory('Vineward', function ($resource, DateUtils) {
        return $resource('api/vinewards/:id', {}, {
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
