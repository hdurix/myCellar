'use strict';

angular.module('mycellarApp')
    .filter('missing', function () {
        return function(input) {
            return input || 'Non renseigné';
        };
    })
    .filter('euro', function ($filter) {
        return function(input) {
            if (angular.isDefined(input) && input !== null) {
                return $filter('number') (input, '2') + " €";
            }
        };
    });
