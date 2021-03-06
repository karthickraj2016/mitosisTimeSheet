'use strict';

angular.module('myApp.controllers')


.controller('leaveDetailsController', ['$scope', '$http', '$state','$localStorage','$rootScope', function($scope, $http, $state,$localStorage, $rootScope) {

	$scope.filteredParticipantsResults = []
	,$scope.currentPage = 1
	,$scope.numPerPage = 8
	,$scope.maxSize = 5;
	$scope.units;


	$scope.leaveTypes=["Full Day","First Half","Second Half"];
	$scope.fromTypedisplay = true; 


	$rootScope.navbar=true;

	$scope.checkRequired = function(sheet){
		if(sheet.frmEntryDate == '' || sheet.frmEntryDate == undefined) {
			return true;
		} else if(sheet.toEntryDate == '' || sheet.toEntryDate == undefined) {
			return true;
		} else if(sheet.reason == '' || sheet.reason == undefined) {
			return true;
		}else if(sheet.status == '' || sheet.status == undefined) {
			return true;
		}else{
			return false;
		}

	}

	$scope.dates = function() {
		var dt = new Date();
		var dd = ("0"+ (dt.getDate()-1)).slice(-2);
		var mm = ("0"+ (dt.getMonth()+1)).slice(-2); 
		var yyyy = dt.getFullYear();
		dt=dd+"-"+mm+"-"+yyyy;
		$scope.fromDate=dt;
		$scope.toDate=dt;
		$scope.toTypedisplay = false;

	};

	$scope.dateOptionsFrom = {

			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
			onSelect: function(selected) {
				$scope.toDate=selected;
				
				/*$scope.dateOptionsTo("option","minDate", selected);*/
			}

	};

	$scope.dateOptionsTo = {
			changeYear: true,
			changeMonth: true,
			dateFormat: 'dd-mm-yy',
	};




	$http({

		url: 'rest/teamAssignment/getMemberList',
		method: 'GET',
		/*data: menuJson,*/
		headers: {
			'Content-Type': 'application/json'
		}
	}).success(function(result, status, headers) {

		console.log(result);

		$scope.employeeNameList=result;

		for(var i=0;i<$scope.employeeNameList.length;i++){
			if($scope.employeeNameList[i].adminFlag >= 1){
				$scope.employeeNameList.splice(i,1);
			}
		}
		console.log($scope.employeeNameList);
	});

	$scope.list = function() {
		



		$http({
			url: 'rest/leaveDetails/showLeaveEntryList',
			method: 'GET',
			/* data: menuJson,*/
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			var a=result; 
			var emp;

			console.log("result:"+a);



			for(var i=0;i<a.length;i++){

				if(angular.isUndefined(emp)){
					emp = new Array();
				}	
				
				if(a[i].toLeaveType=="null"){
					a[i].toLeaveType="";

				}

				var empRate={"id":a[i].id,"employee":{"id":a[i].employee.id,"name":a[i].employee.name},"frmEntryDate":a[i].frmEntryDate,"toEntryDate":a[i].toEntryDate,
						"fromLeaveType":a[i].fromLeaveType,"toLeaveType":a[i].toLeaveType,	"noOfDays":a[i].noOfDays, "reason":a[i].reason};

				emp.push(empRate);
			}

			$scope.leaveEntryList=emp; 
			
		

			$scope.$watch('currentPage + numPerPage', function() {
				var begin = (($scope.currentPage - 1) * $scope.numPerPage)
				, end = begin + $scope.numPerPage;
				$scope.filteredParticipantsResults = $scope.leaveEntryList.slice(begin, end);
				$scope.totalItems =	$scope.leaveEntryList.length;
				$scope.dates();
			});
			$scope.noOfPages = Math.ceil($scope.leaveEntryList.length/$scope.maxSize);

			$scope.setPage = function(pageNo) {
				$scope.currentPage = pageNo;
			};

			$scope.filter = function() {
				$scope.$watch('search', function(term) {
					if(term==undefined){
						$scope.totalItems =	$scope.leaveEntryList.length; 


					}
					window.setTimeout(function() {
						$scope.totalItems = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);

						$scope.noOfPages = Math.ceil($scope.filteredParticipantsResults.length/$scope.maxSize);
					}, 10);
				});

			}
		})
	},

	$scope.insertLeaveEntry = function(){




		var fromdate = new Date($scope.fromDate.split('-')[2],$scope.fromDate.split('-')[1],$scope.fromDate.split('-')[0]);
		var todate = new Date($scope.toDate.split('-')[2],$scope.toDate.split('-')[1],$scope.toDate.split('-')[0]);

		var datevalidationfromDate =new Date(($scope.fromDate).split("-")[1]+"-"+($scope.fromDate).split("-")[0]+"-"+($scope.fromDate).split("-")[2]);	
		var datevalidationtoDate = new Date(($scope.toDate).split("-")[1]+"-"+($scope.toDate).split("-")[0]+"-"+($scope.toDate).split("-")[2]);;


		if (datevalidationfromDate > datevalidationtoDate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			return;
		}

		if($scope.fromLeaveType =="Second Half" && $scope.toLeaveType =="Second Half"){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Both From Leave Type and to leave Type cannot be second half!");
			$scope.fromLeaveType='';
			$scope.toLeaveType='';
			return;


		}
		
		if(fromdate==todate){
			
			$scope.toLeaveType='';
			
			
		}
		/*else if(fromdate.getDay()===2 || fromdate.getDay()===3 || todate.getDay()===2 || todate.getDay()===3){
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate or Todate cannot be on Saturdays or Sundays!!!!");
			return;

		}*/
		else{

			var menuJson = angular.toJson({
				"employeeId": $scope.employee.id,"fromDate":$scope.fromDate,"toDate":$scope.toDate,"reason":$scope.reason,"fromLeaveType":$scope.fromLeaveType,"toLeaveType":$scope.toLeaveType
			});

			$http({
				url: 'rest/leaveDetails/insertLeaveEntry',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="inserted"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Leave Entry Added Successfully");
					$scope.reason='';
					$scope.fromLeaveType ='';
					$scope.toLeaveType='';
					$scope.list();	
					

				}else if (result.value=="already exist"){
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Leave Entry Already Entered");
					$scope.list();	
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Process Failed");
					$scope.list();	
				}
			

			})
		}
	},
	
	
	$scope.pencilClick = function (sheet){
		
		if(sheet.frmEntryDate == sheet.toEntryDate){
			
			$('#toleavetype').hide();

		}
		
		else{
		
		
		
		$('#toleavetype').show();
		}
	
		$scope.leaveLists =angular.copy(sheet);
		
	}
	
	$scope.cancel = function (sheet){
		
		sheet = $scope.leaveLists;
		
	}

	$scope.updateLeaveEntry = function(reqParam){
		
		
		
	
		
/*		
	if(	$('#toleavetype').is(':hidden')){
		
		if(reqParam.toLeaveType){
			
			reqParam.toLeaveType="";
			$('#toleavetype').hide();
			
		}
		
		
	}*/
		
	var fromdate = new Date((reqParam.frmEntryDate).split("-")[1]+"-"+(reqParam.frmEntryDate).split("-")[0]+"-"+(reqParam.frmEntryDate).split("-")[2]);
	var todate = new Date((reqParam.toEntryDate).split("-")[1]+"-"+(reqParam.toEntryDate).split("-")[0]+"-"+(reqParam.toEntryDate).split("-")[2]);
	
	
	
	
		

		if (fromdate > todate) {
			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("FromDate cannot be after ToDate!");
			$scope.list();
			return;
		}
		
		if(reqParam.fromLeaveType =="Second Half" && reqParam.toLeaveType =="Second Half"){

			$(".alert-msg1").show().delay(1000).fadeOut(); 
			$(".alert-danger").html("Both From Leave Type and to leave Type cannot be second half!");
			$scope.list();
			return;


		}
		
		if(reqParam.toLeaveType=="null"){
			reqParam.toLeaveType='';

		}
		else{

			var menuJson = angular.toJson({
				"id":reqParam.id,"employeeId": reqParam.employee.id,"fromDate":reqParam.frmEntryDate,"toDate":reqParam.toEntryDate,"reason":reqParam.reason,"fromLeaveType":reqParam.fromLeaveType,"toLeaveType":reqParam.toLeaveType
			});

			$http({
				url: 'rest/leaveDetails/updateLeaveEntry',
				method: 'POST',
				data: menuJson,
				headers: {
					'Content-Type': 'application/json'
				}
			}).success(function(result, status, headers) {

				if(result.value=="updated"){
					$(".alert-msg").show().delay(1000).fadeOut(); 
					$(".alert-success").html("Leave Entry Updated Successfully");
				}else if(result.value=="already exist"){
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Leave Entry Already Exist");
				}else{
					$(".alert-msg1").show().delay(1000).fadeOut(); 
					$(".alert-danger").html("Leave Entry updating Failed");
				}
				$scope.list();	

			})
		}
	},

	$scope.confirmDelete = function(id){

		if(confirm('Are you sure you want to delete?')){
			$scope.deleteLeaveEntry(id);
		}
	},

	$scope.deleteLeaveEntry = function(id){

		$http({
			url: 'rest/leaveDetails/deleteLeaveEntry',
			method: 'POST',
			data: {"id":id},
			headers: {
				'Content-Type': 'application/json'
			}
		}).success(function(result, status, headers) {

			if(result=='success'){
				$(".alert-msg").show().delay(1000).fadeOut(); 
				$(".alert-success").html("Leave Entry Deleted Successfully");
			}else{
				$(".alert-msg1").show().delay(1000).fadeOut(); 
				$(".alert-danger").html("Leave Entry Deletion Failed");
			}
			$scope.list();	
		})
	}

	$scope.todatechange = function (){

		if($scope.fromDate == $scope.toDate){
			$scope.toLeaveType='';

			$scope.toTypedisplay = false;

		}
		
		else{

		$scope.toTypedisplay = true;
		}

	}


	$scope.fromdatechange = function (){

		if($scope.fromDate == $scope.toDate){
			
			$scope.toLeaveType='';

			$scope.toTypedisplay = false;

		}

		$scope.fromleavetype = "Full day";
		console.log($scope.fromleavetype);


	}
	
	
	$scope.editFromDateChange = function(sheet){
		
		if(sheet.frmEntryDate == sheet.toEntryDate){
			
			$scope.toleaveType = false;

		}

			
		
		
		
		
		
	}
	
	$scope.editToDateChange = function(sheet){
		
		

		if(sheet.frmEntryDate == sheet.toEntryDate){
			
			$scope.toleaveType = false;

		}
		
		else{

			$scope.toleaveType = true;
			}


		
	}



}])