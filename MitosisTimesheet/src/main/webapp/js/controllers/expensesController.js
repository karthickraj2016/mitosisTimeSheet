angular.module('myApp.controllers')

.controller('expensesController', ['$scope', '$http', '$state','$rootScope','$localStorage', function($scope, $http, $state, $rootScope,$localStorage) {
	
	
	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;

	$scope.payingModes =["Cheque","Cash","Card"];
	$scope.periods=["Yearly","Monthly"];
	
	
	$scope.list = function(){

		$http({

			url: 'rest/expenses/showList',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {


			$scope.expensesList=result;
			
			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.expensesList.slice(begin, end);
				$scope.totalItems =	$scope.expensesList.length;
			});
			$scope.noOfPages = Math.ceil($scope.expensesList.length/$scope.maxSize);

			$scope.setPage = function(pageNo) {
				$scope.currentPage = pageNo;
			};

			$scope.filter = function() {
				$scope.$watch('search', function(term) {
					if(term==undefined){
						$scope.totalItems =	$scope.expensesList.length; 
					}
					window.setTimeout(function() {
						$scope.totalItems = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);

						$scope.noOfPages = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);
					}, 10);
				});

			}
		});
		
		
	}
	
	
	$scope.insert = function (){
	
		
		var menuJson=angular.toJson({"expenseDescription":$scope.expenseDescription,"payingMode":$scope.payingMode,"period":$scope.period});
		
		
	
		$http({

			url: 'rest/expenses/insertEntry',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			
			if(result.value=="inserted"){
				

				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Expenses have been inserted successfully!!!!");
				$scope.expenseDescription="";
				$scope.payingMode="";
				$scope.period="";
				$scope.list();
			}
		
		});
		
		
		
		
		
	}
	
	
	$scope.pencilClick = function(sheet){
		
		$scope.sheetList = angular.copy(sheet);
		
	}
	
	$scope.cancel = function(){
		
		
		$scope.expensesList = $scope.sheetList ;
		$scope.list();
		
		
		
	}
	
	
	$scope.update = function(sheet){
		
		
		if(sheet.description==""){
			
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Please enter Expenses Description!!!!");
			$scope.list();
			return;
			
		}
		
		var menuJson=angular.toJson({"expenseDescription":sheet.description,"payingMode":sheet.payingMode,"period":sheet.period,"id":sheet.id});
		
	
	
	
	$http({

		url: 'rest/expenses/updateEntry',
		method: 'POST',
		data: menuJson,
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		
		if(result.value=="updated"){

			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Expenses have been updated successfully!!!!");
		

		}
		$scope.list();
	
	});
	}

	
	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteTeamAssignment(id);
		}
	},

	$scope.deleteTeamAssignment = function(id){

		$http({
			url: 'rest/expenses/deleteEntry',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="deleted"){

				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Expenses Entry Deleted Successfully");
			} else{

				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Expenses Entry Delete Failed");
			}
			$scope.list();	
		})
	}


}])