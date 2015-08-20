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
	
	
	$scope.pendingInvoice = function(){
		
		var date = new Date();
		
		console.log(date);
		
	/*	
		var dd2 = firstDay.getDate();
		var mm2 = dt2.getMonth()+1;
		var yyyy2 = dt2.getFullYear();*/
		
		
		var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
		var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
		
		console.log(firstDay.getDate(),firstDay.getMonth()+1, firstDay.getFullYear());
		
		var firstday1 =firstDay.getDate()+'-'+parseInt(firstDay.getMonth()+1)+'-'+firstDay.getFullYear();
		var lastday1 = lastDay.getDate()+'-'+parseInt(lastDay.getMonth()+1)+'-'+lastDay.getFullYear();
		



		var menuJson = angular.toJson({
			"firstday": firstday1,"lastday":lastday1

		});
		

		$http({
			url: 'rest/invoiceReports/pendingInvoiceReports',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			
			if(result.report=="norecords"){
				
				
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("All Invoices are submitted in this month!!!!");
				return;
				
			}
			
			else{
			
			var a = document.createElement('a');
			a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
			console.log(a);
			//a.download = "individualDetailReport.pdf";
			a.target="_blank";
			document.body.appendChild(a);
			a.click();
			document.body.removeChild(a);
			$scope.filepath = a.href;
			console.log($scope.filepath);

			}

		});
		
		
		
		
	}
	
	
	
	$scope.pendingBalance = function(){
		
		
		$http({
			url: 'rest/invoiceReports/pendingBalanceReports',
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result.report=="norecords"){
				
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("No balance is pending from any customer!!!");
				return;
				
				
				
			}
			
			else{
			
			var a = document.createElement('a');
			a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
			console.log(a);
			//a.download = "individualDetailReport.pdf";
			a.target="_blank";
			document.body.appendChild(a);
			a.click();
			document.body.removeChild(a);
			$scope.filepath = a.href;
			console.log($scope.filepath);
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