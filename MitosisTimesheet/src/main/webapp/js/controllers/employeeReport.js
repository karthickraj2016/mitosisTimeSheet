'use strict';

angular.module('myApp.controllers')


.controller('employeeReportController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {


	$scope.employeeDetailsReport = function(){
		
		$http({
			url: 'rest/employeeMaster/employeeReport',
			method: 'GET',
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

	
	
	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	};
	 
}])

