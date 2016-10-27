(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .controller('EntryDetailController', EntryDetailController);

    EntryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Entry', 'Goal', 'Metric'];

    function EntryDetailController($scope, $rootScope, $stateParams, previousState, entity, Entry, Goal, Metric) {
        var vm = this;

        vm.entry = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('rhinobuyApp:entryUpdate', function(event, result) {
            vm.entry = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
