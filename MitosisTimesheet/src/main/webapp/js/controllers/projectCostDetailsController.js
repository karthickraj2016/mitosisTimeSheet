'use strict';

angular.module('myApp.controllers')

.controller('projectCostDetailsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {


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

		$scope.memberlist=result;

		for(var i=0;i<$scope.memberlist.length;i++){
			if($scope.memberlist[i].adminFlag >= 1){
				$scope.memberlist.splice(i,1);
			}
		}
		console.log($scope.memberlist);
	});

	$http({

		url: 'rest/teamAssignment/getProjectList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.projectlist=result;
	});

	$scope.projectValidation= function(project){

		var menuJson = angular.toJson({"projectId":project.projectId})

		$http({

			url: 'rest/projectCost/projectValidation',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="already exist"){

				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Project Already Entered");
				$scope.project="Project";
			}
		});
	},

	$scope.addProjectCostDetails = function(){

		var projectType=$scope.cost.projectType;

		if(projectType=="Fixed"){

			var menuJson=angular.toJson({"projectId":$scope.project.projectId,"projectType":$scope.cost.projectType,
				"projectCost":$scope.cost.projectCost,"currencyCode":$scope.cost.currency});			

			$http({

				url: 'rest/projectCost/addFixedProjectCostDetails',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="success"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Project Cost Details Added Successfully");
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
				}
				$scope.cost='';
				$scope.project="Project";
			});

		}else{

			var menuJson=angular.toJson({"projectId":$scope.project.projectId,"projectType":$scope.cost.projectType,
				"projectCost":$scope.cost.projectCost,"currencyCode":$scope.cost.currency,
				"empList":$scope.empList});			


			$http({

				url: 'rest/projectCost/addHourlyProjectCostDetails',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="success"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Project Cost Details Added Successfully");
					$('#h-show').hide();
					$('#hr-show').hide();
					$('#buttons').hide();
					$('#rateTable').hide();
					$scope.cost='';
					$scope.project="Project";
					$scope.empList=undefined;
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
				}
			});
		}
	},


	$scope.addEmployeeRate = function(){
		
		if($scope.member==undefined | $scope.member=="Member" ){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Please Select Employee");
			return;
		}else if($scope.cost.costOfEmp==undefined | $scope.cost.costOfEmp==''){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Please Enter Rate");
			return;
		}
		
		if(angular.isUndefined($scope.empList)){
			$scope.empList= new Array();
			$scope.iterator=0;
		}

		var empRate=[];

		empRate={"member":$scope.member,"rate":$scope.cost.costOfEmp,"index":$scope.iterator};

		$scope.empList.push(empRate);
		$scope.iterator++;
		console.log($scope.empList);
		$scope.cost.costOfEmp='';
		$scope.member='Member';
	},

	$scope.nameValidation = function(member){

		var listLength=$scope.empList.length;

		if(listLength!=0){

			for(var i=0;i<$scope.empList.length;i++){
				if($scope.empList[i].member==member){  
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Employee Name Already Entered");
					$scope.member='Member';
					return;
				}
			}
		}
	},

	$scope.update = function(sheet){

		$scope.empList[sheet.index]=sheet;

		$(".alert-msg").show().delay(1000).fadeOut(); 
		$(".alert-success").html("Employee Cost Detail Updated");

	},

	$scope.confirmDelete = function(sheet){

		if(confirm('Are you sure you want to delete?')){

			$scope.empList.splice(sheet.index,1);

			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Employee Cost Detail Deleted");
		}

	},

	$scope.logout = function(){

		$http({
			url: 'rest/individualreport/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	}
}])