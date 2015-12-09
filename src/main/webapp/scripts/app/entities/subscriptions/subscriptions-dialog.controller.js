'use strict';

angular.module('jhipsterApp').controller('SubscriptionsDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Subscriptions', 'User', 'Petition',
        function($scope, $stateParams, $modalInstance, entity, Subscriptions, User, Petition) {

        $scope.subscriptions = entity;
        $scope.users = User.query();
        $scope.petitions = Petition.query();
        $scope.load = function(id) {
            Subscriptions.get({id : id}, function(result) {
                $scope.subscriptions = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jhipsterApp:subscriptionsUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.subscriptions.id != null) {
                Subscriptions.update($scope.subscriptions, onSaveSuccess, onSaveError);
            } else {
                Subscriptions.save($scope.subscriptions, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
