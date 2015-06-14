'use strict';

angular.module('mycellarApp')
    .factory('BottleLife', function ($resource, DateUtils) {
        return $resource('api/bottleLifes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.boughtDate = DateUtils.convertDateTimeFromServer(data.boughtDate);
                    data.drinkedDate = DateUtils.convertDateTimeFromServer(data.drinkedDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
