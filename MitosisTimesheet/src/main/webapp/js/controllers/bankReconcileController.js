'use strict';

angular.module('myApp.controllers')


.controller('bankReconcileController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

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
		if(sheet.customerName == '' || sheet.customerName == undefined){
			return true;
		} else if(sheet.project.projectName == '' || sheet.project.projectName == undefined) {
			return true;
		} else if(sheet.invoiceNumber == '' || sheet.invoiceNumber == undefined) {
			return true;
		} else if(sheet.invoiceDate == '' || sheet.invoiceDate == undefined) {
			return true;
		}else if(sheet.invoiceAmount == '' || sheet.invoiceAmount == undefined) {
			return true;
		}else if(sheet.receiptNumber == '' || sheet.receiptNumber == undefined) {
			return true;
		}else if(sheet.receivedDate == '' || sheet.receivedDate == undefined) {
			return true;
		}else if(sheet.receivedAmount == '' || sheet.receivedAmount == undefined) {
			return true;
		}else if(sheet.exchangeRate == '' || sheet.exchangeRate == undefined) {
			return true;
		}else{
			return false;
		}
	}
	
	$http({
		url: 'rest/payment/showCustomerlist',
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {			
		console.log(result);		
		$scope.customerList=result;

	});


	$scope.customerChanged = function() {
		var cusid = angular.toJson({
			"customerId": $scope.customers.customerId
		});
		$http({
			url: 'rest/payment/projectList',
			method: 'POST',
			data:cusid,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {			
			console.log(result);		
			$scope.projectList=result;

		});

	}
	
	$scope.projectChanged = function() {
		var pid = angular.toJson({
			"projectId": $scope.projects.projectId
		});
		$http({
			url: 'rest/payment/invoiceList',
			method: 'POST',
			data:pid,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {			
			console.log(result);		
			$scope.invoiceList=result;

		});

	}
	
	$scope.invoiceChanged = function() {
		
		var ino = angular.toJson({
			"invoiceNumber": $scope.invoices.invoiceNumber
		});
		$http({
			url: 'rest/bankReconcile/getPaymentDetails',
			method: 'POST',
			data:ino,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			$scope.paymentDetails = result;
			console.log(result);
			
		});

	}
	
	
	$scope.getReceiptDetails = function(){
		
		
		
		
		var menuJson = angular.toJson({
			"receiptNumber": $scope.receipt.receiptNumber
		});
		$http({
			url: 'rest/bankReconcile/getReceiptDetails',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			$scope.receiptDetails = result;
			console.log(result);
			
		});
		
		
		
		
		
	}
	
	
	$scope.insertPaymentInfo = function(){
		
		
		var menuJson = angular.toJson({
			"receiptNumber": $scope.receipt.receiptNumber,"recieveddate":$scope.recievedDate,"bankcommission":$scope.bankcommision,"exchangerate":$scope.exchangerate,"amount":amount
		});
		
		
		$http({
			url: 'rest/bankReconcile/insertReconcile',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			
			
		});
		
		
		
		
		
		
		
		
		
	}

}])