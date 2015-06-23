'use strict';

angular.module('myApp.controllers')

.controller('teamController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {


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

		url: 'rest/teamAssignment/getProjectList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.projectUnits=result;
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

		$scope.memberUnits=result;
	});

	$http({

		url: 'rest/teamAssignment/getRoleList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.roleUnits=result;
	});
	
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

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 5
	,$scope.maxSize = 5;
	$scope.units;
	var hoursallowed;

	$scope.checkRequired = function(sheet){
		if(sheet.project.projectName == '' || sheet.project.projectName == undefined){
			return true;
		} else if(sheet.member.name == '' || sheet.member.name == undefined) {
			return true;
		} else if(sheet.role.roleName == '' || sheet.role.roleName == undefined) {
			return true;
		} else{
			return false;
		}

	}

	$scope.teamList = function() {

		$http({
			url: 'rest/teamAssignment/showAssignedTeamList',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.teamLists=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.teamLists.slice(begin, end);
				$scope.totalItems =	$scope.teamLists.length;

			});

		})
	},


	$scope.assignTeam = function(){

		var menuJson = angular.toJson({
			"projectId":$scope.projectunit.projectId,"memberId":$scope.memberunit.id,"roleId":$scope.roleunit.id
		});


		$http({
			url: 'rest/teamAssignment/insertTeamDetails',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			if(result.value=="inserted"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Team Assignment Entry Added Successfully.");
				$scope.projectunit="";
				$scope.memberunit="";
				$scope.roleunit="";
				$scope.teamList();
			}else if(result.value=="error"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Error in Inserting");
			}

		})
	},


	$scope.updateTeamAssignment = function(reqParam){


		$http({
			url: 'rest/teamAssignment/updateTeamAssignment',
			method: 'POST',
			data: reqParam,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			if(result){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Team Assignment Updated Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Team Assignment Updating Failed");
			}
			$scope.teamList();
		})

	},

	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteTeamAssignment(id);
		}
	},

	$scope.deleteTeamAssignment = function(id){

		$http({
			url: 'rest/teamAssignment/deleteAssignment',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=='success'){

				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Team Assignment Entry Deleted Successfully");
				$scope.unit="";
			} else{

				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Team Assignment Entry Delete Failed");
			}
			$scope.teamList();	
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
