fetch('/cart/show')
    .then(response => response.json())
    .then(data => {
        console.log('data', data);
        if(response.ok) {
            // Reading the 'data' variable that contains the database and inject HTML
        }
        } else if (response.status === 400){
            // If the response indicates a credential error, display an alert message
            Swal.fire({
              icon: 'warning',
              title: 'Oops...',
              text: 'A problem has occurred during showing the cart. Try it later!',
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
    .catch(error => {
        // if an unexpected error occurs
        Swal.fire({
              icon: 'error',
              title: 'Internal server error!',
              text: 'Status code: ' + response.status,
              footer: '<a href="#404">Contact support for more information.</a>'
        });
    });