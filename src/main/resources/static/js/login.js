console.log('Script loaded correctly.');
var form = document.querySelector('#login-form');
console.log('form', form);
var btn = form.querySelector('#login-button');
var email = '';
btn.addEventListener('click', (event) => {
    email = document.getElementById('email').value;
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
            Swal.fire(
                'Login success!',
                'Close this window to follow your session.',
                'success'
            )
            // If the response is OK then go back to home with their session
            window.location.href = '#home';
            var loginButton = document.querySelector('[href="#login"]');
            loginButton.innerHTML = 
                    `<div class="dropdown">
                      <a class="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="bi bi-person-fill"></i> <span class="text-about">${email}</span>
                      </a>
                      <ul class="dropdown-menu my-2" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#allProducts">Profile</a></li>
                        <hr class="dropdown-divider">
                        <li><a class="dropdown-item" href="#allProducts">Sign Up</a></li>
                      </ul>
                    </div>`;
            loginButton.removeAttribute('href');           
        } else if (response.status === 400){
            // If the response indicates a credential error, display an alert message
            Swal.fire({
              icon: 'warning',
              title: 'Oops...',
              text: 'Wrong credentials',
            })
        }
        else{
            Swal.fire({
              icon: 'error',
              title: 'Internal server error!',
              text: 'Status code: ${response.status}',
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
              footer: '<a href="#404">Contact support for more information.</a>'
        })
    });
});