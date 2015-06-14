'use strict';

angular.module('mycellarApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


