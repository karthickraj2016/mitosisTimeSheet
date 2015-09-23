'use strict';

angular.module('myApp.controllers')


.controller('lobMasterController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	
	
	
	$scope.list = function (){

	$http({
		url: 'rest/lobMaster/getLobList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		$scope.lobList=result;

	});
	
	},
	
	
	$scope.insert = function (){
		
		
		var menuJson = angular.toJson({"lobName":$scope.lob.lobName,"skills":$scope.skills});
		
		$http({
			url: 'rest/lobMaster/insertEntry',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			

		});
		
		
		
		
	}






}])