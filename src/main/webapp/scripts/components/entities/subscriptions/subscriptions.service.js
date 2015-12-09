'use strict';

angular.module('jhipsterApp')
    .factory('Subscriptions', function ($resource, DateUtils) {
        return $resource('api/subscriptionss/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.subscribtionDateTime = DateUtils.convertDateTimeFromServer(data.subscribtionDateTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
