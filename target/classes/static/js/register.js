console.log('Script loaded correctly');
var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
var form = document.querySelector('#register-form');
console.log('form', form);
var email = '';
var password = '';
var btn = form.querySelector('#create-button');
btn.addEventListener('click', (event) => {
    console.log('click');
    email = document.getElementById('email').value;
    if (!regex.test(email)){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Invalid email format',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    password = document.getElementById('password').value;
    if (password.length < 8){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Password must have at least 8 characters',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    confirmPassword = document.getElementById('passwordcheck').value;
    if (password !== confirmPassword){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Password fields must be the same',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    event.preventDefault();
    fetch('/auth/register', {
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({
          email: document.getElementById('email').value,
          password: document.getElementById('password').value,
          username: document.getElementById('username').value
      })
    })
    .then((response) => {
      console.log('response', response);
      if(response.ok) {
        Swal.fire({
            title: 'Register success!',
            text: 'Please check your inbox and spam box to validate your user account',
            icon: 'success',
            confirmButtonColor: '#0E5FA7'
        });
        window.location.href = '#home';
      } else if (response.status === 400){
        // If the response indicates a credential error, display an alert message
        Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'This user is already registered. Instead, log in.',
          confirmButtonColor: '#0E5FA7'
        })
        window.location.href = '#login';
      }
      else{
          Swal.fire({
            icon: 'error',
            title: 'Internal server error!',
            text: 'Status code: ' + response.status,
            confirmButtonColor: '#0E5FA7',
            footer: '<a href="#404">Contact support for more information.</a>'
          })
      }
    })
    .catch((error) => {
      // Si ocurre un error durante la petición, mostrar un mensaje de alerta
      Swal.fire({
          icon: 'error',
          title: 'Internal server error!',
          text: 'Status code: ' + response.status,
          confirmButtonColor: '#0E5FA7',
          footer: '<a href="#404">Contact support for more information.</a>'
      })
    });
});