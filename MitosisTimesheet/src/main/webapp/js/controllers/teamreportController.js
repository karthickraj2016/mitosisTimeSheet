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
	
		var menuJson = angular.toJson({"fromdate":$scope.timesheet.fromdate,"todate":$scope.timesheet.todate,"name":$rootScope.name,"projectId":$scope.project.project.projectId});
		
		
		$http({
			url: 'rest/teamreport/detailReport',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
		if(result.pdfPath=="norecords"){
			
		alert("No records availiable");	
		return;
			
		}
		else{	
			var a = document.createElement('a');
			 a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
			console.log(a);
				// a.download = "teamDetailReport.pdf";
			a.target="_blank"; 
			document.body.appendChild(a);
		        a.click();
		        document.body.removeChild(a);
		    $scope.filepath = a.href;
		        console.log($scope.filepath);
		        $scope.deletepdfFile(result.pdfPath);
		}
			
		});
		
		
		
		
	},
	
	
	$scope.summaryreport = function(){
		
		console.log($scope.project);
		
		var menuJson = angular.toJson({"fromdate":$scope.timesheet.fromdate,"todate":$scope.timesheet.todate,"name":$rootScope.name,"projectId":$scope.project.project.projectId});
		
		$http({
			url: 'rest/teamreport/summaryReport',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result.pdfPath=="norecords"){
				
				alert("No records availiable");	
				return;
					
				}
			
			else{
			
			var a = document.createElement('a');
			 a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
			console.log(a);
				// a.download = "teamSummaryReport.pdf";
			a.target="_blank";
			 document.body.appendChild(a);
		        a.click();
		        document.body.removeChild(a);
		    $scope.filepath = a.href;
		        console.log($scope.filepath);
		        $scope.deletepdfFile(result.pdfPath);
			}
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

	$scope.logout = function(){

		$http({
			url: 'rest/teamreport/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	}

}])

