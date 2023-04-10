var buttons = document.querySelectorAll('[id^="atc_"]');
buttons.forEach((button) => {
  button.addEventListener('click', () => {
    // Catch the elements to send it to backend

    fetch('/cart/add', {
      method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            // elements read
        })
    })
    .then(response => {
        console.log('response', response);
        if(response.ok) {
          Swal.fire({
            title: 'Added to cart!',
            text: 'Close and keep buying or visit your cart',
            icon: 'success',
            confirmButtonColor: '#0E5FA7'
          });
        }
        } else if (response.status === 400){
            // If the response indicates a credential error, display an alert message
            Swal.fire({
              icon: 'warning',
              title: 'Oops...',
              text: 'A problem has occurred during adding to cart. Try it later!',
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
  });
});