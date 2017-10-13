$(document).ready(function(){
    $("body").append('<div class="toast-message"><span style="display:none;" id="toastmessageouter" class=""><span id="toastmessage">Hello! Welcome to simpleToastAlert!</span><span onclick="closeToast()" id="closetoast">&times;</span></span></div>');
});

var successToast = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessageouter").removeClass("error-toast").addClass("success-toast");
    toastMessage(message);
};

var errorToast = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessageouter").removeClass("success-toast").addClass("error-toast");
    toastMessage(message);
};

var defaultToast = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessageouter").removeClass("success-toast").removeClass("error-toast");
    toastMessage(message);
};

var toastMessage = function(message) {
    $("#toastmessage").text(message);
    $("#toastmessageouter").fadeIn(500);
};

var closeToast = function(){
    $("#toastmessageouter").fadeOut("fast",function() {
        $("#toastmessageouter").removeClass("success-toast").removeClass("error-toast");
    });
};

var successToastAuto = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessageouter").removeClass("error-toast").addClass("success-toast");
    toastMessageAuto(message);
};

var errorToastAuto = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessageouter").removeClass("success-toast").addClass("error-toast");
    toastMessageAuto(message);
};

var defaultToastAuto = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessageouter").removeClass("success-toast").removeClass("error-toast");
    toastMessageAuto(message);
};

var toastMessageAuto = function(message) {
    $("#toastmessageouter").attr("style", "display:none");
    $("#toastmessage").text(message);
    $("#toastmessageouter").fadeIn(500);
    clearTimeout(timeout);
    var timeout = setTimeout(function(){
        $("#toastmessageouter").fadeOut(500);
    },1500);
};
