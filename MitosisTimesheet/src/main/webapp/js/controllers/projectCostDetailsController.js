'use strict';

angular.module('myApp.controllers')

.controller('projectCostDetailsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.costshow = true;
	

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

			},
	
	
	$scope.projectchange = function (project){
				
				
				
				var menuJson = angular.toJson({"projectId":project.projectId})

				$http({

					url: 'rest/projectCost/getTeamMember',
					method: 'POST',
					data: menuJson,
					headers: {
						'Content-Type': 'application/json'
					}
				}).success(function(result, status, headers) {
					

					$scope.memberlist=result;

			
				});	
				
				
		
				
				
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
					
					console.log(result);

					if(result.projectType==null){
						$('#h-show').hide();
						$('#hr-show').hide();
						$('#buttons').hide();
						$('#rateTable').hide();
						$scope.cost=undefined;
						$scope.hdrid=undefined;
						$scope.empList=undefined;
						$scope.emp=undefined;
						return;
					}else if(result.projectType=="Fixed"){
						$('#h-show').hide();
						$('#hr-show').hide();
						$('#buttons').hide();
						$('#rateTable').hide();
						$scope.cost=result;
						$scope.hdrid=result.id;

					}else{
						
						if(result.projectType="Hourly"){
							
							$scope.costshow = false;
							
						}
						if(result.projectType="Monthly"){
							
							$scope.costshow = true;
							
						}

						$('#h-show').show();
						$('#hr-show').show();
						$('#buttons').show();
						$('#rateTable').show();

						$scope.cost=result;
						$scope.hdrid=result.id;

						for(var i=0;i<result.projectCostDetails.length;i++){
							

							if(angular.isUndefined($scope.emp)){
								$scope.emp= new Array();
								$scope.index=0;
							}

							var empRate=[];

							empRate={"member":result.projectCostDetails[i].employee,"rate":result.projectCostDetails[i].rate,"id":result.projectCostDetails[i].id,"index":$scope.index};
							
							$scope.emp.push(empRate);
						    $scope.index++;
						}

						$scope.empList=$scope.emp;
						console.log($scope.empList);
					}
				});

		
		
		
		
		
	}

	$scope.addProjectCostDetails = function(){

		
		if(($scope.cost.currencyCode).length>3){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Currency Code");
			$('#currency').val('');
			$('#currency').focus();
			$('#addDet').show();
			return;
		}
		
		if(isNaN($scope.cost.costOfEmp)){
			
			$scope.cost.costOfEmp=undefined;
			
			
		}

		var projectType=$scope.cost.projectType;

		if(projectType=="Fixed"){

			if($scope.cost.projectCost==undefined | $scope.cost.projectCost==''){
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Please Enter Project Cost");
				$("#costdet").focus();	
				return;
			}else if(($scope.cost.projectCost).length>8){
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Invalid Cost");
				$('#costdet').val('');
				$('#costdet').focus();
				$('#addDet').show();
				return;
			}
		
			


			var menuJson=angular.toJson({"id":$scope.hdrid,"projectId":$scope.project.projectId,"projectType":$scope.cost.projectType,
				"projectCost":$scope.cost.projectCost,"currencyCode":$scope.cost.currencyCode});			

			$('#addDet').hide();

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
			
			if($scope.empList==undefined){
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Please Add Employees");
				return;
			}

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
		}
		else if(($scope.cost.costOfEmp==undefined | $scope.cost.costOfEmp=='') && $scope.cost.projectType!="Monthly"){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Please Enter Rate");
			return;
		}else if(parseInt(($scope.cost.costOfEmp)).length>4){
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
		
		$scope.iterator = $scope.empList.length+1;

		var empRate=[];
		
		$scope.cost.costOfEmp=parseFloat($scope.cost.costOfEmp);
		
		if(isNaN($scope.cost.costOfEmp)){
			
			$scope.cost.costOfEmp = undefined;
			
		}

		empRate={"member":$scope.member.member,"rate":$scope.cost.costOfEmp,"index":$scope.iterator};

		$scope.empList.push(empRate);
		$scope.iterator++;
		console.log($scope.empList);
		$scope.cost.costOfEmp='';
		$scope.member='Member';
	},

	$scope.nameValidation = function(member){
		
		console.log("member:---------->"+member);

		var listLength=$scope.empList.length;

		if(listLength!=0){

			for(var i=0;i<$scope.empList.length;i++){
				if($scope.empList[i].member.id==member.member.id){  
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

			$scope.empList.splice(sheet.index-1,1);

			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Employee Cost Detail Deleted");
		}

	}
	
		
	$scope.costTypeChange = function(project){
	
		
		var menuJson = angular.toJson({"projectId":project.projectId})
		
		var projectType=$scope.cost.projectType;

		
		if(projectType=="Hourly"){
			
			
			$scope.costshow=false;
			
	
		}
		
		else if(projectType=="Monthly"){
			
			

			$scope.costshow=true;
			
			
		}

		
		
	}
}])