(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .factory('PictureSearch', PictureSearch);

    PictureSearch.$inject = ['$resource'];

    function PictureSearch($resource) {
        var resourceUrl =  'api/_search/pictures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
