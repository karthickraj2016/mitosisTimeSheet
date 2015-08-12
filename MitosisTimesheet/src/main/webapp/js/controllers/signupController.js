'use strict';

angular.module('myApp.controllers')

.controller('signupController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.loader=false;
	$scope.signup=true;
	$rootScope.blocker='none';
	/*$scope.loding=none;*/

	$('#username').blur(function(){
		var username=$('#username').val();
		if(username!=""){

			var menuJson=angular.toJson({"username":$scope.username});
			console.log(menuJson);

			$http({
				url: 'rest/account/nameValidation',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result=="false"){
					$('#username').val('');
					$('#username').focus();
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Username already Exists");
				}
			})
		}
	});

	$('#email').blur(function(){

		var email=$('#email').val();

		if(email!=""){

			var menuJson=angular.toJson({"email":$scope.email});
			console.log(menuJson);

			$http({
				url: 'rest/account/mailValidation',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result=="false"){
					$('#email').val('');
					$('#email').focus();

					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("MailId Already Exists");
				}
			})

		}  

		else  
		{  
			$(".alert-msg1").show().delay(1500).fadeOut(); 
			$(".alert-danger").html("You have entered an invalid email address!");   
			return false;  
		}
	});

	//$scope.user = {};
	$scope.signup = function() {
		// Try to login

		if($scope.email!=$scope.username){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("EmailId and Username should be same");
			return;
		}

		var emailId = $scope.email;

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


		if(!($scope.password.indexOf(" ")<0)){
			$(".alert-msg1").show().delay(1500).fadeOut(); 
			$(".alert-danger").html("Invalid Password....clear spaces in password");
			return;

		}

		$scope.loader=true;
		$scope.signup=false;
		$('#auth_container').css("background",'none');
		$('#auth_container').css("border-bottom",'none');
		$rootScope.blocker='block';

		var menuJson = angular.toJson({
			"name": $scope.name,"username":$scope.username,"password":$scope.password,"email":$scope.email
		});


		$http({
			url: 'rest/account/signup',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.loader=false;
			$scope.signup=false;
			$rootScope.blocker='none';

			$(".alert-msg").show().delay(2000).fadeOut(); 
			$(".alert-success").html("Signup SuccessFull...Please Click the Activation Link in Your Mail");
			$('#auth_container').css("background",'#C9D4D7');
			$('#auth_container').css("border-bottom",'2px solid #ccc');
			$('#signin').click();

		})

	};
}])