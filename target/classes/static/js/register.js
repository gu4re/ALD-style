console.log('Script loaded correctly');
  var form = document.querySelector('#register-form');
  console.log('form', form);
  var btn = form.querySelector('#create-button');
  btn.addEventListener('click', (event) => {
      console.log('click');
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
        alert('Succesfully sign up! Close and login to your account.');
        // Si la respuesta es exitosa, redirigir al usuario al home
        window.location.href = '#home';
      } else {
        // Si la respuesta indica un error de credenciales, mostrar un mensaje de alerta
        alert('Invalid fields or maybe user already exists.');
      }
    })
    .catch((error) => {
      // Si ocurre un error durante la petición, mostrar un mensaje de alerta
      alert('Unexpected error signing up.');
    });
  });