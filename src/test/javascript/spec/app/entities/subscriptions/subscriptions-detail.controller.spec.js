'use strict';

describe('Subscriptions Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSubscriptions, MockUser, MockPetition;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSubscriptions = jasmine.createSpy('MockSubscriptions');
        MockUser = jasmine.createSpy('MockUser');
        MockPetition = jasmine.createSpy('MockPetition');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Subscriptions': MockSubscriptions,
            'User': MockUser,
            'Petition': MockPetition
        };
        createController = function() {
            $injector.get('$controller')("SubscriptionsDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'jhipsterApp:subscriptionsUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
