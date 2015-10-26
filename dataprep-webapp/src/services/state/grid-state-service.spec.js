describe('Grid state service', function () {
    'use strict';

    var data = {
        records: [
            {tdpId: 0, firstname: 'Tata'},
            {tdpId: 1, firstname: 'Tetggggge'},
            {tdpId: 2, firstname: 'Titi'},
            {tdpId: 3, firstname: 'Toto'},
            {tdpId: 4, name: 'AMC Gremlin'},
            {tdpId: 5, firstname: 'Tyty'},
            {tdpId: 6, firstname: 'Papa'},
            {tdpId: 7, firstname: 'Pepe'},
            {tdpId: 8, firstname: 'Pipi'},
            {tdpId: 9, firstname: 'Popo'},
            {tdpId: 10, firstname: 'Pupu'},
            {tdpId: 11, firstname: 'Pypy'}
        ]
    };

    beforeEach(module('data-prep.services.state'));

    beforeEach(inject(function(gridState) {
        gridState.dataView = new DataViewMock();
    }));

    describe('filter', function() {
        it('should set filters to DataView', inject(function (gridState, GridStateService) {
            //given
            gridState.dataView.setItems(data.records, 'tdpId');
            var filterFnCol1 = function() {
                return function (item) {
                    return item.col1.indexOf('toto') > -1;
                };
            };
            var filterFnCol2 = function() {
                return function (item) {
                    return item.col2.indexOf('toto') > -1;
                };
            };
            var filters = [
                {filterFn: filterFnCol1},
                {filterFn: filterFnCol2}
            ];

            //when
            GridStateService.setFilter(filters, data);

            //then
            expect(gridState.dataView.filter({
                col1: 'mon toto', col2: 'toto tata titi'
            })).toBe(true);
            expect(gridState.dataView.filter({
                col1: 'mon tutu', col2: 'toto tata titi'
            })).toBe(false);
            expect(gridState.dataView.filter({
                col1: 'mon toto', col2: 'tutu tata titi'
            })).toBe(false);
        }));

        it('should grid line stats', inject(function (gridState, GridStateService) {
            //given
            gridState.dataView.setItems(data.records.slice(0, 2), 'tdpId');
            var filterFnCol1 = function() {
                return function (item) {
                    return item.col1.indexOf('toto') > -1;
                };
            };
            var filterFnCol2 = function() {
                return function (item) {
                    return item.col2.indexOf('toto') > -1;
                };
            };
            var filters = [
                {filterFn: filterFnCol1},
                {filterFn: filterFnCol2}
            ];

            //when
            GridStateService.setFilter(filters, data);

            //then
            expect(gridState.nbLines).toBe(2);
            expect(gridState.nbTotalLines).toBe(12);
            expect(gridState.displayLinesPercentage).toBe('17');
        }));
    });

    describe('data', function() {
        it('should set data to DataView', inject(function (gridState, GridStateService) {
            //given
            expect(gridState.dataView.getItems()).not.toBe(data.records);

            //when
            GridStateService.setData(data);

            //then
            expect(gridState.dataView.getItems()).toBe(data.records);
        }));

        it('should grid line stats', inject(function (gridState, GridStateService) {
            //given
            gridState.nbLines = null;
            gridState.nbTotalLines = null;
            gridState.displayLinesPercentage = null;

            //when
            GridStateService.setData(data);

            //then
            expect(gridState.nbLines).toBe(12);
            expect(gridState.nbTotalLines).toBe(12);
            expect(gridState.displayLinesPercentage).toBe('100');
        }));
    });

    describe('grid event result state', function() {
        it('should set focused columns', inject(function (gridState, GridStateService) {
            //given
            expect(gridState.columnFocus).toBeFalsy();

            //when
            GridStateService.setColumnFocus('0001');

            //then
            expect(gridState.columnFocus).toBe('0001');
        }));

        it('should set grid selection', inject(function (gridState, GridStateService) {
            //given
            gridState.selectedColumn = null;
            gridState.selectedLine = null;

            //when
            GridStateService.setGridSelection('0001', 18);

            //then
            expect(gridState.selectedColumn).toBe('0001');
            expect(gridState.selectedLine).toBe(18);
        }));
    });

    describe('reset', function() {
        it('should reset event result state', inject(function(gridState, GridStateService) {
            //given
            gridState.columnFocus = '0001';
            gridState.selectedColumn = '0001';
            gridState.selectedLine = 2;

            //when
            GridStateService.reset();

            //then
            expect(gridState.columnFocus).toBe(null);
            expect(gridState.selectedColumn).toBe(null);
            expect(gridState.selectedLine).toBe(null);
        }));
    });
});