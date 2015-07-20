'use strict';

angular.module('myApp.controllers')


.controller('leaveReportController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	$scope.units;

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
		var dd = ("0"+ (dt.getDate())).slice(-2);
		var mm = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		$scope.leave.fromdate=dt;
		$scope.leave.todate=dt;
	};

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
			onSelect: function(selected) {
				/*$scope.leave.todate=selected;*/
			}

	};

	/*$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
	};*/


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
			$rootScope.name=result;

		}
	});


	$scope.leavereport = function(){

		var menuJson = angular.toJson({"fromdate":$scope.leave.fromdate,"todate":$scope.leave.todate,"name":$rootScope.name});

		var fromdate = $scope.leave.fromdate;
		var todate = $scope.leave.todate;

		if (fromdate > todate) {
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
				scope.deletepdfFile(result.pdfPath);
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


	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})

	};

}])