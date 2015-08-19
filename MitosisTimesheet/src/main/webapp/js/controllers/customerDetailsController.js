'use strict';

angular.module('myApp.controllers')


.controller('customerDetailsController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {


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
			$scope.noOfPages = Math.ceil($scope.customerlist.length/$scope.maxSize);

			$scope.setPage = function(pageNo) {
				$scope.currentPage = pageNo;
			};

			$scope.filter = function() {
				$scope.$watch('search', function(term) {
					if(term==undefined){
						$scope.totalItems =	$scope.customerlist.length; 


					}
					window.setTimeout(function() {
						$scope.totalItems = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);

						$scope.noOfPages = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);
					}, 10);
				});

			};

		});

		},
	

	$scope.nameValidation = function(customer){

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
				}else{
					$scope.mailValidation(customer);
				}
			})
		}
	},

	$scope.mailValidation = function(customer){

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
				}else{

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
					} else{
						$scope.addCustomerDetails(customer);
					}
				}
			})
		}else{
			$scope.addCustomerDetails(customer);
		}
	},

	$('#phoneNumber').blur(function(){
		var phone=$('#phoneNumber').val();
		if(phone.length>15){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Phone Number");
			$('#phoneNumber').val('');
			$('#phoneNumber').focus();
		}
	}),

	$('#mobileNumber').blur(function(){
		var phone=$('#mobileNumber').val();
		if(phone.length>10){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invalid Mobile Number");
			$('#mobileNumber').val('');
			$('#mobileNumber').focus();
		}
	}),

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
		
		if(mail==null){
			mail="";
		}else{
			mail=mail;
		}

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
		}else{

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
	}
	
/*
	$scope.searchRecords = function(customer){

		var searchValue={"customerName":$scope.customer.customerName,"email":$scope.customer.email,"address":$scope.customer.address,"skypeId":$scope.customer.skypeId,
				"phone":$scope.customer.phone,"mobile":$scope.customer.mobile,"website":$scope.customer.website,"fax":$scope.customer.fax,"status":$scope.customer.status};
		console.log(searchValue);
		var a=$scope.customerlist;
		var b=[];
		for(var i=0;i<a.length;i++){

			if(searchValue.customerName==a[i].customerName){
				b.push(a[i]);
			}else if(searchValue.email==a[i].email){
				b.push(a[i]);
			}else if(searchValue.address==a[i].address){
				b.push(a[i]);
			}else if(searchValue.skypeId==a[i].skypeId){
				b.push(a[i]);
			}else if(searchValue.phone==a[i].phone){
				b.push(a[i]);
			}else if(searchValue.mobile==a[i].mobile){
				b.push(a[i]);
			}else if(searchValue.website==a[i].website){
				b.push(a[i]);
			}else if(searchValue.fax==a[i].fax){
				b.push(a[i]);
			}else if(searchValue.status==a[i].status){
				b.push(a[i]);
			}
		}
		$scope.customerlist=b;
		b=undefined;

		$scope.$watch('currentPage + numPerPage', function() {
			var begin = (($scope.currentPage - 1) * $scope.numPerPage)
			, end = begin + $scope.numPerPage;
			$scope.filteredParticipantsResults = $scope.customerlist.slice(begin, end);
			$scope.totalItems =	$scope.customerlist.length;
		});
	}*/

}])