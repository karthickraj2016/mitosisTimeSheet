'use strict';

angular.module('myApp.controllers')

.controller('teamreportController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	    
   
	
	$scope.dates = function() {
	     $scope.timesheet = '';
		 $scope.timesheet={};
		    var dt = new Date();
		    var dd = dt.getDate();
		    var mm = dt.getMonth()+1; 
		    var yyyy = dt.getFullYear();
		    dt=dd+"-"+mm+"-"+yyyy;
		    var dt1= new Date();
		    var dd1 = dt1.getDate();
		    var mm1 = dt1.getMonth()+1;
		    var yyyy1 = dt1.getFullYear();
		    dt1=dd1+"-"+mm1+"-"+yyyy1;
		 $scope.timesheet.fromdate=dt;	
		 $scope.timesheet.todate=dt1;
	};

	
	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			maxDate:'0d',
			minDate:-30,
			dateFormat:'dd-mm-yy'

			/* yearRange: '1900:-0'*/
	};
	
	
	$http({
		url: 'rest/teamreport/getprojectlist',
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {
		$scope.projectlist = result;
		
	});
	
	
	
	
	$scope.detailreport = function(){
	
		var menuJson = angular.toJson({"fromdate":$scope.timesheet.fromdate,"todate":$scope.timesheet.todate,"name":$rootScope.name,"projectId":$scope.project.projectId});
		
		
		$http({
			url: 'rest/teamreport/detailreport',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			var a = document.createElement('a');
			 a.href = "/jasper-report/reports/"+result.pdfFileName;
			console.log(a);
				 a.download = "individualDetailReport.pdf";
			 document.body.appendChild(a);
		        a.click();
		        document.body.removeChild(a);
		    $scope.filepath = a.href;
		        console.log($scope.filepath);
		        $scope.deletepdfFile(result.pdfPath);
			
		});
		
		
		
		
		
	},
	
	
	$scope.summaryreport = function(){
		
		var menuJson = angular.toJson({"fromdate":$scope.timesheet.fromdate,"todate":$scope.timesheet.todate,"name":$rootScope.name});
		
		$http({
			url: 'rest/teamreport/summaryreport',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			var a = document.createElement('a');
			 a.href = "/jasper-report/reports/"+result.pdfFileName;
			console.log(a);
				 a.download = "individualSummaryReport.pdf";
			 document.body.appendChild(a);
		        a.click();
		        document.body.removeChild(a);
		    $scope.filepath = a.href;
		        console.log($scope.filepath);
		        $scope.deletepdfFile(result.pdfPath);
			
		});
		
	}
	
	$scope.deletepdfFile= function(pdfPath){
		
		var menuJson = angular.toJson({"filepath":pdfPath});
		
		$http({
			url: 'rest/teamreport/deletepdffile',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
		
			
			
			
		});
		
		
	}
	
	function HeaderController($scope, $location) 
	{ 
	    $scope.isActive = function (viewLocation) { 
	        return viewLocation === $location.path();
	    };
	}


}])

