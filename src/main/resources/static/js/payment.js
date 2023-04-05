console.log('Script loaded correctly.');
var regex = /^\d+$/;
var form = document.querySelector('#creditCard-form');
console.log('form', form);
var btn = form.querySelector('#credit-button');
var card_number = '';
var card_name = '';
var cvv = '';
btn.addEventListener('click', (event) => {
    card_number = document.getElementById('number').value;
    card_name = document.getElementById('name').value;
    cvv = document.getElementById('cvv').value;
    if (!regex.test(card_number.replace(/\s/g, ''))){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Invalid card number format',
          confirmButtonColor: '#0E5FA7'
       })
    }
    if (card_number.length !== 16){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Card number should have exactly 16 digits',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    if (card_name.length > 25){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Card Name should have less than 25 characters',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    if (!regex.test(cvv.replace(/\s/g, ''))){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Invalid CVV field format',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    if(cvv.length !== 3){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'CVV field should have exactly 3 digit',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    console.log('click');
    event.preventDefault();
    Swal.fire({
        title: 'Purchase success!',
        text: 'Thanks a lot! Close this window to keep visiting our page.',
        icon: 'success',
        confirmButtonColor: '#0E5FA7'
    });
    window.location.href = '#home';
});