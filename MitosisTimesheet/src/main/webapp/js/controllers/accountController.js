'use strict';

angular.module('myApp.controllers')


.controller('accountController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

    $scope.accountList = function(){
    	
    	$http({
    		url: 'rest/accountdetails/getaccountdetails',
    		method: 'GET',
    		headers: {
    			'Content-Type': 'application/json'
    		}
    	}).success(function(result, status, headers) {
    		
    		console.log(result);
    		
    		$scope.name=result.name;
    		$scope.email=result.eMail;
    		$scope.username = result.userName;
    		
    		
    	});
    	
    	
    },
    
    $scope.changepassword = function(){
    	
    	$scope.currentpassword=$('#password').val();
    	$scope.newpassword=$('#newpassword').val();
    	$scope.confirmpassword =$('#confirmpassword').val();
    	
    	
    	
    	
    	var menuJson = angular.toJson({
			"currentpassword": $scope.currentpassword,"newpassword":$scope.newpassword

		});
    	if($scope.newpassword!=$scope.confirmpassword){
    		
    		
    		$(".alert-msg1").show().delay(1500).fadeOut(); 
			$(".alert-danger").html("New password and Confirm password is not matched!!!!");
			return;
    		
    		
    	}
    	else{
    		
    		$http({
    			url: 'rest/accountdetails/changepassword',
    			method: 'POST',
    			data: menuJson,
    			headers: {
        			'Content-Type': 'application/json'
        		}
    		}).success(function(result, status, headers) {
    			
    			if(result.msg=="error_currentpassword"){
    				
    				$(".alert-msg1").show().delay(1500).fadeOut(); 
    				$(".alert-danger").html("Current password is wrong!!!");
    				$('#password').empty();
    				$('#newpassword').empty();
    				$('#confirmpassword').empty();
    				
    			}
    			else if(result.msg=="updated"){

    			$(".alert-msg").show().delay(2000).fadeOut(); 
    			$(".alert-success").html("New password has been changed successfully!!!");
    			$('#password').empty();
				$('#newpassword').empty();
				$('#confirmpassword').empty();
    			}
    		})
    		
    		
    		
    		
    	}
    	
    	
    },
    
   
    $scope.edituserdetails = function(){
    	
    	var menuJson = angular.toJson({
			"name": $scope.name,"email":$scope.email,"username":$scope.username

		});
    	
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
		
    	
    	
    	$http({
    		url: 'rest/accountdetails/editdetails',
    		method: 'POST',
    		data:menuJson,
    		headers: {
    			'Content-Type': 'application/json'
    		}
    	}).success(function(result, status, headers) {
    		

			 if(result.msg=="updated"){

			 
			$(".span-profile").show();
			$(".edit-profile").hide();
			$('.btn-profile-edit').show();	
			$('.btn-profile-save').hide();
			$('.btn-profile-cancel').hide();
			$(".alert-msg").show().delay(2000).fadeOut();
			$(".alert-success").html("your Account details has been recorded successfully!!!");
		
			}
			 else if(result.msg=="updationerror"){
					
					$(".alert-msg1").show().delay(1500).fadeOut(); 
					$(".alert-danger").html("updation Error!!");
				}
				
    		
    		
    	});
    	
    	
    	
    	
    	
    	
    },
    
    
   $scope.cancel = function(){
    	
	   $(".span-profile").show();
	 	$(".edit-profile").hide();
	 	$('.btn-profile-edit').show();	
	 	$('.btn-profile-save').hide();
	 	$('.btn-profile-cancel').hide();
	   $scope.accountList();	
    	
    	
    	
    },
    
    $scope.logout = function(){

		$http({
			url: 'rest/accountdetails/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	}
	
}])