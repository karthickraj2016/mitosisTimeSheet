'use strict';

angular.module('myApp.controllers')


.controller('projectController', ['$scope', '$http', '$state','$localStorage','$rootScope','filterFilter', function($scope, $http, $state,$localStorage, $rootScope,filterFilter) {
	$scope.filteredParticipantsResults = []
	,/*$scope.currentPage = 1*/
	$scope.numPerPage = 8
	,$scope.maxSize = 8;

	$scope.check = function(sheet){
		if(sheet.projectName == '' || sheet.projectName == undefined){
			return true;
		} else if(sheet.customer.customerName == '' || sheet.customer.customerName == undefined) {
			return true;
		} else if(sheet.billable == '' || sheet.billable == undefined) {
			return true;
		}  else if(sheet.startEntryDate == '' || sheet.startEntryDate == undefined) {
			return true;
		} else if(sheet.endEntryDate == '' || sheet.endEntryDate == undefined) {
			return true;
		} else if(sheet.status == '' || sheet.status == undefined) {
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
				$scope.startDate=dt;
				$scope.endDate=dt;
			};
		
			$scope.dateOptions = {
					changeYear: true,
					changeMonth: true,
					dateFormat: 'dd-mm-yy',
			};
		
			$scope.startdatechange = function(fromDate){
				$scope.toDate = fromDate;
			};
			
			
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
		$scope.accessRights();
	});


	$scope.accessRights=function(){

		if(!$scope.manageProject){

			$state.go('timesheet')
		}

	}


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
			
			 $scope.setPage = function(pageNo) {
			        $scope.currentPage = pageNo;
			    };
			$scope.noOfPages = Math.ceil($scope.projectlist.length/$scope.maxSize);
			 $scope.$watch('search', function(term) {
				 
				 $scope.filtered = filterFilter($scope.projectlist, term);
			        $scope.noOfPages = Math.ceil($scope.filtered.length/$scope.maxSize);
			    });

		});
	}

	
	$http({

		url: 'rest/project/getCustomerList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.customerlist=result;
	});
	
		

	$scope.addproject = function(projectname,customer,billable,startDate,endDate,taskstatus){
				var startdate=$scope.project.startDate;
				var enddate=$scope.project.endDate;
				
				var menuJson = angular.toJson({
					"projectname": $scope.project.projectname,"customerId":$scope.customer.customerId,"billable":$scope.project.billable,"startdate":$scope.project.startDate,"enddate":$scope.project.endDate,"status":$scope.project.status});
				
				if (startdate > enddate) {
					$(".alert-danger").html("End Date cannot be Before Start Date...!");
					return;
							
		          }
				
				else{
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

	
	$scope.updateproject = function(reqParam){
		
		var menuJson=angular.toJson({"projectId":reqParam.projectId,"projectName":reqParam.projectName,"customerId":reqParam.customer.customerId,
						"billable":reqParam.billable,"startdate":reqParam.startEntryDate,"enddate":reqParam.endEntryDate,"status":reqParam.status});
		
			$http({
				url: 'rest/project/updateproject',
				method: 'POST',
				data: menuJson,
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
			delete $localStorage.projectList;

			$state.go('login')
		})
	}

}])
.directive('autoComplete', function($timeout) {
	return function(scope, iElement, iAttrs) {
		iElement.autocomplete({
			source: scope[iAttrs.uiItems],
			select: function() {
				$timeout(function() {
					iElement.trigger('#hello');
				}, 0);
			}
		});
	};
})
.filter('startFrom', function() {
    return function(input, start) {
        if(input) {
            start = +start; //parse to int
            return input.slice(start);
        }
        return [];
    }
});
