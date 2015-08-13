'use strict';

angular.module('myApp.controllers')


.controller('includeController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {


	$rootScope.navbar=true;
	$rootScope.accountList = function(){

		$http({
			url: 'rest/accountdetails/getaccountdetails',
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			console.log(result);
			if(result.name==""){
				$state.go('login')
			}else{
				$rootScope.name=result.name;
				$rootScope.manageFinance=result.manageFinance;
				$rootScope.manageProject=result.manageProject;
				$rootScope.manageTeam=result.manageTeam;
				$rootScope.manageCustomer=result.manageCustomer;
				$rootScope.manageEmployees=result.manageEmployees;
				$rootScope.mail=result.eMail;
			}

		});
	}

	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	};
}])