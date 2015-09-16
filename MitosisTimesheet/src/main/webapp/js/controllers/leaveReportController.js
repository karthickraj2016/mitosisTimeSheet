'use strict';

angular.module('myApp.controllers')


.controller('leaveReportController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	$scope.units;
	
	$rootScope.navbar=true;

	$scope.checkRequired = function(sheet){
		if(sheet.frmEntryDate == '' || sheet.frmEntryDate == undefined) {
			return true;
		} else if(sheet.toEntryDate == '' || sheet.toEntryDate == undefined) {
			return true;
		} else if(sheet.reason == '' || sheet.reason == undefined) {
			return true;
		}else if(sheet.status == '' || sheet.status == undefined) {
			return true;
		}else{
			return false;
		}

	}
	
	$scope.dates = function() {
		$scope.leave = '';
		$scope.leave={};
		var dt = new Date();
		var mm = ("0"+ (dt.getMonth())).slice(-2); 
		var mm1 = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();

		var date1=new Date(yyyy,mm,1);
		var d=("0"+ date1.getDate()).slice(-2);
		var m=("0"+ (date1.getMonth()+1)).slice(-2);
		var y= date1.getFullYear();
		date1=d+"-"+m+"-"+y;
		$scope.leave.fromdate=date1;

		var date2=new Date(yyyy,mm1,0);
		var d1=("0"+date2.getDate()).slice(-2);
		var m1=("0"+ (date2.getMonth()+1)).slice(-2);
		var y1= date2.getFullYear();
		date2=d1+"-"+m1+"-"+y1;
		$scope.leave.todate =date2;
	};

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
			onSelect: function(selected) {
				/*$scope.leave.todate=selected;*/
			}
	};

	$scope.leavereport = function(){

		var menuJson = angular.toJson({"fromdate":$scope.leave.fromdate,"todate":$scope.leave.todate,"name":$rootScope.name});

		var fromdate = $scope.leave.fromdate;
		var todate = $scope.leave.todate;

		var datevalidationfromDate =new Date(fromdate.split("-")[1]+"-"+fromdate.split("-")[0]+"-"+fromdate.split("-")[2]);	
		var datevalidationtoDate = new Date(todate.split("-")[1]+"-"+todate.split("-")[0]+"-"+todate.split("-")[2]);

		if (datevalidationfromDate > datevalidationtoDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			return;
		}


		$http({
			url: 'rest/leavereport/detailreport',
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
				$scope.deletepdfFile(result.pdfPath);
			}
		});
	},

	$scope.deletepdfFile= function(pdfPath){

		var menuJson = angular.toJson({"filepath":pdfPath});

		$http({
			url: 'rest/leavereport/deletepdffile',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

		});
	}
	
	$scope.leaveSummaryReport =function(){
		
		var monOfDate=($scope.leave.fromdate).split("-")[1];
		var yearOfDate=($scope.leave.fromdate).split("-")[2];
		var startDate;
		var totalLeaveDays;
		if(monOfDate<=3){
			startDate=yearOfDate+"-01-01";
			totalLeaveDays=20;
		}else if(monOfDate>3 && monOfDate<=6){
			startDate=yearOfDate+"-04-01";
			totalLeaveDays=15;
		}else if(monOfDate>6 && monOfDate<=9){
			startDate=yearOfDate+"-07-01";
			totalLeaveDays=10;
		}else if(monOfDate>9 && monOfDate<=12){
			startDate=yearOfDate+"-10-01";
			totalLeaveDays=5;
		}
		
		var beginDate=yearOfDate+"-01-01";	
		var menuJson = angular.toJson({"totalLeaveDays":totalLeaveDays,"startDate":startDate,"fromdate":$scope.leave.fromdate,"todate":$scope.leave.todate,"beginDate":beginDate});

		$http({
			url: 'rest/leavereport/leaveSummaryReport',
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
		});
	}
	
}])