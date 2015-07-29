'use strict';

angular.module('myApp.controllers')


.controller('levelMasterController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	
	$http({
		url: 'rest/account/getName',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {
		if(result==""){
			$state.go('login')

		}else{
			$scope.name=result;
		}
	});
	
	$http({
		url: 'rest/timesheet/getUserDetails',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		$scope.manageFinance=result.manageFinance;
		$scope.manageProject=result.manageProject;
		$scope.manageTeam=result.manageTeam;
		$scope.manageCustomer=result.manageCustomer;
		$scope.manageEmployees=result.manageEmployees;
	
	});
	
	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	
	$scope.check = function(sheet){
		if(sheet.yearFrom == '' || sheet.yearFrom == undefined){
			return true;
		} else if(sheet.yearTo == '' || sheet.yearTo == undefined) {
			return true;
		}else if(sheet.level == '' || sheet.level == undefined) {
			return true;
		}else if(sheet.ratePerHour == '' || sheet.ratePerHour == undefined) {
			return true;
		}else if(sheet.totalHours == '' || sheet.totalHours == undefined) {
			return true;
		}else if(sheet.numberOfEmployees == '' || sheet.numberOfEmployees == undefined) {
			return true;
		}else if(sheet.totalAmount == '' || sheet.totalAmount == undefined) {
			return true;
		}else{
			return false;
		}
	},
	
	$scope.list = function() {

		$http({
			url: 'rest/levelMaster/showlevelDetails',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.levelEntryList=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.levelEntryList.slice(begin, end);
				$scope.totalItems =	$scope.levelEntryList.length;

			});

		})
	},
	
	$scope.addLevelMasterDetails = function(){
		
		var menuJson=angular.toJson({"yearFrom":$scope.levelDetail.yearFrom,"yearTo":$scope.levelDetail.yearTo,
			"level":$scope.levelDetail.level,"ratePerHour":$scope.levelDetail.ratePerHour,"hoursPerMonth":$scope.levelDetail.hoursPerMonth});

		$http({
			url: 'rest/levelMaster/addLevelDetails',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Level Details Added Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Process Failed");
			}
			$scope.list();	
			$scope.levelDetail="";
		})
	},
	
   $scope.updateLevelMasterDetails = function(reqParam){
		
		$http({
			url: 'rest/levelMaster/updateLevelDetails',
			method: 'POST',
			data: reqParam,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Level Details Updated Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Process Failed");
			}
			$scope.list();	
		})
	},
	
	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteLevelDetails(id);
		}
	},

	$scope.deleteLevelDetails = function(id){

		$http({
			url: 'rest/levelMaster/deleteLevelDetailEntry',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Level Detail Deleted Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Level Detail Delete Failed");
			}
			$scope.list();	
		})
	},
	
	$scope.calculateAndUpdateEstimation = function(){
		
		var levelList=$scope.levelEntryList;
		
		for(var i=0;i<=levelList.length;i++){
			
			var ratePerHour=levelList[i].ratePerHour;
			var hoursPerMonth=levelList[i].hoursPerMonth;
			var amount=ratePerHour*hoursPerMonth;
		    			
			var menuJson=angular.toJson({"id":levelList[i].id,"yearFrom":levelList[i].yearFrom,"yearTo":levelList[i].yearTo,
				"level":levelList[i].level,"ratePerHour":levelList[i].ratePerHour,"hoursPerMonth":levelList[i].hoursPerMonth,"amount":amount,
				"inrRate":$scope.inrRate});
		
				
			$http({
				url: 'rest/levelMaster/calculateAndUpdateEstimation',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="success"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Projection Updated Successfully");
					$scope.inrRate="";
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
				}
				$scope.list();	
			})
		}
	},
	
	$scope.getEstimatedRevenue = function(){
	   
		var estimatedRevenue = 0;
	  
		for(var i = 0; i < $scope.filteredParticipantsResults.length; i++){
	        var revenue =  $scope.filteredParticipantsResults[i].totalAmount;
	        estimatedRevenue += revenue;
	    }
	    return estimatedRevenue;
	},
	
	$scope.getCountOfEmp = function(){
		   
		var numOfEmp = 0;
	  
		for(var i = 0; i < $scope.filteredParticipantsResults.length; i++){
	        var emp =  $scope.filteredParticipantsResults[i].numberOfEmployees;
	        numOfEmp += emp;
	    }
	    return numOfEmp;
	},
	
	$scope.getEstimatedRevenueINR = function(){
		   
		var estimatedRevenueINR = 0;
	  
		for(var i = 0; i < $scope.filteredParticipantsResults.length; i++){
	        var revenue =  $scope.filteredParticipantsResults[i].totalAmountINR;
	        estimatedRevenueINR += revenue;
	    }
	    return estimatedRevenueINR;
	},
	
	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	};
	
}])