console.log('Script loaded successfully');
var buttons = document.querySelectorAll('[id^="atc_"]');
var size = '';
buttons.forEach((button) => {
  button.addEventListener('click', () => {
    var container = button.closest('[id^="adm_"]');
    size = container.querySelector('[id^="ss_"]').value;
    if (size === 'Select size'){
        Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'You need to select a size to add it to the cart',
        })
        return;
    }
    fetch('/cart/add', {
      method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            name: container.querySelector('[id^="name_"]').textContent,
            prize: container.querySelector('[id^="p_"]').textContent.replace('$', ''),
            size: size
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
          var form = document.querySelector('#cart-button-form');
          var counter = parseInt(form.querySelector('#cart-counter').textContent);
          var newValue = counter + 1;
          form.querySelector('#cart-counter').textContent = newValue;
          console.log('newCartCounter', form.querySelector('#cart-counter').textContent);
        } else if (response.status === 400){
            // If the response indicates a credential error, display an alert message
            Swal.fire({
              icon: 'warning',
              title: 'Oops...',
              text: 'Out of stock. Maximum of 3 units exceeded',
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
              text: 'Message: ' + error.message,
              footer: '<a href="#404">Contact support for more information.</a>'
        });
    });
  });
});