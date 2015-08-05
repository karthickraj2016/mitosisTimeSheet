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

		url: 'rest/projectCost/getProjectList',
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
		
		$scope.emp=undefined;

		var menuJson = angular.toJson({"projectId":project.projectId})

		$http({

			url: 'rest/projectCost/projectValidation',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result.projectType==null){
				$scope.hdrid=undefined;
				return;
			}else if(result.projectType=="Fixed"){
				$('#h-show').hide();
				$('#hr-show').hide();
				$('#buttons').hide();
				$('#rateTable').hide();
				$scope.cost=result;
				$scope.hdrid=result.id;
				
				}else{
				
				 $('#h-show').show();
		            $('#hr-show').show();
		            $('#buttons').show();
		            $('#rateTable').show();

			$scope.cost=result;
			$scope.hdrid=result.id;
		
			for(var i=0;i<result.projectCostDetails.length;i++){
				
				if(angular.isUndefined($scope.emp)){
					$scope.emp= new Array();
				}
				
				var empRate=[];

				empRate={"member":result.projectCostDetails[i].employee,"rate":result.projectCostDetails[i].rate,"id":result.projectCostDetails[i].id};
			  
				$scope.emp.push(empRate);
			}
			
			$scope.empList=$scope.emp;
			}
		});
	},

	$scope.addProjectCostDetails = function(){
		
		$('#addDet').hide();
		
		if(($scope.cost.projectCost).length>8){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Cost");
			$('#costdet').val('');
			$('#costdet').focus();
			$('#addDet').show();
			return;
		}
		
		if(($scope.cost.currencyCode).length>3){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Currency Code");
			$('#currency').val('');
			$('#currency').focus();
			$('#addDet').show();
			return;
		}
		

		var projectType=$scope.cost.projectType;

		if(projectType=="Fixed"){
			
			if($scope.cost.projectCost==undefined){
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Please Enter Project Cost");
				$("#costdet").focus();	
				return;
			}

			var menuJson=angular.toJson({"id":$scope.hdrid,"projectId":$scope.project.projectId,"projectType":$scope.cost.projectType,
				"projectCost":$scope.cost.projectCost,"currencyCode":$scope.cost.currencyCode});			

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
				$scope.hdrid=undefined;
				$('#addDet').show();
			});

		}else{

			var menuJson=angular.toJson({"id":$scope.hdrid,"projectId":$scope.project.projectId,"projectType":$scope.cost.projectType,
				"projectCost":$scope.cost.projectCost,"currencyCode":$scope.cost.currencyCode,
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
					$scope.emp=undefined;
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
				}
				$('#addDet').show();
				$scope.hdrid=undefined;
				$scope.emp=undefined;
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
		}else if(($scope.cost.costOfEmp).length>4){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Rate");
			$('#rate').val('');
			$('#rate').focus();
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