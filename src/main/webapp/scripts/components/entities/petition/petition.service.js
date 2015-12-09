'use strict';

angular.module('jhipsterApp')
    .factory('Petition', function ($resource, DateUtils) {
        return $resource('api/petitions/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationTime = DateUtils.convertDateTimeFromServer(data.creationTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
