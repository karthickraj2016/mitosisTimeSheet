angular.module('myApp.controllers')


.controller('projectCostViewController', ['$scope', '$http', '$state','$localStorage','$rootScope','filterFilter', function($scope, $http, $state,$localStorage, $rootScope,filterFilter) {
	
	
	
	$scope.list = function (){
		
		console.log("hi");
		
		
		$http({

			url: 'rest/projectCost/viewAllProjectCosts',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			
			console.log(result);

		
		});

		
		
		
		
		
	}
	
	

}])