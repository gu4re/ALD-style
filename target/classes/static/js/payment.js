console.log('Script loaded correctly.');
var regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
var form = document.querySelector('#login-form');
console.log('form', form);
var btn = form.querySelector('#login-button');
var email = '';
btn.addEventListener('click', (event) => {
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
    console.log('click');
    event.preventDefault();
    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: document.getElementById('email').value,
            password: document.getElementById('password').value
        })
    })
    .then((response) => {
        console.log('response', response);
        if(response.ok) {
            Swal.fire({
                title: 'Login success!',
                text: 'Close this window to follow your session.',
                icon: 'success',
                confirmButtonColor: '#0E5FA7'
            });
            // If the response is OK then go back to home with their session
            window.location.href = '#home';
            var loginButton = document.querySelector('[href="#login"]');
            loginButton.innerHTML =
                    `<div class="dropdown">
                      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-fill"></i> <span class="text-about">${email}</span>
                      </a>
                      <ul class="dropdown-menu my-2" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#404">Profile</a></li>
                        <hr class="dropdown-divider">
                        <li><a class="dropdown-item" id="sign-out">Sign out</a></li>
                      </ul>
                    </div>`;
            loginButton.removeAttribute('href');
            var signOutButton = loginButton.querySelector('#sign-out');
            signOutButton.addEventListener('click', (event) => {
                Swal.fire({
                  title: 'Confirm Logout',
                  text: "Are you sure you want to logout?",
                  icon: 'warning',
                  showCancelButton: true,
                  confirmButtonColor: '#0E5FA7',
                  cancelButtonColor: '#A5A79F',
                  confirmButtonText: 'Yes, Logout'
                }).then((result) => {
                  if (result.isConfirmed) {
                    window.location.href = '/';
                  }
                })
            });
        } else if (response.status === 400){
            // If the response indicates a credential error, display an alert message
            Swal.fire({
              icon: 'warning',
              title: 'Oops...',
              text: 'Wrong credentials',
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
        // if an unexpected error occurs
        Swal.fire({
              icon: 'error',
              title: 'Internal server error!',
              text: 'Status code: ${response.status}',
              confirmButtonColor: '#0E5FA7',
              footer: '<a href="#404">Contact support for more information.</a>'
        })
    });
});