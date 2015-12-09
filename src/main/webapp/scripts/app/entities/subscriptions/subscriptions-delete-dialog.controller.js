'use strict';

angular.module('jhipsterApp')
	.controller('SubscriptionsDeleteController', function($scope, $modalInstance, entity, Subscriptions) {

        $scope.subscriptions = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Subscriptions.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });