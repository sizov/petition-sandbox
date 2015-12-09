'use strict';

angular.module('jhipsterApp')
    .controller('PetitionController', function ($scope, $state, $modal, Petition) {
      
        $scope.petitions = [];
        $scope.loadAll = function() {
            Petition.query(function(result) {
               $scope.petitions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.petition = {
                title: null,
                body: null,
                creationTime: null,
                creater: null,
                id: null
            };
        };
    });
