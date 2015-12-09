'use strict';

angular.module('jhipsterApp')
    .controller('SubscriptionsDetailController', function ($scope, $rootScope, $stateParams, entity, Subscriptions, User, Petition) {
        $scope.subscriptions = entity;
        $scope.load = function (id) {
            Subscriptions.get({id: id}, function(result) {
                $scope.subscriptions = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:subscriptionsUpdate', function(event, result) {
            $scope.subscriptions = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
