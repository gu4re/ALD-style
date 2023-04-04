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
            text: 'Close this window to follow your session.',
            icon: 'success',
            confirmButtonColor: '#0E5FA7'
        });
        // If the response is OK then go back to home with their session
        window.location.href = '#home';
        var loginButton = document.querySelector('[href="#login"]');
        loginButton.innerHTML = loginButton.innerHTML =
                    `<div class="dropdown">
                      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-fill"></i> <span class="text-about">${email}</span>
                      </a>
                      <ul class="dropdown-menu my-2" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#404">Profile</a></li>
                        <hr class="dropdown-divider">
                        <li><a class="dropdown-item" href="/">Sign out</a></li>
                      </ul>
                    </div>`;
        loginButton.removeAttribute('href');
      } else if (response.status === 400){
        // If the response indicates a credential error, display an alert message
        Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Invalid fields introduced.',
          confirmButtonColor: '#0E5FA7'
        })
      }
      else{
          Swal.fire({
            icon: 'error',
            title: 'Internal server error!',
            text: 'Status code: ${response.status}',
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
          text: 'Status code: ${response.status}',
          confirmButtonColor: '#0E5FA7',
          footer: '<a href="#404">Contact support for more information.</a>'
      })
    });
});