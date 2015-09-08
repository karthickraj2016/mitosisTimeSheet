angular.module('myApp.controllers')

.controller('invoiceDetailsController', ['$scope', '$http', '$state','$rootScope', function($scope, $http, $state, $rootScope) {

	$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	$scope.iterator=0;

	var hoursallowed;
	var memberobj;
	var previoussheet;
	
	 var index=0;

	var invoice= new Array();
	var dt1;
	var dt2;

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
		dt1 = new Date();

		var dd1 = dt1.getDate();
		var mm1 = dt1.getMonth()+1; 
		var yyyy1 = dt1.getFullYear();
		dt1=dd1+"-"+mm1+"-"+yyyy1;
		dt2= new Date();

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

	$scope.loadProjects=function()
	{
		var cusid = angular.toJson({
			"customerId": $scope.invoice.customer.customerId
		});

		$scope.invoice.invoiceno="";
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
	

		if($scope.member.rateperhour && $scope.member.billablehours==undefined || $scope.member.billablehours=="" || isNaN($scope.member.billablehours)){
			
		
			index =$scope.member.rateperhour.toString().length-1;
			
			
		 var lastnum = $scope.member.rateperhour.toString().slice(index, $scope.member.rateperhour.length);
		 

		 
	


		 if(lastnum=="."){
			 
			 
			 $scope.member.rateperhour =$scope.member.rateperhour;
			 
			 $scope.member.amount = $scope.member.rateperhour+0.00;
			 
			 
		 }
		 
		 else{

				$scope.member.rateperhour =parseFloat($scope.member.rateperhour);


				$scope.member.amount=parseFloat((1.0*$scope.member.rateperhour).toFixed(2));

			 
		 }
		
			
			

		}	else {

			$scope.member.rateperhour =parseFloat($scope.member.rateperhour);

			$scope.member.billablehours = parseFloat($scope.member.billablehours);

			$scope.member.amount=parseFloat($scope.member.billablehours*$scope.member.rateperhour).toFixed(2);
		}
		
		
		 if($scope.invoiceList==undefined || $scope.invoiceList== "" || $scope.invoiceList.length<0){
			 
			 
			 $scope.invoice.invoiceamt = $scope.member.amount * 1;
			 $scope.invoice.invoiceamt =  $scope.invoice.invoiceamt.toFixed(2);
			 
			 
		 }
		 else  {
			 
			 if($scope.member.billablehours==""){
				 
				 $scope.member.amount=parseFloat(1*$scope.member.rateperhour).toFixed(2);
				 
				 var total=0;
				 
				 for(i=0;i<$scope.invoiceList.length;i++){
					 
					 total+= parseFloat($scope.invoiceList[i].amount);
					 
					
				 }
				 
				 $scope.invoice.invoiceamt = total+ parseFloat($scope.member.amount);
				 $scope.invoice.invoiceamt =  $scope.invoice.invoiceamt.toFixed(2);
				 
				 
			 }
			 
			 else{
			 
			 $scope.member.amount=parseFloat($scope.member.billablehours*$scope.member.rateperhour).toFixed(2);
			
			 
			 console.log($scope.invoice.invoiceamt);
			 
			 var total=0;
			 
			 for(i=0;i<$scope.invoiceList.length;i++){
				 
				 total+= parseFloat($scope.invoiceList[i].amount);
				 
				
			 }
			 
			 $scope.invoice.invoiceamt = total+ parseFloat($scope.member.amount);
			 $scope.invoice.invoiceamt =  $scope.invoice.invoiceamt.toFixed(2);

		 }
		 }
		 
		 if(isNaN($scope.invoice.invoiceamt)){
				
				$scope.invoice.invoiceamt = undefined;
				
			}
		 
		 if(isNaN($scope.member.amount)){
			 
			 $scope.member.amount = undefined;
			 
		 }
		
		
	}

	$scope.amountCalForUpdate = function (sheet){

		console.log(sheet);
		
		
		index =sheet.rateperhour.toString().length-1;
		
		
		 var lastnum = sheet.rateperhour.toString().slice(index, sheet.rateperhour.length);
		 
		 

		

		if(sheet.rateperhour && sheet.billablehours==undefined||sheet.billablehours==""){
			
			
			
			
			 if(lastnum=="."){
				 
				 
				 sheet.rateperhour =sheet.rateperhour;
				 
				 sheet.amount = sheet.rateperhour+0.00;
				 sheet.amount = sheet.amount.toFixed(2);
				 
				 
			 }
			 
			 else{

				 	sheet.rateperhour =parseFloat(sheet.rateperhour);


				 	sheet.amount=parseFloat((1.0*sheet.rateperhour).toFixed(2));
				 	
				 	sheet.amount =sheet.amount.toFixed(2)

				 
			 }
			

		}else {
			
			
			if(lastnum=="."){
				 
				 
				 sheet.rateperhour =sheet.rateperhour;
				 
				 sheet.amount = (sheet.rateperhour+0.00) * sheet.billablehours;
				 
				 sheet.amount = sheet.amount.toFixed(2);
				 
				 
			 }
			else{

			sheet.rateperhour =parseFloat(sheet.rateperhour);

			sheet.billablehours = parseFloat(sheet.billablehours);

			sheet.amount=parseFloat(sheet.billablehours*sheet.rateperhour).toFixed(2);
		
			}
	}
		
		
		if($scope.invoiceList==undefined || $scope.invoiceList== "" || $scope.invoiceList.length<0){
			 
			 
			 $scope.invoice.invoiceamt = sheet.amount * 1;
			 
			 $scope.invoice.invoiceamt = $scope.invoice.invoiceamt.toFixed(2);
			 
			 
		 }
		 else {
			 
			 var total=0;
			 
			 for(i=0;i<$scope.invoiceList.length;i++){
				 
				 total+= parseFloat($scope.invoiceList[i].amount);
				 
				
			 }
			 
			 $scope.invoice.invoiceamt = total;
			 
			 $scope.invoice.invoiceamt = $scope.invoice.invoiceamt.toFixed(2);
			 console.log($scope.invoice.invoiceamt);
		 }
		
		if(isNaN($scope.invoice.invoiceamt)){
			
			$scope.invoice.invoiceamt = undefined;
			
		}
		
		if(isNaN(sheet.amount)){
			 
			 sheet.amount = undefined;
			 
		 }
	}

	$scope.projectBasedSelections = function(project){

		var menuJson = angular.toJson({"project":project});
		
		$('#memberamount').prop('readonly', false);

		$scope.invoice.invoiceno="";
		$scope.invoiceList=[];
		$scope.invoice.projectType="";
		$scope.invoice.currency="";
		$scope.invoice.invoiceamt="";
		$scope.invoice.teammembers=[];
		$scope.member.description='';
		$scope.member.teamlist="Team Members"
		$scope.member.rateperhour=undefined;
		$scope.member.billablehours =undefined;
		$scope.member.amount=undefined;

		$http({

			url: 'rest/invoiceDetails/getProjectTypeList',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
			
			
	
			
			
			if(result.length<=0){
				
				
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Please Enter project cost for" +project.projectName+ "!");
				
				$scope.invoice.projectlist="Project";
				return;
				
				
			}
			
			else{


			$scope.projectTypeList=result;
			console.log($scope.projectTypeList);
			$scope.invoice.projectType =$scope.projectTypeList[0].projectType;
			$scope.invoice.currency=$scope.projectTypeList[0].currencyCode;
			
			if($scope.invoice.projectType=="Monthly"){
				
				$('#memberamount').prop('readonly', false);

				
			} 
			
			else if($scope.invoice.projectType=="Fixed"){
			
				$('#rateperhour').hide();
				$('#billablehour').hide();
				$('#memberamount').prop('readonly', false);
			
			}
			else{
				
				$('#memberamount').prop('readonly', true);
			}
			
			console.log("result------->:"+result);

			for(var i=0;i<result.length;i++){

				if(angular.isUndefined($scope.invoice.teammembers)){
					$scope.invoice.teammembers= new Array();
				}

				var teammembers=[];
				
				for(var j=0;j<result[i].projectCostDetails.length;j++){
					
					

					teammembers={"member":result[i].projectCostDetails[j].employee};

					$scope.invoice.teammembers.push(teammembers);
					
				}

			}

			console.log($scope.invoice.teammembers);
			}

		});
		
	

	}
	
	
	$scope.calculateRateForInsert = function(teammember,project){
		
	
		
		
		var menuJson = angular.toJson({"memberId":teammember.member.id,"projectId":project.projectId}); 
		
		
		
		$http({

			url: 'rest/invoiceDetails/getMemberRate',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
	
				$scope.member.rateperhour= result.rate;
				
				
				$scope.amountCalForinsert();
				
				console.log($scope.member.rateperhour);
			
			
		});

		
	}
	
	
	
$scope.calculateRateForUpdate = function(project,sheet){
		
	
		
		
		var menuJson = angular.toJson({"memberId":sheet.teammember.id,"projectId":project.projectId}); 
		
		
		
		$http({

			url: 'rest/invoiceDetails/getMemberRate',
			method: 'POST',
			data: menuJson,
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {
	
				sheet.rateperhour= result.rate;
				
				$scope.amountCalForUpdate(sheet);

			
		});
}
	
	$scope.pencilClick = function(sheet){
	
		
		$scope.previoussheet=angular.copy(sheet);

		
	}

	$scope.addTeamMember = function (){

		var fromdate = new Date($scope.member.fromdate.split('-')[2],$scope.member.fromdate.split('-')[1],$scope.member.fromdate.split('-')[0]);
		var todate = new Date($scope.member.todate.split('-')[2],$scope.member.todate.split('-')[1],$scope.member.todate.split('-')[0]);

		var datevalidationfromDate =new Date(($scope.member.fromdate).split("-")[1]+"-"+($scope.member.fromdate).split("-")[0]+"-"+($scope.member.fromdate).split("-")[2]);	
		var datevalidationtoDate = new Date(($scope.member.todate).split("-")[1]+"-"+($scope.member.todate).split("-")[0]+"-"+($scope.member.todate).split("-")[2]);;
		
		
		if(isNaN($scope.member.rateperhour)){
			
			$scope.member.rateperhour=undefined;

			
		}
	
		if (datevalidationfromDate > datevalidationtoDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			return;	

		}
		if($scope.member.fromdate==undefined||$scope.member.todate==undefined){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details of from date and todate");
			return;

		}else if($scope.member.description==undefined||$scope.member.description==""){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for description");
			return;	

		}else if(($scope.member.rateperhour==undefined || isNaN($scope.member.rateperhour) ||$scope.member.rateperhour=="") &&($scope.invoice.projectType!="Monthly")){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for rate");
			return;	

		}else if(($scope.member.billablehours==undefined || isNaN($scope.member.billablehours) ||$scope.member.billablehours=="")&&($scope.invoice.projectType=="Hourly")){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for billable hours");
			return;	

		}else if($scope.member.amount==undefined || isNaN($scope.member.amount) || $scope.member.amount==""){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for amount");
			return;	

		}
		
		
		else{
			if(angular.isUndefined($scope.invoiceList)){
				$scope.invoiceList =[];
			}

			if($scope.member.billablehours==undefined || isNaN($scope.member.billablehours)){

				$scope.member.billablehours='';

			}
			
			if($scope.member.rateperhour==undefined || isNaN($scope.member.rateperhour)){
				
				
				$scope.member.rateperhour ='';
				
			}
		
			if($scope.member.teamlist==undefined || $scope.member.teamlist==null || $scope.member.teamlist==""){

				memberobj ="";
			}
			
			else if($scope.member.teamlist.member){
				memberobj=$scope.member.teamlist.member;
			}
			
			console.log($scope.member.teamlist);
			invoice = {"fromdate":$scope.member.fromdate,"todate":$scope.member.todate,"description":$scope.member.description,"billablehours":$scope.member.billablehours,"amount":$scope.member.amount,"index":$scope.iterator,"rateperhour":$scope.member.rateperhour};
			invoice["teammember"]=memberobj;
			console.log(invoice);
			var copiedarray=[];
			$scope.invoiceList.push(invoice);

			console.log($scope.invoiceList);
			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Member Details added successfully!!!!!");
			$scope.iterator++;
			$scope.member.fromdate=dt1;
			$scope.member.todate=dt2;
			$scope.member.teamlist='';
			$scope.member.description='';
			$scope.member.billablehours='';
			$scope.member.amount='';
			$scope.member.rateperhour='';
		}
			
			

			
	}

	$scope.updateteammembers= function(sheet){
		
		console.log("previoussheet:"+$scope.previoussheet);
		
		console.log("sheet:"+sheet);
		
		var fromdate = new Date(sheet.fromdate.split('-')[2],$scope.member.fromdate.split('-')[1],sheet.fromdate.split('-')[0]);
		var todate = new Date(sheet.todate.split('-')[2],sheet.todate.split('-')[1],sheet.todate.split('-')[0]);

		var datevalidationfromDate =new Date((sheet.fromdate).split("-")[1]+"-"+(sheet.fromdate).split("-")[0]+"-"+(sheet.fromdate).split("-")[2]);	
		var datevalidationtoDate = new Date((sheet.todate).split("-")[1]+"-"+(sheet.todate).split("-")[0]+"-"+(sheet.todate).split("-")[2]);;

		if (datevalidationfromDate > datevalidationtoDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			
			$scope.invoiceList[sheet.index]=$scope.previoussheet;
			return;

		}
		if(sheet.fromdate==undefined||sheet.todate==undefined){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details of from date and todate");
			$scope.invoiceList[sheet.index]=$scope.previoussheet;
			return;

		}else if(sheet.description==undefined||sheet.description==""){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for description");
			$scope.invoiceList[sheet.index]=$scope.previoussheet;
			return;	

		}else if(sheet.rateperhour==undefined || isNaN(sheet.rateperhour) ||sheet.rateperhour=="" &&($scope.invoice.projectType!="Monthly")){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for rate");
			$scope.invoiceList[sheet.index]=$scope.previoussheet;
			return;	

		}else if((sheet.billablehours==undefined || isNaN(sheet.billablehours) ||sheet.billablehours=="")&&($scope.invoice.projectType=="Hourly")){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for billable hours");
			$scope.invoiceList[sheet.index]=$scope.previoussheet;
			return;	

		}else if(sheet.amount==undefined || isNaN(sheet.amount) || sheet.amount==""){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter the member details for amount");
			$scope.invoiceList[sheet.index]=$scope.previoussheet;
			return;	

		}
		
		else{
			$scope.invoiceList[sheet.index]=sheet;

			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Member Details updated successfully!!!!!");
		}

		
	}

	$scope.deleteteammembers = function(sheet){

		
		console.log($scope.indexing);
		$scope.invoiceList.splice(sheet.index,1);
		$scope.iterator--;
		
		
		
		console.log($scope.invoiceList);
		
		if($scope.invoiceList.length<=0){
			
			
			
			
			 $scope.invoice.invoiceamt =undefined;
			$(".alert-msg").show().delay(1000).fadeOut(); 
			$(".alert-success").html("Member Details deleted Successfully!!!!");
			
		}
		
		else{
			
			 var total=0;
			 
			 for(i=0;i<$scope.invoiceList.length;i++){
				 
				 total+= parseFloat($scope.invoiceList[i].amount);
				 
				
			 }
			 
			 $scope.invoice.invoiceamt = total;
			 
			 $scope.invoice.invoiceamt = $scope.invoice.invoiceamt.toFixed(2);
			
		}
		
		
		 
		
	}

	$scope.teammemberslist= function(sheet){

		$scope.invoiceList[sheet.index]=$scope.previoussheet;
	
	}

	$scope.insert = function(){
		
		
		if($scope.invoiceList.length<=0){
			
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("please enter atleast one member!!!!");
			return;

		}
		
		console.log($scope.invoiceList);
		
		
		var jsonstring=JSON.stringify($rootScope.invoiceList);	
		console.log($scope.invoice.customer+','+$scope.invoice.projectlist);
		var sc=$scope.invoice.invoiceamt;
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
		
		var totalSum;
		
		if($scope.member.amount=="" || isNaN($scope.member.amount) || $scope.member.amount==undefined){
			
			
			totalSum =0;
			
			
		}
		else{

		
			totalSum= parseFloat($scope.member.amount);
		}
		var sum =0;
		
		if($scope.invoiceList.length<=0){
			
			$scope.invoice.invoiceamt = $scope.member.amount;
				
		}
		
		else{
		try{
			for(var i = 0; i < $scope.invoiceList.length; i++){
				var revenue = parseFloat($scope.invoiceList[i].amount);
				sum += revenue;
			}
			
			console.log("revenue-------->"+revenue);
			
		}
		catch(err)
		{
			$scope.invoiceList=[];
			totalSum=0;
			sum=0;
		}
		console.log("Sum of amounts==>"+totalSum);
		$scope.invoice.invoiceamt = totalSum+sum;
		}
	}

}])
