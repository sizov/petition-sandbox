'use strict';

angular.module('jhipsterApp').controller('PetitionDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Petition', 'User', 'Subscriptions',
        function($scope, $stateParams, $modalInstance, entity, Petition, User, Subscriptions) {

        $scope.petition = entity;
        $scope.users = User.query();
        $scope.subscriptionss = Subscriptions.query();
        $scope.load = function(id) {
            Petition.get({id : id}, function(result) {
                $scope.petition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:petitionUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.petition.id != null) {
                Petition.update($scope.petition, onSaveSuccess, onSaveError);
            } else {
                Petition.save($scope.petition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
