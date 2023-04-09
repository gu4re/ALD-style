fetch('/auth/validate', {
    method: 'POST',
})
.then((response) => {
    console.log('response', response);
    if(response.ok) {
        Swal.fire(
            'Validation success!',
            'Now, you can log into your account',
            'success'
        )
        // If the response is OK then go back to home with their session
        window.location.href = '#login';
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