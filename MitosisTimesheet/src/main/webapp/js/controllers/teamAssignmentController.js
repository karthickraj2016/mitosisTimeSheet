	'use strict';

angular.module('myApp.controllers')

.controller('teamAssignmentController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.accessRights=function(){

		if(!$scope.manageTeam){

			$state.go('timesheet')

		}else{
			$scope.teamList();
		}

	}

	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			minDate:0,
			dateFormat: 'dd-mm-yy',

			/* yearRange: '1900:-0'*/
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

		$scope.memberlist=result;

		for(var i=0;i<$scope.memberlist.length;i++){
			if($scope.memberlist[i].adminFlag >= 1){
				$scope.memberlist.splice(i,1);
			}
		}
		console.log($scope.memberlist);
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
	,$scope.maxSize = 8;
	$scope.units;
	var hoursallowed;

	$scope.checkRequired = function(sheet){
		if(sheet.project.projectName == '' || sheet.project.projectName == undefined){
			return true;
		} else if(sheet.member.name == '' || sheet.member.name == undefined) {
			return true;
		} else if(sheet.role.roleName == '' || sheet.role.roleName == undefined) {
			return true;
		}else if(sheet.releaseEntryDate == '' || sheet.releaseEntryDate == undefined) {
			return true;
		} else{
			return false;
		}

	}

	$scope.teamList = function(){

		if($rootScope.manageProject && $rootScope.manageTeam ){

			$scope.getListByAllProjects();

		}else{

			$scope.getListByProject();
		}
	}

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
			
			console.log($scope.project);
		

			$scope.projectlist=result;
			
			
			$scope.showAssignedTeamList();
		});

		
		
	}
	
	
	$scope.showAssignedTeamList = function (){
		
		console.log($scope.project);

		$http({
			url: 'rest/teamAssignment/showAssignedTeamList',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			var a=result; 
			var emp;
			
			console.log($scope.project);
			console.log($scope.member);
			console.log($scope.role);
			
			
			for(var i=0;i<a.length;i++){

				if(angular.isUndefined(emp)){
					emp = new Array();
				}				

				var empRate={"id":a[i].id,"member":{"id":a[i].member.id,"name":a[i].member.name},"project":{"projectId":a[i].project.projectId,"projectName":a[i].project.projectName},"role":{"id":a[i].role.id,"roleName":a[i].role.roleName},
						"releaseEntryDate":a[i].releaseEntryDate};

				emp.push(empRate);
			}
			$scope.teamLists=emp;
			
			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.teamLists.slice(begin, end);
				$scope.totalItems =	$scope.teamLists.length;
			});
			$scope.noOfPages = Math.ceil($scope.teamLists.length/$scope.maxSize);

			$scope.setPage = function(pageNo) {
				$scope.currentPage = pageNo;
			};

			$scope.filter = function() {
				$scope.$watch('search', function(term) {
					if(term==undefined){
						$scope.totalItems =	$scope.teamLists.length; 


					}
					window.setTimeout(function() {
						$scope.totalItems = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);

						$scope.noOfPages = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);
					}, 10);
				});

			};

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

				var a=result; 
				var emp;
				
				for(var i=0;i<a.length;i++){

					if(angular.isUndefined(emp)){
						emp = new Array();
					}				

					var empRate={"id":a[i].id,"member":{"id":a[i].member.id,"name":a[i].member.name},"project":{"projectId":a[i].project.projectId,"projectName":a[i].project.projectName},"role":{"id":a[i].role.id,"roleName":a[i].role.roleName},
							"releaseEntryDate":a[i].releaseEntryDate};

					emp.push(empRate);
				}
				$scope.teamLists=emp;
			
				$scope.$watch('currentPage + numPerPage', function() {
					var begin = (($scope.currentPage - 1) * $scope.numPerPage)
					, end = begin + $scope.numPerPage;
					$scope.filteredParticipantsResults = $scope.teamLists.slice(begin, end);
					$scope.totalItems =	$scope.teamLists.length;

				});

				$scope.noOfPages = Math.ceil($scope.teamLists.length/$scope.maxSize);

				$scope.setPage = function(pageNo) {
					$scope.currentPage = pageNo;
				};

				$scope.filter = function() {
					$scope.$watch('search', function(term) {
						if(term==undefined){
							$scope.totalItems =	$scope.teamLists.length; 


						}
						window.setTimeout(function() {
							$scope.totalItems = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);

							$scope.noOfPages = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);
						}, 10);
					});	

				};

			});
		})
	},


	$scope.validateAssignment = function(){

		var menuJson = angular.toJson({
			"projectId":$scope.teamAss.projectId,"memberId":$scope.teamAss.memberId,"roleId":$scope.teamAss.roleId
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

	$scope.assignTeam = function(menuJson){
		var menuJson = angular.toJson({
			"projectId":$scope.teamAss.projectId,"memberId":$scope.teamAss.memberId,"roleId":$scope.teamAss.roleId,"releaseDate":$scope.teamAss.releaseDate
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
				console.log($scope.project);
				$scope.teamAss.memberId="";
				$scope.teamAss.roleId="";
				$scope.teamAss.releaseDate="";
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
		
		var menuJson = angular.toJson({"id":reqParam.id,
			"project":reqParam.project,"member":reqParam.member,"role":reqParam.role,"releaseDate":reqParam.releaseEntryDate});


		var projectChange=$scope.projectchange;
		var memberChange=$scope.memberchange;
		var roleChange=$scope.rolechange;
		
		if(projectChange == undefined && memberChange == undefined && roleChange==undefined){
			
			$scope.updateTeamAssignment(menuJson);
			
		}else if(projectChange == undefined && memberChange == undefined && roleChange=="true"){

			$scope.updateTeamAssignment(menuJson);
			$scope.projectchange=undefined;
			$scope.memberchange=undefined;
			$scope.rolechange=undefined;

		}else{

			var menu = angular.toJson({
				"projectId":reqParam.project.projectId,"memberId":reqParam.member.id,"roleId":reqParam.role.id});

			$http({
				url: 'rest/teamAssignment/validateAssignment',
				method: 'POST',
				data: menu,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result=="false"){

					$scope.updateTeamAssignment(menuJson);
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

	$scope.updateTeamAssignment = function(menuJson){
		
		$http({
			url: 'rest/teamAssignment/updateTeamAssignment',
			method: 'POST',
			data: menuJson,
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
	}

}])
