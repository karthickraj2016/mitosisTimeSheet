'use strict';

angular.module('myApp.controllers')


.controller('customerDetailsController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$http({
		url: 'rest/account/getName',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {
		if(result==""){
			$state.go('login')

		}else{
			$scope.name=result;
		}
	});

	$http({
		url: 'rest/timesheet/getUserDetails',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		$scope.manageProject=result.manageProject;
		$scope.manageTeam=result.manageTeam;
		$scope.manageCustomer=result.manageCustomer;
		$scope.accessRights();
	});

	$scope.accessRights=function(){

		if(!$scope.manageCustomer){

			$state.go('timesheet')
		}

	};

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;

	$scope.check = function(sheet){
		if(sheet.customerName == '' || sheet.customerName == undefined){
			return true;
		} else if(sheet.email == '' || sheet.email == undefined) {
			return true;
		} else if(sheet.address == '' || sheet.address == undefined) {
			return true;
		} else if(sheet.skypeId == '' || sheet.skypeId == undefined) {
			return true;
		}else if(sheet.phone == '' || sheet.phone == undefined) {
			return true;
		}else if(sheet.mobile == '' || sheet.mobile == undefined) {
			return true;
		}else if(sheet.website == '' || sheet.website == undefined) {
			return true;
		}else if(sheet.status == '' || sheet.status == undefined) {
			return true;
		}else{
			return false;
		}
	},

	$scope.list = function() {

		$http({
			url: 'rest/customerDetails/showCustomerlist',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.customerlist=result; 

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.customerlist.slice(begin, end);
				$scope.totalItems =	$scope.customerlist.length;
			});

		})
	},

	$scope.nameValidation = function(){

		var name=$('#customerName').val();
		
		if(name!=""){

			var menuJson=angular.toJson({"name":name});
			console.log(menuJson);

			$http({
				url: 'rest/customerDetails/nameValidation',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="Already Exist"){
					$('#customerName').val('');
					$('#customerName').focus();
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Name Already Exists");
				}
			})
		}
	},
			
	$scope.mailValidation = function(){

		var mail=$('#customerMail').val();
		
		if(mail!=""){

			var menuJson=angular.toJson({"mail":mail});
			console.log(menuJson);

			$http({
				url: 'rest/customerDetails/mailValidation',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="Already Exist"){
					$('#customerMail').val('');
					$('#customerMail').focus();
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("MailId Already Exists");
				}

				$scope.validateEmail = function(mail) {

					var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
					return emailReg.test( mail );
				}

				var lstIndex = mail.lastIndexOf('@');
				if( !$scope.validateEmail(mail)) {
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("EmailId is not valid");
					$('#customerMail').val('');
					$('#customerMail').focus();
					return;
				} /*else if(mail.substring(lstIndex + 1)!='.com') {
				$(".alert-msg1").show().delay(1500).fadeOut(); 
				$(".alert-danger").html("Invalid domain name, use only mitosistech.com");
				return;
			}*/
			})
		}
	}

	$('#phoneNumber').blur(function(){
		var phone=$('#phoneNumber').val();
		if(phone.length>15){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Phone Number");
			$('#phoneNumber').val('');
			$('#phoneNumber').focus();
		}
	})
	
	$('#mobileNumber').blur(function(){
		var phone=$('#mobileNumber').val();
		if(phone.length>10){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Mobile Number");
			$('#mobileNumber').val('');
			$('#mobileNumber').focus();
		}
	})

	$scope.addCustomerDetails = function(customer){

		var menuJson=angular.toJson({"customerName":$scope.customer.customerName,"email":$scope.customer.email,"address":$scope.customer.address,"skypeId":$scope.customer.skypeId,
			"phone":$scope.customer.phone,"mobile":$scope.customer.mobile,"website":$scope.customer.website,"fax":$scope.customer.fax,"status":$scope.customer.status});

		$http({
			url: 'rest/customerDetails/addCustomer',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="inserted"){
				$scope.customer='';
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Customer Detail added Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Customer Detail adding Failed");
			}
			$scope.list();	
		})

	},

	$scope.updateCustomerDetails = function(reqParam){
		
		var mail=reqParam.email;
		
		$scope.validateEmail = function(mail) {

			var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
			return emailReg.test( mail );
		}

		var lstIndex = mail.lastIndexOf('@');
		if( !$scope.validateEmail(mail)) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("EmailId is not valid");
			$scope.list();
			return;
		}
		else{

		$http({
			url: 'rest/customerDetails/updateCustomer',
			method: 'POST',
			data: reqParam,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="updated"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Customer Detail Updated Successfully");
			}
			else if(result.value=="InActive"){
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Some Projects for this customer is open...Please Kindly close and then change Status");
				
				
			}
			else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Customer Detail updating Failed");
			}
			$scope.list();	
		});
		}

	},

	$scope.confirmDelete = function(customerId){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteCustomerDetails(customerId);
		}
	},

	$scope.deleteCustomerDetails = function(customerId){

		$http({
			url: 'rest/customerDetails/removeCustomer',
			method: 'POST',
			data: {"customerId":customerId},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="deleted"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Customer Detail Deleted Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Customer Detail Delete Failed");
			}
			$scope.list();	
		})
	},

	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	};

}])