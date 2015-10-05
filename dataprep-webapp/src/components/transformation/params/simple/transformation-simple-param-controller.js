(function () {
    'use strict';

    /**
     * @ngdoc controller
     * @name data-prep.transformation-params.controller:TransformSimpleParamCtrl
     * @description Simple transformation parameter controller.
     * @requires data-prep.services.utils.service:ConverterService
     */
    function TransformSimpleParamCtrl(ConverterService) {

        var vm = this;

        /**
         * @ngdoc method
         * @name initParamValues
         * @methodOf data-prep.transformation-params.controller:TransformSimpleParamCtrl
         * @description [PRIVATE] Init simple param values to default
         */
        var initParamValues = function () {
            if(vm.parameter.initialValue) {
                vm.parameter.initialValue = ConverterService.adaptValue(vm.parameter.type, vm.parameter.initialValue);
            }

            if (vm.parameter.value) {
                vm.parameter.value = ConverterService.adaptValue(vm.parameter.type, vm.parameter.value);
            }
            else if (vm.parameter.default) {
                vm.parameter.default = ConverterService.adaptValue(vm.parameter.type, vm.parameter.default);
                vm.parameter.value = vm.parameter.default;
            }
        };

        /**
         * @ngdoc method
         * @name initInputTypes
         * @methodOf data-prep.transformation-params.controller:TransformSimpleParamsCtrl
         * @description [PRIVATE] Init params input type, depending on param type
         */
        var initInputTypes = function() {
            vm.parameter.inputType = ConverterService.toInputType(vm.parameter.type);
        };

        initParamValues();
        initInputTypes();
    }

    angular.module('data-prep.transformation-params')
        .controller('TransformSimpleParamCtrl', TransformSimpleParamCtrl);
})();