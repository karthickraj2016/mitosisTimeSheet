'use strict';

angular.module('myApp.controllers')


.controller('bankReconcileController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;

	$scope.check = function(sheet){
		
		if(sheet.project.projectName == '' || sheet.project.projectName == undefined) {
			return true;
		} else if(sheet.invoiceNumber == '' || sheet.invoiceNumber == undefined) {
			return true;
		} else if(sheet.invoiceDate == '' || sheet.invoiceDate == undefined) {
			return true;
		}else if(sheet.invoiceAmount == '' || sheet.invoiceAmount == undefined) {
			return true;
		}else if(sheet.receiptNumber == '' || sheet.receiptNumber == undefined) {
			return true;
		}else if(sheet.receivedDate == '' || sheet.receivedDate == undefined) {
			return true;
		}else if(sheet.receivedAmount == '' || sheet.receivedAmount == undefined) {
			return true;
		}else if(sheet.exchangeRate == '' || sheet.exchangeRate == undefined) {
			return true;
		}else{
			return false;
		}
	}
	
	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',

			/* yearRange: '1900:-0'*/
	};
	
	$scope.dates = function() {
		$scope.payment = '';
		$scope.payment={};
		var dt = new Date();
		var dd = dt.getDate();
		var mm = dt.getMonth()+1; 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		$scope.payment.recievedDate=dt;			
	};
	
	$http({
		url: 'rest/payment/showCustomerlist',
		method: 'GET',
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {			
		console.log(result);		
		$scope.customerList=result;

	});


	$scope.customerChanged = function() {
		var cusid = angular.toJson({
			"customerId": $scope.customers.customerId
		});
		$http({
			url: 'rest/payment/projectList',
			method: 'POST',
			data:cusid,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {			
			console.log(result);		
			$scope.projectList=result;

		});

	}

	$scope.projectChanged = function() {
		var pid = angular.toJson({
			"projectId": $scope.projects.projectId
		});
		$http({
			url: 'rest/payment/invoiceList',
			method: 'POST',
			data:pid,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {			
			console.log(result);		
			$scope.invoiceList=result;

		});

	}

	$scope.invoiceChanged = function() {

		var ino = angular.toJson({
			"invoiceNumber": $scope.invoices.invoiceNumber
		});
		$http({
			url: 'rest/bankReconcile/getPaymentDetails',
			method: 'POST',
			data:ino,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			if(result.length==0){
				$scope.invoice="";
			    $scope.receiptDetails="";
			    $scope.paymentDetails="";
			}else{
			$scope.paymentDetails = result;
			$scope.receiptDetails="";
			$scope.invoice=result[0].invoiceHdr;
			}
			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.paymentDetails.slice(begin, end);
				$scope.totalItems =	$scope.paymentDetails.length;
				$scope.dates();
			});
			console.log(result);

		});

	}


	$scope.getReceiptDetails = function(){

		var menuJson = angular.toJson({
			"receiptNumber": $scope.receipt.receiptNumber
		});
		$http({
			url: 'rest/bankReconcile/getReceiptDetails',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.receiptDetails = result;
			console.log(result);
			$scope.id=result.id;

		});

	}
	
	$scope.calculateAmount = function(){
		
		$scope.payment.paidAmountInr=$scope.payment.receivedamount*$scope.payment.exchangerate;
	    $scope.finalAmount=$scope.payment.paidAmountInr-$scope.payment.commisionamount;
		
	}

	$scope.insertPaymentInfo = function(){

		var menuJson = angular.toJson({
			"id":$scope.id,
			"receiptNumber": $scope.receipt.receiptNumber,
			"receivedDate":$scope.payment.recievedDate,
			"receivedAmount":$scope.payment.receivedamount,
			"commisionAmount":$scope.payment.commisionamount,
			"exchangeRate":$scope.payment.exchangerate,
			"paidAmountInr":$scope.payment.paidAmountInr,
			"invoiceNumber":$scope.invoices.invoiceNumber,
			"paidAmount":$scope.invoices.paidAmount,
			"receiptDate":$scope.receiptDetails.receiptDateStr,
			"currencyCode":$scope.invoice.currencyCode,
			"finalAmount":$scope.finalAmount

		});

		$http({
			url: 'rest/bankReconcile/insertReconcile',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="inserted"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Inserted");
				$scope.id="";
				$scope.payment="";
				$scope.receiptDetails="";
				$scope.invoiceChanged();
				$scope.dates();
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Insertion Failed");
			}

		});

	}
	
   $scope.calculateRate = function(sheet){
		
	sheet.paidAmountInr=sheet.receivedAmount*sheet.exchangeRate;
	sheet.finalAmount=sheet.paidAmountInr-sheet.commisionAmount;
	
	}

	$scope.updatePaymentInfo = function(reqParam){

		var menuJson = angular.toJson({
			"id":reqParam.id,
			"receiptNumber": reqParam.receiptNumber,
			"receivedDate":reqParam.bankReceivedDateStr,
			"receivedAmount":reqParam.receivedAmount,
			"commisionAmount":reqParam.commisionAmount,
			"exchangeRate":reqParam.exchangeRate,
			"paidAmountInr":reqParam.paidAmountInr,
			"invoiceNumber":reqParam.invoiceHdr.invoiceNumber,
			"paidAmount":reqParam.paidAmount,
			"receiptDate":reqParam.receiptDateStr,
			"currencyCode":reqParam.currencyCode,
			"finalAmount":reqParam.finalAmount
		});

		$http({
			url: 'rest/bankReconcile/insertReconcile',
			method: 'POST',
			data:menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value=="inserted"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Updated");
				$scope.invoiceChanged();
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Updating Failed");
			}

		});

	}

}])