'use strict';

angular.module('jhipsterApp')
	.controller('PetitionDeleteController', function($scope, $modalInstance, entity, Petition) {

        $scope.petition = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Petition.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });