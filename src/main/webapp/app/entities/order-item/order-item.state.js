(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-item', {
            parent: 'entity',
            url: '/order-item',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.orderItem.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-item/order-items.html',
                    controller: 'OrderItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderItem');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-item-detail', {
            parent: 'entity',
            url: '/order-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.orderItem.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-item/order-item-detail.html',
                    controller: 'OrderItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderItem');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderItem', function($stateParams, OrderItem) {
                    return OrderItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-item-detail.edit', {
            parent: 'order-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item/order-item-dialog.html',
                    controller: 'OrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderItem', function(OrderItem) {
                            return OrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-item.new', {
            parent: 'order-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item/order-item-dialog.html',
                    controller: 'OrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                price: null,
                                details: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-item', null, { reload: 'order-item' });
                }, function() {
                    $state.go('order-item');
                });
            }]
        })
        .state('order-item.edit', {
            parent: 'order-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item/order-item-dialog.html',
                    controller: 'OrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderItem', function(OrderItem) {
                            return OrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-item', null, { reload: 'order-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-item.delete', {
            parent: 'order-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item/order-item-delete-dialog.html',
                    controller: 'OrderItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderItem', function(OrderItem) {
                            return OrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-item', null, { reload: 'order-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
