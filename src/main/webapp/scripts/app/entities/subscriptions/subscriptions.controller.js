'use strict';

angular.module('jhipsterApp')
    .controller('SubscriptionsController', function ($scope, $state, $modal, Subscriptions) {
      
        $scope.subscriptionss = [];
        $scope.loadAll = function() {
            Subscriptions.query(function(result) {
               $scope.subscriptionss = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.subscriptions = {
                subscribtionDateTime: null,
                subscriber: null,
                id: null
            };
        };
    });
