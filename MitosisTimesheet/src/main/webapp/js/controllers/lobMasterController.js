'use strict';

angular.module('myApp.controllers')


.controller('lobMasterController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.currentPage = 1
	$scope.numPerPage = 8
	$scope.maxSize = 8;


	$scope.list = function (){

		$http({
			url: 'rest/lobMaster/getLobList',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.lobLists=result;
			$scope.totalItems =	$scope.lobLists.length;
			
			$scope.filter = function() {
				$scope.$watch('search', function(term) {
					if(term==undefined){
						$scope.totalItems =	$scope.lobLists.length; 


					}
					window.setTimeout(function() {
						$scope.totalItems = Math.ceil($scope.lobLists.length/$scope.maxSize);

						$scope.noOfPages = Math.ceil($scope.lobLists.length/$scope.maxSize);
					}, 10);
				});

			};

		});

	},

	

	$scope.insert = function (){


		var menuJson = angular.toJson({"lobName":$scope.lobName,"skills":$scope.skills});

		$http({
			url: 'rest/lobMaster/insertEntry',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="alreadyentered"){

				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Lob is already entered...");

			}

			else if(result.value=="inserted"){

				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html(" Lob is successfully Inserted!!!!!");

			}
			
			$scope.list();


		});



	}

	
	

	$scope.Update = function (sheet){


		var menuJson = angular.toJson({"lobName":sheet.lobName,"skills":sheet.skills,"id":sheet.id});



		$http({
			url: 'rest/lobMaster/updateEntry',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="alreadyentered"){

				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Lob is already entered!!!");

			}

			else if(result.value=="inserted"){

				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html(" Lob is successfully updated!!!!!");

			}
			
			$scope.list();


		});





	}


	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteLob(id);
		}
	},

	$scope.deleteLob = function(id){
		
		
		var menuJson = angular.toJson({"id":id});

		$http({
			url: 'rest/lobMaster/deleteEntry',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {


		 if(result.value=="deleted"){

				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Lob is deleted successfully!!!");

			}
		 
		 else if(result.value=="failed"){
			 
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Lob deletion failed!!!");
			 
		 }
		 
		 $scope.list();


		});
	}
	
	
	$scope.pencilClick= function(sheet){
		
		$scope.lists=angular.copy(sheet);
		
	}

	
	$scope.cancel = function (sheet){
		
		sheet=$scope.lists;
		$scope.list();
	}


}])