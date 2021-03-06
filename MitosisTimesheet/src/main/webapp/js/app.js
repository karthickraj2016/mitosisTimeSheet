'use strict';


//Declare app level module which depends on filters, and services
angular.module('myApp', ['ui.router','myApp.controllers','ngStorage','ui.bootstrap','ui.date', 'dialogs'])

.run(
		['$rootScope', '$state', '$stateParams',
		 function($rootScope, $state, $stateParams) {
			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;
		}
		])

		.config(
				[
				 '$stateProvider',
				 '$urlRouterProvider',
				 function($stateProvider, $urlRouterProvider) {

					 // lazy controller, directive and service
					 /*app.controller = $controllerProvider.register;
                app.filter = $filterProvider.register;
                app.factory = $provide.factory;
                app.service = $provide.service;
                app.constant = $provide.constant;
                app.value = $provide.value;*/

					 $urlRouterProvider.otherwise('/login');
					 $stateProvider
					 .state('login', {
						 url: '/login',
						 templateUrl: 'html/Signin_mit.html'
					 })
					
					 .state('access.success', {
						 url: '/success',
						 templateUrl: 'html/success.html'
					 })
					 .state('access.error', {
						 url: '/error',
						 templateUrl: 'html/error.html'
					 })
					 .state('timesheet', {
						 url: '/timesheet',
						 templateUrl: 'html/timesheet.html',
						 controller : 'listcontroller'
					 })
					 .state('project', {
						 url: '/project',
						 templateUrl: 'html/project.html',
						 controller : 'projectController'
					 })
					 .state('teamAssignment', {
						 url: '/teamAssignment',
						 templateUrl: 'html/teamAssignment.html',
						 controller : 'teamAssignmentController'
					 })
					 .state('account', {
						 url: '/account',
						 templateUrl: 'html/account.html',
						 controller : 'accountController'
					 })
					 .state('resetpassword', {
						 url: '/resetpassword',
						 templateUrl: 'html/reset_pwd.html',
						 controller : 'forgotpasswordController'
					 })
					 .state('userRights', {
						 url: '/userRights',
						 templateUrl: 'html/authority.html',
						 controller : 'userRightsController'
					 })
					 .state('individualreport', {
						 url: '/individualreport',
						 templateUrl: 'html/individualreport.html',
						 controller:'individualreportController'
					 })

					 .state('teamreport', {
						 url: '/teamreport',
						 templateUrl: 'html/teamreport.html',
						 controller :'teamreportController'
					 })

					 .state('leaveDetails', {
						 url: '/leaveDetails',
						 templateUrl: 'html/leaveDetails.html',
						 controller :'leaveDetailsController'
					 })

					 .state('customerDetails', {
						 url: '/customerDetails',
						 templateUrl: 'html/customerDetails.html',
						 controller :'customerDetailsController'
					 })

					 .state('leavereport', {
						 url: '/leaveReport',
						 templateUrl: 'html/leavereport.html',
						 controller :'leaveReportController'
					 })

					 .state('employeeMaster', {
						 url: '/employeeMaster',
						 templateUrl: 'html/employeeMaster.html',
						 controller :'employeeMasterController'
					 })
					 .state('invoice', {
						 url: '/invoice',
						 templateUrl: 'html/project_invoice.html',
						 controller :'invoiceDetailsController'
					 })
					 .state('levelMaster', {
						 url: '/levelMaster',
						 templateUrl: 'html/levelMaster.html',
						 controller :'levelMasterController'
					 })
					 .state('company_info', {
						 url: '/company_info',
						 templateUrl: 'html/company_info.html',
						 controller	:'companyInfoController'
					 })
					 .state('customer_payment', {
						 url: '/customer_payment',
						 templateUrl: 'html/customer_payment.html',
						 controller	:'customerpaymentController'
					 })
					 .state('projectCostDetails', {
						 url: '/projectCostDetails',
						 templateUrl: 'html/projectCost.html',
					     controller :'projectCostDetailsController'
					 })
					 .state('bankReconcile', {
						 url: '/bankReconcile',
						 templateUrl: 'html/bank_reconcile.html',
					     controller :'bankReconcileController'
					 })
					 .state('financeReport', {
						 url: '/financeReport',
						 templateUrl: 'html/finance_report.html',
					     controller :'financeReportController'
					 })
					 .state('invoiceView', {
						 url: '/financeReport',
						 templateUrl: 'html/invoiceView.html',
					     controller :'invoiceViewController'
					 })
					 
					 .state('projectCostView', {
						 url: '/allProjectCost',
						 templateUrl: 'html/projectCostView.html',
					     controller :'projectCostViewController'
					 })
					 
					  .state('lobMaster', {
						 url: '/lobMaster',
						 templateUrl: 'html/lobMaster.html',
					     controller :'lobMasterController'
					 })
					  .state('pendingReceipt', {
						 url: '/pendingReceipt',
						 templateUrl: 'html/pendingReceiptDetails.html',
					     controller :'pendingReceiptController'
					 })
					 
					  .state('expenses', {
						 url: '/expenses',
						 templateUrl: 'html/expenses.html',
						 controller :'expensesController'
					    
					 })
					   .state('pettyCash', {
						 url: '/pettyCash',
						 templateUrl: 'html/pettycash.html',
					    
					 })
					  .state('issueCheque', {
						 url: '/issueCheque',
						 templateUrl: 'html/issuecheque.html',
					    
					 })
				
					 
			 }]);

angular.module('myApp.controllers',[])