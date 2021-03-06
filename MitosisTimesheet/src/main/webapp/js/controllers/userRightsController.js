'use strict';

angular.module('myApp.controllers')

.controller('userRightsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	$scope.units;
	var hoursallowed;
	
	$rootScope.navbar=true;

	$scope.checkRequired = function(sheet){
		if(sheet.name == '' || sheet.name == undefined){
			return true;
		} else if(sheet.eMail == '' || sheet.eMail == undefined) {
			return true;
		} else if(sheet.active == '' || sheet.active == undefined) {
			return true;
		} else if(sheet.manageFinance == '' || sheet.manageFinance == undefined) {
			return true;
		} else if(sheet.manageProject == '' || sheet.manageProject == undefined) {
			return true;
		} else if(sheet.manageTeam == '' || sheet.manageTeam == undefined) {
			return true;
		} else if(sheet.manageCustomer == '' || sheet.manageCustomer == undefined) {
			return true;
		}else if(sheet.manageEmployees == '' || sheet.manageEmployees == undefined) {
			return true;
		}else{
			return false;
		}
	}

	$scope.list = function() {

		$http({
			url: 'rest/userRights/showUserList',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.lists=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.lists.slice(begin, end);
				$scope.totalItems =	$scope.lists.length;

			});

		})
	},

	$scope.update = function(reqParam){

		$http({
			url: 'rest/userRights/updateRights',
			method: 'POST',
			data: reqParam,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			if(result){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Updated Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Updating Failed");
			}
			$scope.list();
		})
	}
	
}])