(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('OrderItemDialogController', OrderItemDialogController);

    OrderItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderItem', 'Orders', 'OrderItemStatusCode'];

    function OrderItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderItem, Orders, OrderItemStatusCode) {
        var vm = this;

        vm.orderItem = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orders = Orders.query();
        vm.orderitemstatuscodes = OrderItemStatusCode.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderItem.id !== null) {
                OrderItem.update(vm.orderItem, onSaveSuccess, onSaveError);
            } else {
                OrderItem.save(vm.orderItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('rhinobuyApp:orderItemUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
