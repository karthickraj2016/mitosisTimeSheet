'use strict';

angular.module('myApp.controllers')

.controller('customerpaymentController', ['$scope', '$http', '$state','$rootScope','$localStorage', function($scope, $http, $state, $rootScope,$localStorage) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	$scope.checkRequired = function(sheet){
		if(sheet.entryDate == '' || sheet.entryDate == undefined){
			return true;
		} else if(sheet.hours == '' || sheet.hours == undefined) {
			return true;
		} else if(sheet.hours == '' || sheet.hours == undefined) {
			return true;
		} else if(sheet.description == '' || sheet.description == undefined) {
			return true;
		} else{
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
		$scope.payment.receiptDate=dt;			
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
		$scope.invoice={};
		var ino = angular.toJson({
			"invoiceNumber": $scope.invoices.invoiceNumber
		});
		$http({
			url: 'rest/payment/invoiceHdr',
			method: 'POST',
			data:ino,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {			
			console.log(result);
			$scope.invoice.date=result.invoiceDateStr;
			$scope.invoice.status=result.invoiceStatus;
			$scope.invoice.invoiceAmount=result.invoiceAmount;
			$scope.invoice.paidAmount=result.paidAmount;
			$scope.invoice.balanceAmount=result.balanceAmount;
			$scope.invoice.currencyCode=result.currencyCode;
		});

	}
	$scope.addPayment = function(){
		var payment = angular.toJson({
			"invoiceNumber": $scope.invoices.invoiceNumber,"currencyCode":$scope.invoice.currencyCode,"receiptDate":$scope.payment.receiptDate,"receiptNumber":$scope.payment.receiptNumber,"paidAmount":$scope.payment.paidAmount
		});
		$http({
			url: 'rest/payment/addPayment',
			method: 'POST',
			data:payment
		}).success(function(result, status, headers) {
			if(result="true"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Receipt Added Successfully");
			}
			$scope.payment.receiptNumber="";
			$scope.payment.paidAmount="";
			$scope.payment.receiptDate="";
			$scope.invoiceChanged();
			$scope.list();
		})
	}
	$scope.list = function() {

		$http({
			url: 'rest/payment/showPaymentlist',
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			$scope.paymentList=result; 
			console.log("result",result);
			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.paymentList.slice(begin, end);
				$scope.totalItems =	$scope.paymentList.length;
			});
			$scope.dates();
		})
	}


	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteReceipt(id);
		}
	},

	$scope.deleteReceipt = function(id){

		$http({
			url: 'rest/payment/removePayment',
			method: 'POST',
			data:{"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result.value="success"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Receipt Deleted Successfully");
				$scope.list();			
			}
		})
	}

	$scope.updateReceipt =function(reqParam){

		var payment = angular.toJson({"id":reqParam.id,
			"invoiceNumber": reqParam.invoiceHdr.invoiceNumber,"currencyCode":reqParam.currencyCode,"receiptDate":reqParam.receiptDateStr,"receiptNumber":reqParam.receiptNumber,"paidAmount":reqParam.paidAmount
		});

		$http({
			url: 'rest/payment/addPayment',
			method: 'POST',
			data:payment,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=="true"){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Receipt Updated Successfully");
				$scope.list();			
			}
		})

	}

	$scope.zerocheck = function(){
		if($scope.payment.paidAmount=="0")
		{
			$scope.payment.paidAmount="";
		}
	}


	$scope.receiptCheck = function(){
		var rno = angular.toJson({
			"receiptNumber": $scope.payment.receiptNumber
		});
		$http({
			url: 'rest/payment/checkReceipt',
			method: 'POST',
			data:rno
		}).success(function(result, status, headers) {
			console.log("Result==>"+result);
			if(result!="true")
			{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("ReceiptNumber already exists");
				$scope.payment.receiptNumber="";
			$('#repNum').focus();
			}
		})
	}
}])