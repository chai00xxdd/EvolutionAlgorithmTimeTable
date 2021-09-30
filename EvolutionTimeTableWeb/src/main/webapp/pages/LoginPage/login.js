
const loginForm = document.querySelector("#LoginForm");
const loginError = document.querySelector("#LoginError");


$(function(){
tryLogin();
});

$(loginForm).submit(function () {


    $.ajax({
        data: $(this).serialize(),
        url: this.action,
        timeout: 2000,
        error: function (errorObject) {
            console.error("Failed to login !");
            loginError.innerHTML = errorObject.responseText;
        },
        success: function (nextPageUrl) {
            window.location.replace(nextPageUrl);
        }
    });

    // by default - we'll always return false so it doesn't redirect the user.
    return false;

});

function tryLogin()
{

 $.ajax({
        data: $(loginForm).serialize(),
        url: loginForm.action,
        timeout: 2000,
        error: function (errorObject) {
          
        },
        success: function (nextPageUrl) {
            window.location.replace(nextPageUrl);
        }
    });

}