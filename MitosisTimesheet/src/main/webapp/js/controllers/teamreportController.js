'use strict';

angular.module('myApp.controllers')

.controller('teamreportController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.dates = function() {
		$scope.timesheet = '';
		$scope.timesheet={};
		var dt = new Date();
		var mm = ("0"+ (dt.getMonth())).slice(-2); 
		var mm1 = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();

		var date1=new Date(yyyy,mm,1);
		var d=("0"+ date1.getDate()).slice(-2);
		var m=("0"+ (date1.getMonth()+1)).slice(-2);
		var y= date1.getFullYear();
		date1=d+"-"+m+"-"+y;
		$scope.timesheet.fromdate=date1;

		var date2=new Date(yyyy,mm1,0);
		var d1=("0"+date2.getDate()).slice(-2);
		var m1=("0"+ (date2.getMonth()+1)).slice(-2);
		var y1= date2.getFullYear();
		date2=d1+"-"+m1+"-"+y1;
		$scope.timesheet.todate =date2;
	};

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			/*maxDate:'0d',*/
			/*minDate:-30,*/
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


	$scope.checkUserRightsByProjectForDetails = function(){

		var menuJson=angular.toJson({"projectId":$scope.project.project.projectId})

		$http({
			url: 'rest/teamreport/checkUserRights',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.level>=2){
				$scope.detailreport();
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("You have No Rights to View this Report");
			}

		});

	}


	$scope.detailreport = function(){

		var projectName=$scope.project.project.projectName;

		if(projectName=="All Projects"){

			var menuJson = angular.toJson({"fromdate":$scope.timesheet.fromdate,"todate":$scope.timesheet.todate,"name":$rootScope.name,"projectId":$scope.project.project.projectId});


			$http({
				url: 'rest/teamreport/getAllProjectsDetails',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.pdfPath=="norecords"){

					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("No records availiable");
					return;

				}
				else{	
					var a = document.createElement('a');
					a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
					console.log(a);
					//a.download = "teamDetailReport.pdf";
					a.target="_blank"; 
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					$scope.filepath = a.href;
					console.log($scope.filepath);
					//$scope.deletepdfFile(result.pdfPath);
				}

			});

		}else{

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

					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("No records availiable");
					return;

				}
				else{	
					var a = document.createElement('a');
					a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
					console.log(a);
					//a.download = "teamDetailReport.pdf";
					a.target="_blank"; 
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					$scope.filepath = a.href;
					console.log($scope.filepath);
					//$scope.deletepdfFile(result.pdfPath);
				}

			});
		}
	},



	$scope.checkUserRightsByProjectForSummary = function(){

		var menuJson=angular.toJson({"projectId":$scope.project.project.projectId})

		$http({
			url: 'rest/teamreport/checkUserRights',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.level>=2){
				$scope.summaryreport();
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("You have No Rights to View this Report");
			}

		});

	}

	$scope.summaryreport = function(){

		var projectName=$scope.project.project.projectName;

		if(projectName=="All Projects"){

			var menuJson = angular.toJson({"fromdate":$scope.timesheet.fromdate,"todate":$scope.timesheet.todate,"name":$rootScope.name,"projectId":$scope.project.project.projectId});

			$http({
				url: 'rest/teamreport/getAllProjectsSummary',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.pdfPath=="norecords"){
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("No records availiable");
					return;

				}

				else{

					var a = document.createElement('a');
					a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
					console.log(a);
					//a.download = "teamSummaryReport.pdf";
					a.target="_blank";
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					$scope.filepath = a.href;
					console.log($scope.filepath);
					//$scope.deletepdfFile(result.pdfPath);
				}
			});


		}else{

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
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("No records availiable");
					return;

				}

				else{

					var a = document.createElement('a');
					a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
					console.log(a);
					//a.download = "teamSummaryReport.pdf";
					a.target="_blank";
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					$scope.filepath = a.href;
					console.log($scope.filepath);
					//$scope.deletepdfFile(result.pdfPath);
				}
			});
		}
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

