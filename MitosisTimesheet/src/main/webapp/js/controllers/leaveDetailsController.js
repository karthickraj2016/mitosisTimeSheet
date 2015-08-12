'use strict';

angular.module('myApp.controllers')


.controller('leaveDetailsController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

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
		var dt = new Date();
		var dd = ("0"+ (dt.getDate()-1)).slice(-2);
		var mm = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		$scope.fromDate=dt;
		$scope.toDate=dt;

	};

	$scope.dateOptionsFrom = {

			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
			onSelect: function(selected) {
				$scope.toDate=selected;
				/*$scope.dateOptionsTo("option","minDate", selected);*/
			}

	};

	$scope.dateOptionsTo = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
	};



	$http({

		url: 'rest/teamAssignment/getMemberList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.employeeNameList=result;

		for(var i=0;i<$scope.employeeNameList.length;i++){
			if($scope.employeeNameList[i].adminFlag >= 1){
				$scope.employeeNameList.splice(i,1);
			}
		}
		console.log($scope.employeeNameList);
	});

	$scope.list = function() {

		$http({
			url: 'rest/leaveDetails/showLeaveEntryList',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.leaveEntryList=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.leaveEntryList.slice(begin, end);
				$scope.totalItems =	$scope.leaveEntryList.length;
				$scope.dates();
			});

		})
	},

	$scope.insertLeaveEntry = function(){


		var fromdate = new Date($scope.fromDate.split('-')[2],$scope.fromDate.split('-')[1],$scope.fromDate.split('-')[0]);
		var todate = new Date($scope.toDate.split('-')[2],$scope.toDate.split('-')[1],$scope.toDate.split('-')[0]);

		var datevalidationfromDate =new Date(($scope.fromDate).split("-")[1]+"-"+($scope.fromDate).split("-")[0]+"-"+($scope.fromDate).split("-")[2]);	
		var datevalidationtoDate = new Date(($scope.toDate).split("-")[1]+"-"+($scope.toDate).split("-")[0]+"-"+($scope.toDate).split("-")[2]);;


		if (datevalidationfromDate > datevalidationtoDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			return;
		}
		else if(fromdate.getDay()===2 || fromdate.getDay()===3 || todate.getDay()===2 || todate.getDay()===3){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate or Todate cannot be on Saturdays or Sundays!!!!");
			return;

		}
		else{

			var menuJson = angular.toJson({
				"employeeId": $scope.employee.id,"fromDate":$scope.fromDate,"toDate":$scope.toDate,"reason":$scope.reason
			});

			$http({
				url: 'rest/leaveDetails/insertLeaveEntry',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="inserted"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Leave Entry Added Successfully");
					$scope.reason='';
				}else if (result.value=="already exist"){
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Leave Entry Already Entered");
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
				}
				$scope.list();	

			})
		}
	},

	$scope.updateLeaveEntry = function(reqParam){

		if (reqParam.frmEntryDate > reqParam.toEntryDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			$scope.list();
			return;
		}else{

			var menuJson = angular.toJson({
				"id":reqParam.id,"employeeId": reqParam.employee.id,"fromDate":reqParam.frmEntryDate,"toDate":reqParam.toEntryDate,"reason":reqParam.reason,
			});

			$http({
				url: 'rest/leaveDetails/updateLeaveEntry',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="updated"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Leave Entry Updated Successfully");
				}else if(result.value=="already exist"){
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Leave Entry Already Exist");
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Leave Entry updating Failed");
				}
				$scope.list();	

			})
		}
	},

	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteLeaveEntry(id);
		}
	},

	$scope.deleteLeaveEntry = function(id){


		$http({
			url: 'rest/leaveDetails/deleteLeaveEntry',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=='success'){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Leave Entry Deleted Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Leave Entry Deletion Failed");
			}
			$scope.list();	
		})
	},

	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})

	};

}])