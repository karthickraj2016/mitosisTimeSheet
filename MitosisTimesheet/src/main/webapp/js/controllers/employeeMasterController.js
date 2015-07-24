'use strict';

angular.module('myApp.controllers')


.controller('employeeMasterController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
			maxDate:'0d'
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
		url: 'rest/timesheet/getUserDetails',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {
		
		$scope.manageFinance=result.manageFinance;
		$scope.manageProject=result.manageProject;
		$scope.manageTeam=result.manageTeam;
		$scope.manageCustomer=result.manageCustomer;
		$scope.manageEmployees=result.manageEmployees;
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
	
	$scope.validateEmployee = function(employee){
		
		var userId=employee.id;
		var id=$('#userId').val();
		
		if(id!=""){
			
			$http({
				url: 'rest/employeeMaster/employeeValidation',
				method: 'POST',
				data: {"userId":userId},
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="already exist"){
					$('#userId').val(''); 
					$('#userId').focus();
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Employee Details Already Entered");
				}
			})
			
		}
	}
	
	
	$('#empId').blur(function(){
		
		var employeeId=$('#empId').val();
	 
		if(employeeId!=""){
		 
		   $http({
				url: 'rest/employeeMaster/employeeIdValidation',
				method: 'POST',
				data: {"employeeId":employeeId},
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="already exist"){
					$('#empId').val(''); 
					$('#empId').focus();
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Employee ID Already Exist");
				}
			})
	    }
	})
	
	
	$scope.addEmployeeMasterDetails = function(){

		var dt = new Date();
		var dd = ("0"+ (dt.getDate())).slice(-2);
		var mm = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		var asOnDate=dt;
		
		var joinDate=($scope.employeeDetail.joiningDate).split("-");
		var startDate=($scope.employeeDetail.expStartDate).split("-");
        var joinedDate=new Date(joinDate[1]+"-"+joinDate[0]+"-"+joinDate[2]);
        var startedDate=new Date(startDate[1]+"-"+startDate[0]+"-"+startDate[2]);
		
        if(joinedDate<startedDate){
			
    	   $(".alert-msg1").show().delay(1000).fadeOut(); 
		   $(".alert-danger").html("ExpStartDate cannot be after JoinedDate!");
		  return;
       }
		
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

		
		var joinDate=(reqParam.joiningEntryDate).split("-");
		var startDate=(reqParam.expStartEntryDate).split("-");
	    var joinedDate=new Date(joinDate[1]+"-"+joinDate[0]+"-"+joinDate[2]);
	    var startedDate=new Date(startDate[1]+"-"+startDate[0]+"-"+startDate[2]);
			
	    if(joinedDate<startedDate){
	    	   $(".alert-msg1").show().delay(1000).fadeOut(); 
			   $(".alert-danger").html("Process Failed");
			 $scope.list();
			 return;
	       }
				
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

			var expStartDate =(employeelist[i].expStartEntryDate).split("-");
			var d2=currentDate.getDate();
			var m2=currentDate.getMonth()+1;
			var y2=currentDate.getFullYear();
			
			if( d2 < (expStartDate[0])){
								
				m2=m2-1;
			}
	
			if(m2< (expStartDate[1])){
				
				y2=y2-1;
				m2=m2+12;
			}
		
			var yearsOfExp=y2-expStartDate[2];
			var monthsOfExp=m2-expStartDate[1];
							
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