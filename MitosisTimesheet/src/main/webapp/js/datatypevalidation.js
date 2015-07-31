
function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
     if (charCode == 46) {
        return true;
    } else if(charCode > 31 && (charCode < 48 || charCode > 57)) {
    	return false;
    }
    return true;
}
function ValidateAlpha(evt)
{	
    var keyCode = (evt.which) ? evt.which : evt.keyCode
    	
    if ((keyCode < 65 || keyCode > 90) && (keyCode < 97 || keyCode > 123) && keyCode != 32 && keyCode!=8 && keyCode!=46){
       return false;
    }
        return true;
}

function show_hide(){
           $('#acc_opt').toggle();
}

function showmd() {
$('#empdet').modal('show');
}
