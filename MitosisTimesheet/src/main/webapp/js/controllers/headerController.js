angular.module('myApp.controllers')


.controller('headerController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.homebutton = function (){

		$http({
			url: 'rest/headercontrols/homebutton',
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.adminFlag==2){
				$state.go('leaveDetails');

			}else if(result.adminFlag==1){

				$state.go('userRights');

			}else{

				$state.go('timesheet');
			}
		});
	}

	function HeaderController($scope, $location) 
	{ 
		$scope.isActive = function (viewLocation) { 
			return viewLocation === $location.path();
		};
	}

}]);