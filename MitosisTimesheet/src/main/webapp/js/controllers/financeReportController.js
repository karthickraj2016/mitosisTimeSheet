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
			a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
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


}])