console.log('Script cargado correctamente');
  var form = document.querySelector('#register-form');
  console.log('form', form);
  var btn = form.querySelector('#create-button');
  btn.addEventListener('click', (event) => {
      console.log('click');
      event.preventDefault();
      fetch('/register', {
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
        // Si la respuesta es exitosa, redirigir al usuario al home
        window.location.href = '#home';
      } else {
        // Si la respuesta indica un error de credenciales, mostrar un mensaje de alerta
        alert('Wrong credentials.');
      }
    })
    .catch((error) => {
      // Si ocurre un error durante la petición, mostrar un mensaje de alerta
      alert('Unexpected error during authentication.');
    });
  });