'use strict';

angular.module('myApp.controllers')


.controller('employeeMasterController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
	};


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
			$scope.name=result;
		}
	});


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


	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;

	$scope.check = function(sheet){
		if(sheet.employeeId == '' || sheet.employeeId == undefined){
			return true;
		} else if(sheet.firstName == '' || sheet.firstName == undefined) {
			return true;
		} else if(sheet.lastName == '' || sheet.lastName == undefined) {
			return true;
		} else if(sheet.joiningEntryDate == '' || sheet.joiningEntryDate == undefined) {
			return true;
		} else if(sheet.expStartEntryDate == '' || sheet.expStartEntryDate == undefined) {
			return true;
		} else if(sheet.yearsOfExperience == '' || sheet.yearsOfExperience == undefined) {
			return true;
		}else if(sheet.monthsOfExperience == '' || sheet.monthsOfExperience == undefined) {
			return true;
		}else if(sheet.level == '' || sheet.level == undefined) {
			return true;
		}else if(sheet.asOnEntryDate == '' || sheet.asOnEntryDate == undefined) {
			return true;
		}else if(sheet.billable == '' || sheet.billable == undefined) {
			return true;
		}else{
			return false;
		}
	},

	$scope.list = function() {

		$http({
			url: 'rest/employeeMaster/showEmployeeDetailsEntryList',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.employeeEntryList=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.employeeEntryList.slice(begin, end);
				$scope.totalItems =	$scope.employeeEntryList.length;

			});

		})
	},

	$scope.addEmployeeMasterDetails = function(){

		var dt = new Date();
		var dd = ("0"+ (dt.getDate())).slice(-2);
		var mm = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		var asOnDate=dt;

		var menuJson=angular.toJson({"userId":$scope.employee.id,"employeeId":$scope.employeeDetail.employeeId,"firstName":$scope.employeeDetail.firstName,"lastName":$scope.employeeDetail.lastName,"joiningDate":$scope.employeeDetail.joiningDate,
			"expStartDate":$scope.employeeDetail.expStartDate,"asOnDate":asOnDate,"billable":$scope.employeeDetail.billable});

		$http({
			url: 'rest/employeeMaster/addEmployeeDetails',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Employee Details Added Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Process Failed");
			}
			$scope.list();	
			$scope.employeeDetail="";
		})
	},

	$scope.updateEmployeeMasterDetails = function(reqParam){

		var menuJson=angular.toJson({"id":reqParam.id,"userId":reqParam.employee.id,"employeeId":reqParam.employeeId,"firstName":reqParam.firstName,"lastName":reqParam.lastName,"joiningDate":reqParam.joiningEntryDate,"expStartDate":reqParam.expStartEntryDate,
			"yearsOfExp":reqParam.yearsOfExperience,"monthsOfExp":reqParam.monthsOfExperience,"level":reqParam.level,"asOnDate":reqParam.asOnEntryDate,"billable":reqParam.billable});

		$http({
			url: 'rest/employeeMaster/updateEmployeeDetails',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Employee Details Updated Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Process Failed");
			}
			$scope.list();	
		})
	},

	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteEmpDetails(id);
		}
	},

	$scope.deleteEmpDetails = function(id){

		$http({
			url: 'rest/employeeMaster/deleteEmployeeDetailEntry',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Employee Detail Deleted Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Employee Detail Delete Failed");
			}
			$scope.list();	
		})
	},

	$scope.calculateAndUpdateExp = function(){

		var employeelist=$scope.employeeEntryList
		var dt = new Date();
		var currentDate=new Date();
		var dd = ("0"+ (dt.getDate())).slice(-2);
		var mm = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		var asOnDate=dt;

		for(var i=0;i<employeelist.length;i++){

			var monthsOfExp;
			var expStartDate =(employeelist[i].expStartEntryDate).split("-");
			var yearsOfExp=(currentDate.getFullYear())-(expStartDate[2]);

			if(yearsOfExp>=1 && (currentDate.getMonth()+1)>=(expStartDate[1])){

				yearsOfExp=(currentDate.getFullYear())-(expStartDate[2]);
				monthsOfExp=(currentDate.getMonth()+1)-(expStartDate[1]);

			}else if(yearsOfExp==0){

				yearsOfExp=((currentDate.getFullYear())-(expStartDate[2]));
				monthsOfExp=(((currentDate.getFullYear())-(expStartDate[2]))*12)+((currentDate.getMonth()+1)-(expStartDate[1]));
			}else {
				yearsOfExp=((currentDate.getFullYear())-(expStartDate[2]))-1;
				monthsOfExp=12+(((currentDate.getMonth()+1)-(expStartDate[1])));
			}

							
			var menuJson=angular.toJson({"id":employeelist[i].id,"userId":employeelist[i].employee.id,"employeeId":employeelist[i].employeeId,"firstName":employeelist[i].firstName,"lastName":employeelist[i].lastName,"joiningDate":employeelist[i].joiningEntryDate,
					"expStartDate":employeelist[i].expStartEntryDate,"yearsOfExp":yearsOfExp,"monthsOfExp":monthsOfExp,"asOnDate":asOnDate,"billable":employeelist[i].billable});

					
			$http({
				url: 'rest/employeeMaster/findEmployeeExpAndUpdate',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="success"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Employees Experience Updated Successfully");
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
				}
				$scope.list();	
			})
		}
			
	}
	 
}])