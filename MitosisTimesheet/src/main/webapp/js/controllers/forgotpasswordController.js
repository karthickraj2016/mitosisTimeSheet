'use strict';

angular.module('myApp.controllers')


.controller('forgotpasswordController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	/*if ( angular.isDefined( $localStorage.settings) ) {
			   $rootScope.app =  $localStorage.settings;
		      } */
	$scope.loader= false;
	$scope.passwordhide = true;
	$rootScope.blocker='none';
	$rootScope.isReset;

	$scope.mailvalidation = function(){

		var emailId = $scope.resetemailid;

		var email = angular.toJson({
			"emailid": $scope.resetemailid

		});

		$scope.validateEmail = function($email) {

			var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			return emailReg.test( $email );
		}

		var lstIndex = emailId.lastIndexOf('@');
		if( !$scope.validateEmail(emailId)) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("EmailId is not valid");
			return;
		} else if(emailId.substring(lstIndex + 1)!='mitosistech.com') {
			$(".alert-msg1").show().delay(1500).fadeOut(); 
			$(".alert-danger").html("Invalid domain name, use only mitosistech.com");
			return;
		}

		$http({
			url: 'rest/forgotpassword/mailvalidation',
			method: 'POST',
			data: email,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.eMail==null){

				$(".alert-msg1").show().delay(2000).fadeOut(); 
				$(".alert-danger").html("please Signup and then set your password");
				$('#auth_container').css("background",'#C9D4D7');
				$('#auth_container').css("border-bottom",'2px solid #ccc');
				$state.go('login');
				return;
			}else{

				$scope.forgotpassword();
			}
		});
	}

	$scope.forgotpassword = function() {

		$rootScope.isReset = 'Y';
		var menuJson = angular.toJson({
			"emailid": $scope.resetemailid,"passwordflag":$rootScope.isReset

		});
		$scope.loader = true;
		$scope.passwordhide = false;
		$('#auth_container').css("background",'none');
		$('#auth_container').css("border-bottom",'none');
		$rootScope.blocker='block';

		$http({
			url: 'rest/forgotpassword/changepassword',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.eMail!=null){

				$scope.loader= false;
				$scope.passwordhide = true;		
				$rootScope.blocker='none';

				$(".alert-msg").show().delay(2000).fadeOut(); 
				$(".alert-success").html("Please check the resetpassword link in your mail id to reset your password");
				$('#auth_container').css("background",'#C9D4D7');
				$('#auth_container').css("border-bottom",'2px solid #ccc');
				$state.go('login');

			}

		});
	}

	$scope.resetpassword = function(){

		$scope.resetpassword=true;
		var url = document.URL;
		var index = url.substring(url.lastIndexOf('?') + 1);
		var id = parseInt(index.slice(3));

		$scope.id=id;

		console.log($rootScope.isReset);

		var menuJson = angular.toJson({
			"id": $scope.id,"password":$scope.newpassword, "resetpasswordflag" : $rootScope.isReset

		});

		if($scope.newpassword==$scope.confirmpassword){
			$http({
				url: 'rest/forgotpassword/resetpassword',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				console.log(result);

				if(result=="false"){
					$rootScope.passwordmsg = false;	
					$state.go('login');
					return;

				}else{

					$rootScope.passwordmsg = true;	
					$state.go('login');
					return;
				}
			})

		}else{
			$(".alert-msg1").show().delay(2000).fadeOut(); 
			$(".alert-danger").html("new password and confirm password is not matched...!!!");
		}
	}
}])