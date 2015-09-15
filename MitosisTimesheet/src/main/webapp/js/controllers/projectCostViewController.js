angular.module('myApp.controllers')


.controller('projectCostViewController', ['$scope', '$http', '$state','$localStorage','$rootScope','filterFilter', function($scope, $http, $state,$localStorage, $rootScope,filterFilter) {
	

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	
	
	$scope.viewdet = function (){
		$('.emp-det-show').fadeIn(200);
		$('.overlay').fadeIn(200);
	}

	
	$scope.list = function (){
		
		$http({

			url: 'rest/projectCost/viewAllProjectCosts',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			
			console.log(result);
			
			$scope.projectCostList = result;
			
			console.log("result",result);
			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.projectCostList.slice(begin, end);
				$scope.totalItems =	$scope.projectCostList.length;
			});

		
		});


		
		
	}
	
	
	$scope.memberDetails = function(sheet){
		
		$scope.memDetList = sheet.projectCostDetails;
		
		if($scope.memDetList.length<=0){
			
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("No members in this project!!!");	

		}
		else {
		$scope.viewdet();
	
		}
		
		
		
	}
	
	

}])