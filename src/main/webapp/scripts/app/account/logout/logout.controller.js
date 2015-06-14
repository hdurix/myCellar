'use strict';

angular.module('mycellarApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
