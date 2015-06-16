$(function() {
    var wrapper = $('#auth_container');
    var signIn = $('#signin');
    var signIn1 = $('#signin1');
    var signInContainer = $('#login_container');
    var signUp = $('#register');
    var signUpContainer = $('#signup_container');
    var forgotpassword = $('#forgot_pwd');
    var forgotpwdcontainer = $('#mid');
    wrapperHeightSignUp = 540;
    wrapperHeightSignIn = 420;
    signUp.click(function() {
        signIn.css('opacity', '0');
        wrapperHeightSignIn = document.getElementById("auth_container").offsetHeight;
        wrapper.css('height', (wrapperHeightSignIn ) + 'px');
        signInContainer.fadeOut('slow');
        wrapper.stop().animate({height: wrapperHeightSignUp}, 600);
        signUpContainer.css('opacity', '0', 'display', 'block');
        signUpContainer.animate({opacity: 1}, 'slow').fadeIn(600, function() {
            wrapper.stop().animate({height: '100%'}, 600);
            signIn.animate({opacity: 1}, 200);
        });
    });
    
    forgotpassword.click(function() {
    	signIn.css('opacity', '0');
        wrapperHeightSignIn = document.getElementById("auth_container").offsetHeight;
        wrapper.css('height', (wrapperHeightSignIn ) + 'px');
        signInContainer.fadeOut('slow');
        wrapper.stop().animate({height: wrapperHeightSignUp}, 600);
        forgotpwdcontainer.css('opacity', '0', 'display', 'block');
        forgotpwdcontainer.animate({opacity: 1}, 'slow').fadeIn(600, function() {
            wrapper.stop().animate({height: '100%'}, 600);
            signIn.animate({opacity: 1}, 200);
        });
    });

    signIn.click(function() {
    	
        signUp.css('opacity', '0');
        wrapperHeightSignUp = document.getElementById("auth_container").offsetHeight;
        wrapper.css('height', (wrapperHeightSignUp) + 'px');
        signUpContainer.fadeOut('slow');
        
        wrapper.stop().animate({height: wrapperHeightSignIn}, 600);
        signInContainer.css('opacity', '0', 'display', 'block');
        signInContainer.animate({opacity: 1}, 'slow').fadeIn(600, function() {
            wrapper.stop().animate({height: '100%'}, 600);
            signUp.animate({opacity: 1}, 200);
        });
    });
    
    signIn1.click(function() {
    	forgotpassword.css('opacity', '0');
        wrapperHeightSignUp = document.getElementById("auth_container").offsetHeight;
        wrapper.css('height', (wrapperHeightSignUp) + 'px');
        forgotpwdcontainer.fadeOut('slow');
        
        wrapper.stop().animate({height: wrapperHeightSignIn}, 600);
        signInContainer.css('opacity', '0', 'display', 'block');
        signInContainer.animate({opacity: 1}, 'slow').fadeIn(600, function() {
            wrapper.stop().animate({height: '100%'}, 600);
            forgotpassword.animate({opacity: 1}, 200);
        });
    });
    
    $("#cp").click(function() {
    	alert("DDD");
        $(".password-change").toggle();
    });
    
    /*$('#menu').on('click', function ( e ) {
        e.preventDefault();
        $(this).parents('.navbar-nav').find('.active').removeClass('active').end().addClass('active');
        $(activeTab).show();
    });*/
    
    
    
});