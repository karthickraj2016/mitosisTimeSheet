angular.module('myApp.controllers')


.controller('pendingReceiptController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;

$scope.list = function (){
	
	
	$http({

		url: 'rest/payment/pendingReceiptList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {
		
		
		$scope.pendingReceiptList = result;
		
		
		$scope.$watch('currentPage + numPerPage', function() {
			var begin = (($scope.currentPage - 1) * $scope.numPerPage)
			, end = begin + $scope.numPerPage;
			$scope.filteredParticipantsResults = $scope.pendingReceiptList.slice(begin, end);
			$scope.totalItems =	$scope.pendingReceiptList.length;
			/*$scope.dates();*/
		});
		$scope.noOfPages = Math.ceil($scope.pendingReceiptList.length/$scope.maxSize);

		$scope.setPage = function(pageNo) {
			$scope.currentPage = pageNo;
		};

		/*$scope.filter = function() {
			$scope.$watch('search', function(term) {
				if(term==undefined){
					$scope.totalItems =	$scope.pendingReceiptList.length; 


				}
				window.setTimeout(function() {
					$scope.totalItems = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);

					$scope.noOfPages = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);
				}, 10);
			});

		}*/

		
	});
	
	
	
	
	
}



$scope.showBankReconcile = function(sheet){
	
	
	$rootScope.bankReconcile = sheet;
	
	$state.go("customer_payment");
	
	
	
}






}])

