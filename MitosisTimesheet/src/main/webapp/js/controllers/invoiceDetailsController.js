angular.module('myApp.controllers')

.controller('invoiceDetailsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	
   	$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
 	$scope.iterator=0;
	
   	var hoursallowed;
   	
   	
	var invoice = [];
   	
   	

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
	     $scope.member = '';
		 $scope.member={};
		 $scope.invoice='';
		 $scope.invoice={};
		 
		 
		 var dt = new Date();
		    var dd =dt.getDate();
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
		 $scope.member.fromdate=dt1;	
		 $scope.member.todate=dt2;
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


	}
	
	
	$scope.amountCalForinsert = function(){
	
		
		$scope.member.rateperhour =parseFloat($scope.member.rateperhour);
		
		$scope.member.billablehours = parseFloat($scope.member.billablehours);
		
		$scope.member.amount=parseFloat($scope.member.billablehours*$scope.member.rateperhour);
		

	}
	
	$scope.amountCalForUpdate = function (sheet){
		
		sheet.rateperhour =parseFloat(sheet.rateperhour);
		
		sheet.billablehours = parseFloat(sheet.billablehours);
		
		sheet.amount = parseFloat(sheet.billablehours*sheet.rateperhour);
		
	}
	
	
	
	
	$scope.projectBasedSelections = function(projectId){
		
		var menuJson = angular.toJson({"projectId":projectId});
		
		$scope.invoice.projectType="";
		

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

		
		$http({

			url: 'rest/invoiceDetails/getTeamMembers',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			console.log(result);

			$scope.invoice.teammembers=result;

		});
		
	}
	
	
	$scope.addTeamMember = function (){
		
		
			if($scope.member==undefined||$scope.member.fromdate==undefined||$scope.member.todate==undefined||$scope.member.description==undefined||$scope.member.billablehours==undefined||$scope.member.teamlist==undefined||$scope.member.amount==undefined){
			
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("please enter all the above member details");
				return;
		}
			else if($scope.member.fromdate>$scope.member.todate){
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("From date cannot be greater than To date!!!!");
				return;
				
				
				
			}
		
		
		if(angular.isUndefined($scope.invoiceList)){
			$scope.invoiceList =[];
		}
	

		invoice = {"fromdate":$scope.member.fromdate,"todate":$scope.member.todate,"description":$scope.member.description,"billablehours":$scope.member.billablehours,"amount":$scope.member.amount,"teammember":$scope.member.teamlist.employee.name,"amount":$scope.member.amount,"index":$scope.iterator,"rateperhour":$scope.member.rateperhour};
		
		
		var copiedarray=[];
		console.log();
		$scope.invoiceList.push(invoice);
		$(".alert-msg1").show().delay(1000).fadeOut(); 
		$(".alert-danger").html("Member Details added successfully!!!!!");
		$scope.iterator++;
		$scope.member.fromdate='';
		$scope.member.todate='';
		$scope.member.teamlist='';
		$scope.member.description='';
		$scope.member.billablehours='';
		$scope.member.amount='';
		
		
		
	}
	
	$scope.updateteammembers= function(sheet){

		$scope.invoiceList[sheet.index]=sheet;
		$(".alert-msg1").show().delay(1000).fadeOut(); 
		$(".alert-danger").html("Member Details updated successfully!!!!!");
		
	}
	
	
	$scope.deleteteammembers = function(sheet){
			
		$scope.invoiceList.splice(sheet.index,1);
		$(".alert-msg1").show().delay(1000).fadeOut(); 
		$(".alert-danger").html("Member Details deleted Successfully!!!!");
	

	}
	
	$scope.teammemberslist= function(){
		
		
		$scope.invoiceList[invoice.index]=invoice;
		
	}

	
	$scope.insert = function(){
		

		var jsonstring=JSON.stringify($rootScope.invoiceList);
		console.log(jsonstring);
		
		console.log($scope.invoice.customer+','+$scope.invoice.projectlist);
		
		
		var menuJson = angular.toJson({
			"customerid":$scope.invoice.customer.customerId,
			"invoicedate":$scope.invoice.invoicedate,
			"projectid":$scope.invoice.projectlist,
			"projecttype":$scope.invoice.projectType,
			"invoiceamt":$scope.invoice.invoiceamt,
			"currency":$scope.invoice.currency,
			"invoicelist":$scope.invoiceList

			});
		
		
		$http({

			url: 'rest/invoiceDetails/insertInvoice',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			
			
			if(result.value=="inserted"){
				
				$scope.invoice.invoiceno=result.invoicenumber;
				
				
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Invoice Details are successfully Inserted!!!!!");
				return;
			}
			
			/*if(result.pdfPath=="norecords"){
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("No records availiable");
				return;
					
				}
			
			else{
			
			var a = document.createElement('a');
			 a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
			console.log(a);
			a.blank = "InvoiceReport.pdf";
			a.target="_blank";
			 document.body.appendChild(a);
		        a.click();
		        document.body.removeChild(a);
		    $scope.filepath = a.href;
		        console.log($scope.filepath);
		        //$scope.deletepdfFile(result.pdfPath);
			}*/

		
		});
		
		
		
		
		
	}
	
	$scope.logout = function(){

		$http({
			url: 'rest/individualreport/logout',
			method: 'GET',
		}).success(function(result, status, headers) {

			$state.go('login')
		});
	}

	
	


}])
