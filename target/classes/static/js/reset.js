console.log('Script loaded correctly.');
var form = document.querySelector('#resetPasswd-form');
console.log('form', form);
var btn = form.querySelector('#resetPasswd-button');
var newpassword = '';
var confirmPassword = '';
btn.addEventListener('click', (event) => {
    newpassword = document.getElementById('newpassword').value;
    if (newpassword.length < 8){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'New password must have at least 8 characters',
       })
       return;
    }
    confirmPassword = document.getElementById('newrepeatedpassword').value;
    if (newpassword !== confirmPassword){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Password fields must be the same',
       })
       return;
    }
    console.log('click');
    event.preventDefault();
    fetch('/auth/reset', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            newpassword: document.getElementById('newpassword').value
        })
    })
    .then((response) => {
        console.log('response', response);
        if(response.ok) {
            Swal.fire(
                'Password has changed successfully!',
                'Now you can access with your new credentials',
                'success'
            )
            // If the response is OK then go back to home with their session
            window.location.href = '#login';
        } else if (response.status === 400){
            // If the response indicates a credential error, display an alert message
            Swal.fire({
              icon: 'warning',
              title: 'Oops...',
              text: 'A problem has occurred changing the password. Does not exist a user with that email',
            })
        }
        else{
            Swal.fire({
              icon: 'error',
              title: 'Internal server error!',
              text: 'Status code: ' + response.status,
              footer: '<a href="#404">Contact support for more information.</a>'
            })
        }
    })
    .catch((error) => {
        // if an unexpected error occurs
        Swal.fire({
              icon: 'error',
              title: 'Internal server error!',
              text: 'Status code: ' + response.status,
              footer: '<a href="#404">Contact support for more information.</a>'
        })
    });
});