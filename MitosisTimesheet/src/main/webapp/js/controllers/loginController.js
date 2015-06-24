'use strict';

angular.module('myApp.controllers')


.controller('loginController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	/*if ( angular.isDefined( $localStorage.settings) ) {
			   $rootScope.app =  $localStorage.settings;
		      } */
	if($rootScope.passwordmsg){
	$(".alert-msg").show().delay(1500).fadeOut(); 
	$(".alert-success").html("Password has been changed successfully!!!");
	$rootScope.passwordmsg='';
	}
	else if($rootScope.passwordmsg==false){
		
		$(".alert-msg1").show().delay(2000).fadeOut(); 
		$(".alert-danger").html("your activation session is over!!!..please click reset password again");
	$rootScope.passwordmsg='';
		
		
	}
	
	$scope.login = function() {
		
		
		var menuJson = angular.toJson({
			"username": $scope.username,"password":$scope.password

		});
		
		
		console.log($scope.username); 
		
		$http({
			url: 'rest/account/login',
			method: 'POST',
			data: menuJson,
			headers:
			{
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.message == "notactivated"){
				$(".alert-msg1").show().delay(1500).fadeOut(); 
				$(".alert-danger").html("Click Your Activation Link In Your Mail")
			}else if(result.message == "success"){

				$state.go('timesheet')
			}else if(result.message == "Admin"){

				$state.go('userRights')
			}else if(result.message =="signupunsuccessful"){
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("UserName or password is incorrect !!!");
			}

		})
	}

}])