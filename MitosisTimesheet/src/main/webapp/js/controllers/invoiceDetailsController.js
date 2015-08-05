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


	$http({
		url: 'rest/timesheet/getUserDetails',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		$scope.manageFinance=result.manageFinance;
		$scope.manageProject=result.manageProject;
		$scope.manageTeam=result.manageTeam;
		$scope.manageCustomer=result.manageCustomer;
		$scope.manageEmployees=result.manageEmployees;
	});

	$scope.loadProjects=function()
	{
		var cusid = angular.toJson({
			"customerId": $scope.invoice.customer.customerId
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

	$scope.list = function(){

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


		/*	if($scope.member.billablehours==undefined || $scope.member.billablehours==''){

			$scope.member.amount="";

			return;

		}*/

		if($scope.member.rateperhour && $scope.member.billablehours==undefined||$scope.member.billablehours==""||isNaN($scope.member.billablehours)){

			$scope.member.rateperhour =parseFloat($scope.member.rateperhour);


			$scope.member.amount=parseFloat(1.0*$scope.member.rateperhour);
		}

		else {


			$scope.member.rateperhour =parseFloat($scope.member.rateperhour);

			$scope.member.billablehours = parseFloat($scope.member.billablehours);

			$scope.member.amount=parseFloat($scope.member.billablehours*$scope.member.rateperhour);


		}

	}

	$scope.amountCalForUpdate = function (sheet){

		console.log(sheet);

		if(sheet.rateperhour && sheet.billablehours==undefined||sheet.billablehours==""){

			sheet.rateperhour =parseFloat(sheet.rateperhour);


			sheet.amount=parseFloat(1.0*sheet.rateperhour);
		}

		else {


			sheet.rateperhour =parseFloat(sheet.rateperhour);

			sheet.billablehours = parseFloat(sheet.billablehours);

			sheet.amount=parseFloat(sheet.billablehours*sheet.rateperhour);


		}

	}




	$scope.projectBasedSelections = function(project){

		var menuJson = angular.toJson({"project":project});

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
			console.log($scope.projectTypeList);
			$scope.invoice.projectType =$scope.projectTypeList[0].projectType;
			$scope.invoice.currency=$scope.projectTypeList[0].currencyCode;

			for(var i=0;i<result[0].projectCostDetails.length;i++){

				if(angular.isUndefined($scope.invoice.teammembers)){
					$scope.invoice.teammembers= new Array();
				}

				var teammembers=[];

				teammembers={"member":result[0].projectCostDetails[i].employee};

				$scope.invoice.teammembers.push(teammembers);
			}

			console.log($scope.invoice.teammembers);



			/*for(i=0;i<result[0].projectCostDetails.length;i++){

				console.log(result[0]);
				$scope.invoice.teammembers=[];
			$scope.invoice.teammembers.push(result[0].projectCostDetails[i]);



			}

			console.log($scope.invoice.teammembers);*/

		});


		/*	$http({

			url: 'rest/invoiceDetails/getTeamMembers',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			console.log(result);

			$scope.invoice.teammembers=result;

		});*/

	}


	$scope.addTeamMember = function (){

		console.log($scope.member);
		
		if($scope.invoice.invoiceamt==undefined || isNaN($scope.invoice.invoiceamt) ||$scope.invoice.invoiceamt==""){
			
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the invoice amount");
			return;
			
			
		}



		if($scope.member.fromdate==undefined||$scope.member.todate==undefined){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details of from date and todate");
			return;
		}

		else if($scope.member.description==undefined||$scope.member.description==""){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for description");
			return;	


		}


		else if($scope.member.rateperhour==undefined || isNaN($scope.member.amount) ||$scope.member.rateperhour==""){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for rate");
			return;	

		}

		else if($scope.member.amount==undefined || isNaN($scope.member.amount)){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for amount");
			return;	

		}




		var fromdate = new Date($scope.member.fromdate.split('-')[2],$scope.member.fromdate.split('-')[1],$scope.member.fromdate.split('-')[0]);
		var todate = new Date($scope.member.todate.split('-')[2],$scope.member.todate.split('-')[1],$scope.member.todate.split('-')[0]);

		var datevalidationfromDate =new Date(($scope.member.fromdate).split("-")[1]+"-"+($scope.member.fromdate).split("-")[0]+"-"+($scope.member.fromdate).split("-")[2]);	
		var datevalidationtoDate = new Date(($scope.member.todate).split("-")[1]+"-"+($scope.member.todate).split("-")[0]+"-"+($scope.member.todate).split("-")[2]);;


		if (datevalidationfromDate > datevalidationtoDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			return;
		}
		else if(fromdate.getDay()===2 || fromdate.getDay()===3 || todate.getDay()===2 || todate.getDay()===3){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate or Todate cannot be on Saturdays or Sundays!!!!");
			return;

		}


		else{
			if(angular.isUndefined($scope.invoiceList)){
				$scope.invoiceList =[];
			}

			if($scope.member.billablehours==undefined){

				$scope.member.billablehours='';

			}

			if($scope.member.teamlist==undefined || $scope.member.teamlist==null || $scope.member.teamlist==""){

				if($scope.member.teamlist){
					$scope.member.teamlist.employee.name=null;
				}
			}

			else if($scope.member.teamlist.employee){
				invoice["teammember"]=$scope.member.teamlist.member.name;
			}

			invoice = {"fromdate":$scope.member.fromdate,"todate":$scope.member.todate,"description":$scope.member.description,"billablehours":$scope.member.billablehours,"amount":$scope.member.amount,"amount":$scope.member.amount,"index":$scope.iterator,"rateperhour":$scope.member.rateperhour};


			var copiedarray=[];
			$scope.invoiceList.push(invoice);
			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Member Details added successfully!!!!!");
			$scope.iterator++;
			$scope.member.fromdate='';
			$scope.member.todate='';
			$scope.member.teamlist='';
			$scope.member.description='';
			$scope.member.billablehours='';
			$scope.member.amount='';
			$scope.member.rateperhour='';



		}
	}

	$scope.updateteammembers= function(sheet){

		$scope.invoiceList[sheet.index]=sheet;
		$(".alert-msg").show().delay(1000).fadeOut(); 
		$(".alert-success").html("Member Details updated successfully!!!!!");

	}


	$scope.deleteteammembers = function(sheet){

		$scope.invoiceList.splice(sheet.index,1);
		$(".alert-msg").show().delay(1000).fadeOut(); 
		$(".alert-success").html("Member Details deleted Successfully!!!!");
	}

	$scope.teammemberslist= function(){
		$scope.invoiceList[invoice.index]=invoice;
	}

	$scope.insert = function(){
		var jsonstring=JSON.stringify($rootScope.invoiceList);	
		console.log($scope.invoice.customer+','+$scope.invoice.projectlist);
		var sc=Number($scope.sumCost());
		var val=true;
		if(sc>0)
		{
			console.log("sc greater than zero");
			if(Number($scope.invoice.invoiceamt)==sc)
			{val=true;}
			else{
				val=false;}
		}
		if(val){
			var menuJson = angular.toJson({
				"customer":$scope.invoice.customer,
				"invoicedate":$scope.invoice.invoicedate,
				"project":$scope.invoice.projectlist,
				"projecttype":$scope.invoice.projectType,
				"invoiceamt":$scope.invoice.invoiceamt,
				"currency":$scope.invoice.currency,
				"invoicelist":$scope.invoiceList,
				"currency":$scope.invoice.currency

			});
			$http({
				url: 'rest/invoiceDetails/insertInvoice',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {
				console.log("Result====>"+result+"  status==>"+status);
				if(result.value=="inserted"){
					$scope.invoice.invoiceno=result.invoicenumber;
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Invoice Details are successfully Inserted!!!!!");

					var a = document.createElement('a');
					a.href = "/MitosisTimesheet/reports/"+result.pdfFileName;
					console.log(a);
					//a.download = "individualDetailReport.pdf";
					a.target="_blank";
					document.body.appendChild(a);
					a.click();
					document.body.removeChild(a);
					$scope.filepath = a.href;
					console.log($scope.filepath);
					return;
				}
			});
		}
		else
		{
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Invoice Amount should be equal to the total amount of members !!!!");
		}
	}
	$scope.sumCost = function(){

		var totalSum= 0;
		try{
			for(var i = 0; i < $scope.invoiceList.length; i++){
				var revenue = parseInt($scope.invoiceList[i].amount);
				totalSum += revenue;
			}
		}
		catch(err)
		{
			$scope.invoiceList=[];
			totalSum=0;
		}
		console.log("Sum of amounts==>"+totalSum);
		return(totalSum);
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
