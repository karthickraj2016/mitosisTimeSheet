'use strict';

angular.module('myApp.controllers')


.controller('companyInfoController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {


	$scope.list = function(){
		$http({
			url: 'rest/company/showCompany',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			$scope.comp={};
			if(result==""){
				console.log("====>No Result Found");
			}else{
				console.log("Result====>"+result.companyName);
				$scope.comp.companyName=result.companyName;
				$scope.comp.companyAddress=result.companyAddress;
				$scope.comp.phoneNumber=result.phoneNumber;
				$scope.comp.mobileNumber=result.mobileNumber;
				$scope.comp.faxNo=result.faxNo;
				$scope.comp.accountNumber=result.accountNumber;
				$scope.comp.bankName=result.bankName;
				$scope.comp.bankAddress=result.bankAddress;
				$scope.comp.ifscCode=result.ifscCode;
				$scope.comp.micrCode=result.micrCode;
				$scope.comp.swiftCode=result.swiftCode;
				$scope.comp.logo="data:image/jpeg;base64,"+result.logo;
				$scope.oldlogo=result.logo;
				$scope.comp.id=result.id;

			}

		});

	}

	$rootScope.handleFileSelect = function(evt) {
		var files = evt.files;
		var file = files[0];
		if(file.type == "image/png"){
			if (files && file) {
				var reader = new FileReader();
				reader.onload = function(readerEvt) {
					var binaryString = readerEvt.target.result;
					$rootScope.base64Content = btoa(binaryString);
					$scope.res=btoa(binaryString);
					$scope.comp.logo="data:image/jpeg;base64,"+btoa(binaryString);
					$scope.$apply();
				};
				reader.readAsBinaryString(file);
			}
		}else {
			alert("Import image with .png format");
			$('#img1').val('');
		}
	} 


	$scope.addCompany = function(){
		if($scope.res==null){
			$scope.res=$scope.oldlogo;}
		if($scope.comp.phoneNumber==null){
			$scope.comp.phoneNumber="";}
		if($scope.comp.mobileNumber==null){
			$scope.comp.mobileNumber="";}
		if($scope.comp.faxNo==null){
			$scope.comp.faxNo="";}
		var menuJson=angular.toJson({"id":$scope.comp.id,"companyName":$scope.comp.companyName,"companyAddress":$scope.comp.companyAddress,"phoneNumber":$scope.comp.phoneNumber,"mobileNumber":$scope.comp.mobileNumber,"logo":$scope.res,"faxNo":$scope.comp.faxNo,"accountNumber":$scope.comp.accountNumber,"bankName":$scope.comp.bankName,"bankAddress":$scope.comp.bankAddress,"ifscCode":$scope.comp.ifscCode,"micrCode":$scope.comp.micrCode,"swiftCode":$scope.comp.swiftCode});
		$http({
			url: 'rest/company/addcompany',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			console.log("Result==>"+result);
			if(result=="true"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Company Details Added Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Process Failed");
			}
			$scope.list();
			$(".span-profile").show();
			$(".edit-profile").hide();
			$(".edit-profile-img").hide();
			$('.btn-profile-edit').show();	
			$('.btn-profile-save').hide();
			$('.btn-profile-cancel').hide();
		})
	}
	$scope.logout = function(){

		$http({
			url: 'rest/account/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		})
	};



}])