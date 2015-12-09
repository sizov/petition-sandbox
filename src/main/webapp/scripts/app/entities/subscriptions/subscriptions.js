'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('subscriptions', {
                parent: 'entity',
                url: '/subscriptionss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Subscriptionss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subscriptions/subscriptionss.html',
                        controller: 'SubscriptionsController'
                    }
                },
                resolve: {
                }
            })
            .state('subscriptions.detail', {
                parent: 'entity',
                url: '/subscriptions/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Subscriptions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subscriptions/subscriptions-detail.html',
                        controller: 'SubscriptionsDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Subscriptions', function($stateParams, Subscriptions) {
                        return Subscriptions.get({id : $stateParams.id});
                    }]
                }
            })
            .state('subscriptions.new', {
                parent: 'subscriptions',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/subscriptions/subscriptions-dialog.html',
                        controller: 'SubscriptionsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    subscribtionDateTime: null,
                                    subscriber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('subscriptions', null, { reload: true });
                    }, function() {
                        $state.go('subscriptions');
                    })
                }]
            })
            .state('subscriptions.edit', {
                parent: 'subscriptions',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/subscriptions/subscriptions-dialog.html',
                        controller: 'SubscriptionsDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Subscriptions', function(Subscriptions) {
                                return Subscriptions.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subscriptions', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('subscriptions.delete', {
                parent: 'subscriptions',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/subscriptions/subscriptions-delete-dialog.html',
                        controller: 'SubscriptionsDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Subscriptions', function(Subscriptions) {
                                return Subscriptions.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subscriptions', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
