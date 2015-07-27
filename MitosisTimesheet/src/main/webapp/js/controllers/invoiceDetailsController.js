angular.module('myApp.controllers')

.controller('invoiceDetailsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	
   	$scope.invoicelist = new Array()
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
 	$scope.iterator=0;
	
   	var hoursallowed;
   	
   	 $scope.invoicedetails = {
   		    invoice: []
   		};

	$scope.checkRequired = function(sheet){
		if(sheet.entryDate == '' || sheet.entryDate == undefined){
			return true;
		} else if(sheet.hours == '' || sheet.hours == undefined) {
			return true;
		} else if(sheet.amount == '' || sheet.amount == undefined) {
			return true;
		} else if(sheet.description == '' || sheet.description == undefined) {
			return true;
		}
		else if(sheet.billablehours == ''|| sheet.billablehours == undefined){
			return true;
	}
		else{
			return false;
		}
		
	}
	
	$scope.dates = function() {
	     $scope.invoice = '';
		 $scope.invoice={};
		 
		 var dt = new Date();
		    var dd = dt.getDate();
		    var mm = dt.getMonth()+1; 
		    var yyyy = dt.getFullYear();
		    dt=dd+"-"+mm+"-"+yyyy;
		    var dt1 = new Date();
		    var dd1 = dt1.getDate();
		    var mm1 = dt1.getMonth()+1; 
		    var yyyy1 = dt1.getFullYear();
		    dt1=dd1+"-"+mm1+"-"+yyyy1;
		    var dt2= new Date();
		    var dd2 = dt2.getDate();
		    var mm2 = dt2.getMonth()+1;
		    var yyyy2 = dt2.getFullYear();
		    dt2=dd2+"-"+mm2+"-"+yyyy2;
		    $scope.invoice.invoicedate=dt;
		 $scope.invoice.fromdate=dt1;	
		 $scope.invoice.todate=dt2;
	};

	
	$scope.dateOptions = {
			changeYear: true,
			changeMonth: true,
			maxDate:'0d',
			/*minDate:-30,*/
			dateFormat:'dd-mm-yy'

			/* yearRange: '1900:-0'*/
	};
	
	
	
	
	$scope.list = function(){
		$http({

			url: 'rest/invoiceDetails/getProjectList',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			console.log(result);

			$scope.projectList=result;
		});



		$http({

			url: 'rest/invoiceDetails/getCustomerList',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			console.log(result);

			$scope.customerList=result;
		});

		

		
		$http({

			url: 'rest/invoiceDetails/getTeamMembers',
			method: 'GET',
			/*data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			console.log(result);

			$scope.teammembers=result;
		});



	}
	
	
	$scope.projectType = function(projectId){
		
		var menuJson = angular.toJson({"projectId":projectId});
		
		
		

		$http({

			url: 'rest/invoiceDetails/getProjectTypeList',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
		
			
			$scope.projectTypeList=result;
			$scope.invoice.projectType =$scope.projectTypeList[0].projectType;
		});

		
	}
	
	
$('#addentry').click(function(){
	
	
console.log($scope.invoice);
$scope.invoicelist.push($scope.invoice);

	

	
	
	
});
	

	
	$scope.insert = function(){
		
		
		
		
		var menuJson = angular.toJson({
			"customerid":$scope.employee.customerId,
			"invoicedate":$scope.invoice.invoicedate,
			"projectid":$scope.projectlist.projectId,
			"projecttype":$scope.projecttype.id,
			"invoiceamt":$scope.invoiceamount,
			"invoicefromdate":$scope.invoice.fromdate,
			"invoicetodate":$scope.invoice.todate,
			"project":$scope.projectlist.projectId,
			"description":$scope.description,
			"billablehours":$scope.billablehours,
			"teammember":$scope.teammember.member.name,
			"amount":$scope.amount
			

			});
		
		
		$http({

			url: 'rest/invoiceDetails/insertInvoice',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

		console.log(result);
		});
		
		
		
		
		
	}
	
	


}])
