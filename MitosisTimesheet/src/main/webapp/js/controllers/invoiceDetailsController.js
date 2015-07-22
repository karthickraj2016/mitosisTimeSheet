angular.module('myApp.controllers')

.controller('invoiceDetailsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.list = function(){
	$http({
		
		url: 'rest/invoiceDetails/getProjectList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.projectList=result;
	});
	
	
	
	$http({
		
		url: 'rest/invoiceDetails/getCustomerList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.customerList=result;
	});
	
	
	
	
	}
	
	
}])
