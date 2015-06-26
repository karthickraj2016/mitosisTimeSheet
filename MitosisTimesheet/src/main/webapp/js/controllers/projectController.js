'use strict';

angular.module('myApp.controllers')


.controller('projectController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {
	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 5
	,$scope.maxSize = 5;

	$scope.check = function(sheet){
		if(sheet.projectName == '' || sheet.projectName == undefined){
			return true;
		} else if(sheet.customerName == '' || sheet.customerName == undefined) {
			return true;
		} else if(sheet.billable == '' || sheet.billable == undefined) {
			return true;
		} else{
			return false;
		}

	}
	
	$http({
		url: 'rest/timesheet/getUserDetails',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {
		
		$scope.manageProject=result.manageProject;
		$scope.manageTeam=result.manageTeam;
		/*$scope.manageCustomer=result.manageCustomer;*/
	});

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

	$('#name').blur(function(){
		var projectName=$('#name').val();
		if(projectName!=""){
			if(projectName.length>100){
				$(".alert-msg1").show().delay(1500).fadeOut(); 
				$(".alert-danger").html("Only 100 letters are Allowed in Projectname Field");
				$('#name').val(''); 
				$('#name').focus();
			}else{
                var projectId=0;
				var menuJson=angular.toJson({"projectName":projectName,"projectId":projectId});
				console.log(menuJson);

				$http({
					url: 'rest/project/nameValidation',
					method: 'POST',
					data: menuJson,
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(result, status, headers) {

					if(result=="false"){
						$('#name').val(''); 
						$('#name').focus();
						$(".alert-msg1").show().delay(1000).fadeOut(); 
						$(".alert-danger").html("Project Name already exists");
					}
				})
			}
		}
	});

	$('#customer').blur(function(){
		var customerName=$('#customer').val();
		if(customerName!=""){
			if(customerName.length>100){

				$(".alert-msg1").show().delay(1500).fadeOut(); 
				$(".alert-danger").html("Only 100 letters are Allowed in Customer Field");
				$('#customer').val(''); 
				$('#customer').focus();
			}
		}
	});
	$scope.list = function() {

		$http({
			url: 'rest/project/showProjectlist',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.projectlist=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.projectlist.slice(begin, end);
				$scope.totalItems =	$scope.projectlist.length;
			});

		})
	},

	$scope.addproject = function(projectname,customername,billable){
		var bill=$scope.project.billable;
		
	if(bill==undefined){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Please Select Billable Option");
		}else{
			var menuJson = angular.toJson({
				"projectname": $scope.project.projectname,"customername":$scope.project.customername,"billable":$scope.project.billable});
			$http({
				url: 'rest/project/addproject',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {
				$scope.project = '';
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Projcet added successfully");
				$scope.list();

			})
		}
	},

	$scope.onBlur = function(e) {
		var projectId=e;
		var projectName=$('#'+projectId).val();
		if(projectName!=""){
			if(projectName.length>100){
				$(".alert-msg1").show().delay(1500).fadeOut(); 
				$(".alert-danger").html("Only 100 letters are Allowed in Projectname Field");
				$('#'+projectId).val(''); 
				$('#'+projectId).focus();
			}else{

				var menuJson=angular.toJson({"projectName":projectName,"projectId":projectId});
				console.log(menuJson);

				$http({
					url: 'rest/project/nameValidation',
					method: 'POST',
					data: menuJson,
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(result, status, headers) {

					if(result=="false"){
						$('#'+projectId).val(''); 
						$('#'+projectId).focus();
						$(".alert-msg1").show().delay(1000).fadeOut(); 
						$(".alert-danger").html("Project Name already exists");
						$scope.list();
					}
				})
			}
		}
	}
	
	$scope.onblur = function(e) {

		var name=e;

		var customerName=$('#'+name).val();
		if(customerName!=""){
			if(customerName.length>100){

				$(".alert-msg1").show().delay(1500).fadeOut(); 
				$(".alert-danger").html("Only 100 letters are Allowed in Customer Field");
				$('#'+name).val(''); 
				$('#'+name).focus();
			}
		}
	}
	
	$scope.updateproject = function(reqParam){

		var customer=reqParam.customerName;
		if(customer.length>100){
			$(".alert-msg1").show().delay(1500).fadeOut(); 
			$(".alert-danger").html("Only 100 letters are Allowed in Customer Field...");
		    $scope.list();
		    return;
		}else{
		$http({
			url: 'rest/project/updateproject',
			method: 'POST',
			data: reqParam,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			if(result){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Project Entry Updated Successfully");
				$state.go('project')
			}
		})
		}	
	},

	$scope.confirmDelete = function(projectId){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteproject(projectId);
		}
	},

	$scope.deleteproject = function(projectId){

		$http({
			url: 'rest/project/removeproject',
			method: 'POST',
			data: {"projectId":projectId},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=="true"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Project Deleted Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Project Delete Failed");
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
	}
	
}])