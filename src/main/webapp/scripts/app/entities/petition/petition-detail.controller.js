'use strict';

angular.module('jhipsterApp')
    .controller('PetitionDetailController', function ($scope, $rootScope, $stateParams, entity, Petition, User, Subscriptions) {
        $scope.petition = entity;
        $scope.load = function (id) {
            Petition.get({id: id}, function(result) {
                $scope.petition = result;
            });
        };
        var unsubscribe = $rootScope.$on('jhipsterApp:petitionUpdate', function(event, result) {
            $scope.petition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
