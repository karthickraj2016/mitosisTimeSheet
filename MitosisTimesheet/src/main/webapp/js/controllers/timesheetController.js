'use strict';

angular.module('myApp.controllers')

.controller('listcontroller', ['$scope', '$http', '$state','$rootScope','$localStorage', function($scope, $http, $state, $rootScope,$localStorage) {

	    
   	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	
   	var hoursallowed;

	$scope.checkRequired = function(sheet){
		if(sheet.entryDate == '' || sheet.entryDate == undefined){
			return true;
		} else if(sheet.hours == '' || sheet.hours == undefined) {
			return true;
		} else if(sheet.hours == '' || sheet.hours == undefined) {
			return true;
		} else if(sheet.description == '' || sheet.description == undefined) {
			return true;
		} else{
			return false;
		}

	}
	
	$scope.dates = function() {
	     $scope.timesheet = '';
		 $scope.timesheet={};
		    var dt = new Date();
		    var dd = dt.getDate();
		    var mm = dt.getMonth()+1; 
		    var yyyy = dt.getFullYear();
		    dt=dd+"-"+mm+"-"+yyyy;
		 $scope.timesheet.date=dt;			
	};

	
	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			maxDate:'0d',
			minDate:-30,
			dateFormat: 'dd-mm-yy',

			/* yearRange: '1900:-0'*/
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
			$rootScope.name=result;
			
		}
	})
	
	
		
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
		$scope.manageCustomer=result.manageCustomer;
		$scope.manageEmployees=result.manageEmployees;
	});

		
	$http({
		
				url: 'rest/timesheet/getprojectlist',
				method: 'GET',
				/*data: menuJson,*/
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {
				
				console.log(result);
				
				$scope.projectList=result;
				$localStorage.projectList = result;
			});
	
	
/*	$scope.allProjectList = function(){
				$http({
					url: 'rest/project/showProjectlist',
					method: 'GET',
					 data: menuJson,
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(result, status, headers) {
		
					$localStorage.allProjectList = result;
		
				})
			}
*/
	$scope.list = function() {
		
		$http({
			url: 'rest/timesheet/showlist',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.timesheetList=result; 
				console.log("result",result);
			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.timesheetList.slice(begin, end);
				$scope.totalItems =	$scope.timesheetList.length;
				$scope.dates();
			});

		})
	},
	
	 
	$scope.addtimesheet = function(date,hours,description,issue){
		
	var hours=parseFloat($('#hours').val());

		if(hours!=0.0 && hours<=16.0){
			var min=hours%1;
						min=Math.round(min * 100) / 100;
						if(min>0.60)
							{
							 $(".alert-msg1").show().delay(2000).fadeOut(); 
							 $(".alert-danger").html("Only 60 mins are allowed");
							 $('#hours').val('');
								$('#hours').focus();
							 return ;
							}else if(min==0.60){
								$scope.timesheet.hours=parseInt(hours)+1;
							}else if(min<0.6){
								$scope.timesheet.hours=hours;
							}	

			var menuJson = angular.toJson({
				"date": $scope.timesheet.date,"hours":$scope.timesheet.hours,"issueNumber":$scope.timesheet.issueNumber,"description":$scope.timesheet.description,"projectId":$scope.projects.projectId
 			});


			$http({
				url: 'rest/timesheet/insertdetails',
				method: 'POST',
				data: menuJson,
				headers: {
				}
			}).success(function(result, status, headers) {
				$scope.timesheet = '';
				
				if(result.value=="done"){
					$(".alert-msg1").show().delay(1000).fadeOut(); 
				    $(".alert-danger").html("you have recorded all 16 hours today");
				    $scope.dates();
				    console.log($scope.timesheet.description);
			}
				
				else if(result.value=="err_total"){
					
					 hoursallowed = result.hoursallowed.toFixed(1);
					 
					 $(".alert-msg1").show().delay(2000).fadeOut(); 
					 $(".alert-danger").html('you have only ' +hoursallowed+ ' hours left. hours cannot be greater than ' +hoursallowed+ '');
					 $scope.unit="";
					 $scope.dates();
					 console.log($scope.timesheet.description);			
				}
				else if(result.value=="inserted"){
					
				    $(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Timesheet Entry Added Successfully.");
					$scope.unit="";
					$scope.list();
					
				}
				else{
					 
					 $(".alert-msg1").show().delay(1000).fadeOut(); 
					 $(".alert-danger").html("insertion failed");
					 $scope.dates();
				}
			
			})
		}else{
			 $(".alert-msg1").show().delay(1000).fadeOut(); 
			 $(".alert-danger").html("Enter Valid Hours");
			$('#hours').val('');
			$('#hours').focus();
		}
	},

	$scope.updatetimesheet = function(reqParam){

		var hour= parseFloat(reqParam.hours);

	
		if(hour!=0.0 && hour<=16.0){
			
			var min=hour%1;
			min=Math.round(min * 100) / 100;
			if(min>0.60)
				{
				 $(".alert-msg1").show().delay(2000).fadeOut(); 
				 $(".alert-danger").html("Only 60 minutes are allowed");
				 $scope.list();
				 return ;
				}else if(min==0.60){
					reqParam.hours=parseInt(hour)+1;
				}else if(min<0.6){
					reqParam.hours=hour;
				}	
			var menuJson = angular.toJson({
				"date": reqParam.entryDate,"hours":reqParam.hours,"issueNumber":reqParam.issueNumber,"description":reqParam.description,"id":reqParam.id,"ProjectId":reqParam.project.projectId
	 
		
			});
		$http({
			url: 'rest/timesheet/updateTimesheet',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result.value=="hoursallowedis0"){
						 
				 $(".alert-msg1").show().delay(2000).fadeOut(); 
				 $(".alert-danger").html("you cannot maximize your hours..you have already recorded all your hours");
				$scope.list();
				return;
				
			}
			

			else if(result.value=="err_total"){
				
				hoursallowed = result.hoursallowed.toFixed(1);
				 $(".alert-msg1").show().delay(2000).fadeOut(); 
				 $(".alert-danger").html('you can maximize upto ' +hoursallowed+ ' only ');
				$scope.list();
				return;
			}
			else if(result.value=="inserted"){
							 
				 $(".alert-msg").show().delay(1000).fadeOut(); 
				 $(".alert-success").html("Timesheet Entry updated successfully");
				$scope.list();
				return;
				
			}
			else{
						 
				 $(".alert-msg1").show().delay(1000).fadeOut(); 
				 $(".alert-danger").html("updation failed");
			}
			$state.go('timesheet')
		})
		}else{
			 
		    $(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html('Enter Valid Hours');
			$scope.list();
			$('#'+reqParam.id).show();
			$('#'+reqParam.id).val('');
			$('#'+reqParam.id).focus();
			
		}
	},
	
	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteTimesheet(id);
		}
	},

	$scope.deleteTimesheet = function(id){


		$http({
			url: 'rest/timesheet/deleteTimesheet',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=='success'){
				 
				 $(".alert-msg").show().delay(1000).fadeOut(); 
				 $(".alert-success").html("Timesheet Entry Deleted Successfully");
				 $scope.unit="";
			} else{
				 
				 $(".alert-msg1").show().delay(1000).fadeOut(); 
				 $(".alert-danger").html("Timesheet Entry Delete Failed");
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

function HeaderController($scope, $location) 
{ 
    $scope.isActive = function (viewLocation) { 
        return viewLocation === $location.path();
    };
}