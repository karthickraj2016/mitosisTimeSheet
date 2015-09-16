'use strict';

angular.module('myApp.controllers')

.controller('financeReportController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {
	
	$scope.dates = function() {
		$scope.finance = '';
		$scope.finance={};
		var dt = new Date();
		var mm = ("0"+ (dt.getMonth())).slice(-2); 
		var mm1 = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();

		var date1=new Date(yyyy,mm,1);
		var d=("0"+ date1.getDate()).slice(-2);
		var m=("0"+ (date1.getMonth()+1)).slice(-2);
		var y= date1.getFullYear();
		date1=d+"-"+m+"-"+y;
		$scope.finance.fromdate=date1;

		var date2=new Date(yyyy,mm1,0);
		var d1=("0"+date2.getDate()).slice(-2);
		var m1=("0"+ (date2.getMonth()+1)).slice(-2);
		var y1= date2.getFullYear();
		date2=d1+"-"+m1+"-"+y1;
		$scope.finance.todate =date2;
	};

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			/*maxDate:'0d',*/
			/*minDate:-30,*/
			dateFormat:'dd-mm-yy'
	};
	
	$scope.bankReconcileReport = function(){
		
	var menuJson=angular.toJson({"fromDate":$scope.finance.fromdate,"toDate":$scope.finance.todate});
		
		$http({
			url: 'rest/financeReport/bankReconcileReport',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			var a = document.createElement('a');
			a.href = "/"+projectName+"/reports/"+result.pdfFileName;
			console.log(a);
			//a.download = "individualDetailReport.pdf";
			a.target="_blank";
			document.body.appendChild(a);
			a.click();
			document.body.removeChild(a);
			$scope.filepath = a.href;
			console.log($scope.filepath);
			return;
		})
	}

	$scope.pendingInvoice = function(){
		
	/*	var date = new Date();
		console.log(date);
	
		var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
		var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
		
		console.log(firstDay.getDate(),firstDay.getMonth()+1, firstDay.getFullYear());
		
		var firstday1 =firstDay.getDate()+'-'+parseInt(firstDay.getMonth()+1)+'-'+firstDay.getFullYear();
		var lastday1 = lastDay.getDate()+'-'+parseInt(lastDay.getMonth()+1)+'-'+lastDay.getFullYear();
		var menuJson = angular.toJson({
			"firstday": firstday1,"lastday":lastday1

		});*/
		
		var menuJson=angular.toJson({"firstday":$scope.finance.fromdate,"lastday":$scope.finance.todate});
	
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
				
			}else{
			
			var a = document.createElement('a');
			a.href = "/"+projectName+"/reports/"+result.pdfFileName;
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
		}else{
		
			var a = document.createElement('a');
			a.href = "/"+projectName+"/reports/"+result.pdfFileName;
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

}])