'use strict';

angular.module('myApp.controllers')

.controller('teamAssignmentController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {


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
		
		$scope.manageProject=result.manageProject;
		$scope.manageTeam=result.manageTeam;
		/*$scope.manageCustomer=result.manageCustomer;*/
		$scope.accessRights();
	});
	
	$scope.accessRights=function(){
		
   if(!$scope.manageTeam){
			
			$state.go('timesheet')
		
		}else{
			$scope.getList();
		}
		
	}
	
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

		$scope.rolelist=result;
	});
	
	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
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
	
	$scope.getList = function(){
			
		if($scope.manageProject && $scope.manageTeam ){
			
			$scope.getListByAllProjects();
			
		}else{
			
			$scope.getListByProject();
		}
	}
	
	
	$scope.teamList = function() {
			
		$scope.getListByAllProjects = function(){
		
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

				});
	    	}
			
		
		 $scope.getListByProject = function(){  
			 

				$http({
					
					url: 'rest/timesheet/getprojectlist',
					method: 'GET',
					/*data: menuJson,*/
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(result, status, headers) {
					
					$scope.projectlist=result;
					
					$scope.projectIds=[];
				
					for(var i=0;i<($scope.projectlist).length;i++){
						
						$scope.projectIds.push($scope.projectlist[i].projectId);
					
					}
								
         	var menuJson = angular.toJson({"projectIds":$scope.projectIds
	          });
	        console.log(menuJson);
	
	
	  $http({
			url: 'rest/teamAssignment/showAssignedTeamListByUserProjects',
			method: 'POST',
			 data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.teamlists=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.teamlists.slice(begin, end);
				$scope.totalItems =	$scope.teamlists.length;

			     });

	         	})
		
	          });
		 }
    }
	
	$scope.validateAssignment = function(){

		var menuJson = angular.toJson({
			"projectId":$scope.project.projectId,"memberId":$scope.member.id,"roleId":$scope.role.id
		});
		
		$http({
			url: 'rest/teamAssignment/validateAssignment',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result=="false"){
				
				$scope.assignTeam();
													
			}else{
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("This Member is Already Assigned to this Project");
				$scope.teamList();
			}
		
		})

	};

	$scope.assignTeam = function(){
		
		var menuJson = angular.toJson({
			"projectId":$scope.project.projectId,"memberId":$scope.member.id,"roleId":$scope.role.id
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
				$scope.project="";
				$scope.member="";
				$scope.role="";
				$scope.teamList();
			}else if(result.value=="error"){
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Error in Inserting");
			}

		})
	},
	
	$scope.projectChange=function(){
		
		$scope.projectchange="true";
		
	},

	$scope.memberChange=function(){
		
		$scope.memberchange="true";
		
	},


	$scope.roleChange=function(){
		
		$scope.rolechange="true";
		
	},


	$scope.validateUpdate = function(reqParam){
		
		var projectChange=$scope.projectchange;
		var memberChange=$scope.memberchange;
		var roleChange=$scope.rolechange;
	
		
		if(projectChange == undefined && memberChange == undefined && roleChange=="true"){
		
			$scope.updateTeamAssignment(reqParam);
			$scope.projectchange=undefined;
			$scope.memberchange=undefined;
			$scope.rolechange=undefined;
			
		}else{

		var menuJson = angular.toJson({
			"projectId":reqParam.project.projectId,"memberId":reqParam.member.id,"roleId":reqParam.role.id
		});
		
		$http({
			url: 'rest/teamAssignment/validateAssignment',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result=="false"){
				
				$scope.updateTeamAssignment(reqParam);
				$scope.projectchange=undefined;
				$scope.memberchange=undefined;
				$scope.rolechange=undefined;
													
			}else{
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("This Member is Already Assigned to this Project");
				$scope.teamList();
				$scope.projectchange=undefined;
				$scope.memberchange=undefined;
				$scope.rolechange=undefined;
			}
		
		})
	}
};

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
