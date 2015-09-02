'use strict';

angular.module('myApp.controllers')


.controller('invoiceViewController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {



	$scope.list	= function (){



		$http({
			url: 'rest/invoiceDetails/getCustomerList',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			console.log(result);

			$scope.customerList=result;
		});


	}


	$scope.loadProjects=function()
	{
		var cusid = angular.toJson({
			"customerId": $scope.invoice.customer.customerId
		});

		$scope.invoice.invoiceno="";
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


	$scope.projectBasedSelections = function(project){

		var menuJson = angular.toJson({"project":project});

		$scope.invoice.invoiceno="";
		$scope.invoiceList=[];
		$scope.invoice.projectType="";
		$scope.invoice.currency="";
		$scope.invoice.invoiceamt="";
		$scope.invoice.teammembers=[];

		$http({

			url: 'rest/invoiceDetails/getProjectTypeList',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {


			$scope.projectTypeList=result;
			console.log($scope.projectTypeList);
			$scope.invoice.projectType =$scope.projectTypeList[0].projectType;
			$scope.invoice.currency=$scope.projectTypeList[0].currencyCode;

		});


		$http({

			url: 'rest/invoiceDetails/getInvoiceList',
			method: 'POST',
			data: project.projectId,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.invoiceList = result;


		});

	}
	
	
	$scope.invoiceSelection = function(invoiceno){
		
		
		$http({

			url: 'rest/invoiceDetails/getInvoiceDetails',
			method: 'POST',
			data: invoiceno,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			$scope.invoiceList =[];
			
			for(i=0;i<result.length;i++){
				
				
				$scope.invoiceList.push(result[i]);		
				
			}
			
			
			console.log($scope.invoiceList);
			

		});
		
		
		
		
		
		
	}






}])