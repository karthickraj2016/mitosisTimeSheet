'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', ['ui.router','myApp.controllers','ngStorage','ui.bootstrap','ui.date'])
  
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
            function($stateProvider, $urlRouterProvider
                ) {

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
            /*  .state('signup', {
                  url: '/signup',
                  templateUrl: 'html/signup.html',
              })*/
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
          .state('projectmapping', {
                  url: '/projectmapping',
                  templateUrl: 'html/projectmapping.html',
                  controller : 'teamController'
          	 })
          .state('account', {
                  url: '/account',
                  templateUrl: 'html/Account.html',
//                  controller : 'listcontroller'
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
             });
    }
  ]);

angular.module('myApp.controllers',[])