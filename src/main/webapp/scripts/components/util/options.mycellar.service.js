'use strict';

angular.module('mycellarApp')
    .service('MycellarOptions', function () {
        return this.colors = {
            RED : "Rouge",
            WHITE : "Blanc",
            PINK : "Rosé",
            YELLOW : "Jaune"
        }
    });
