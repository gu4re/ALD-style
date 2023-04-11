var form_users = document.querySelector('#admin-users');
var form_models = document.querySelector('#admin-models');
// DeleteModels for admin
var deleteModelButton = form_models.querySelector('#admin-button-deletemodel');
deleteModelButton.addEventListener('click', (event) => {
    var nameDeleteModel = from_models.querySelector('#deletename');
    nameDeleteModel = nameDeleteModel.toLowerCase().replace(' ', '_');
    var xhr_delete = new XMLHttpRequest();
    xhr_delete.open("GET", "#allProducts");
    xhr_delete.onload = function() {
        var parser = new DOMParser();
        var htmlDoc = parser.parseFromString(xhr_delete.response, "text/html");
        var containerDiv = htmlDoc.querySelector(`[id^=adm_${nameDeleteModel}]`);
        if (containerDiv) {
            containerDiv.remove();
        }
    }
    xhr_delete.send();
});
// AddModels for admin
var addModelButton = form_models.querySelector('#admin-button-addmodel');
addModelButton.addEventListener('click', (event) => {
    var nameModel = from_models.querySelector('#addname');
    var IDModel = from_models.querySelector('#addId');
    var PriceModel = from_models.querySelector('#addprice');
    var IMGModel = from_models.querySelector('#addlink');
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "#allProducts");
    xhr.onload = function() {
      var parser = new DOMParser();
      var htmlDoc = parser.parseFromString(xhr.response, "text/html");
      var newDiv = htmlDoc.createElement("div");
      newDiv.innerHTML = `<div class="col mb-5" id="adm_${nameModel}">
            <div class="card h-100">
              <!-- New badge-->
              <div class="badge bg-warning text-black position-absolute" style="top: 0.5rem; right: 0.5rem">
                New
              </div>
              <!-- Product image-->
              <img class="card-img-top" src="${IMGModel}" alt="..." img="img_${nameModel}"/>
              <!-- Product details-->
              <div class="card-body p-4">
                <div class="text-center">
                  <!-- Product name-->
                  <h5 class="fw-bolder" id="name_${nameModel}">${nameModel}</h5>
                  <!-- Product reviews-->
                  <div class="d-flex justify-content-center small text-warning mb-2">
                    <div class="bi-star"></div>
                    <div class="bi-star"></div>
                    <div class="bi-star"></div>
                    <div class="bi-star"></div>
                    <div class="bi-star"></div>
                  </div>
                  <!-- Product price-->
                  <p id="p_${nameModel}">${PriceModel}</p>
                </div>
              </div>
              <!-- Product actions-->
              <div class="card-footer p-4 pt-0 border-top-0 bg-transparent">
                <div class="text-center">
                  <form>
                    <select class="form-select mb-3" aria-label="Select size" id="ss_${nameModel}">
                      <option selected disabled>Select size</option>
                      <option value="36">36</option>
                      <option value="37">37</option>
                      <option value="38">38</option>
                      <option value="39">39</option>
                      <option value="40">40</option>
                      <option value="41">41</option>
                      <option value="42">42</option>
                      <option value="43">43</option>
                      <option value="44">44</option>
                      <option value="45">45</option>
                    </select>
                    <a class="btn btn-outline-dark mt-2" id="atc_${nameModel}">Add to cart</a>
                  </form>
                </div>
              </div>
            </div>
          </div>`;
      var containerDiv = htmlDoc.querySelector(".row.gx-4");
      containerDiv.appendChild(newDiv);
      document.documentElement.innerHTML = htmlDoc.documentElement.innerHTML;
    };
    xhr.send();
});
// Documentation for admin
var doc_button = document.querySelector('#javadoc-button');
doc_button.addEventListener('click', (event) => {
    event.preventDefault();
    window.location.href = '/doc';
});
// Add users from admin
var confirm_add_user = document.querySelector('#admin-button-adduser');
var email = '';
var password = '';
confirm_add_user.addEventListener('click', (event) => {
    event.preventDefault();
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
    password = document.getElementById('addpassword').value;
    if (password.length < 8){
       Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Password must have at least 8 characters',
          confirmButtonColor: '#0E5FA7'
       })
       return;
    }
    confirmPassword = document.getElementById('addpasswordcheck').value;
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
          email: email,
          password: password,
          username: document.getElementById('username').value
      })
    })
    .then((response) => {
      console.log('response', response);
      if(response.ok) {
        fetch('/auth/validate', {
            method: 'POST',
        })
        .then((response) => {
            console.log('response', response);
            if(response.ok) {
                Swal.fire({
                    title: 'Register success!',
                    text: 'The account with email' + ${email} + 'has been created',
                    icon: 'success',
                    confirmButtonColor: '#0E5FA7'
                });
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
        .catch((error) => {
            // if an unexpected error occurs
            Swal.fire({
                  icon: 'error',
                  title: 'Internal server error!',
                  text: 'Message: ' + error.message,
                  footer: '<a href="#404">Contact support for more information.</a>'
            })
        });
      } else if (response.status === 400){
        // If the response indicates a credential error, display an alert message
        Swal.fire({
          icon: 'warning',
          title: 'Oops...',
          text: 'Unable to create the user. This user is already registered',
          confirmButtonColor: '#0E5FA7'
        })
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
});
// Delete users from admin
var confirm_delete_user = document.querySelector('#admin-button-deleteuser');
confirm_delete_user.addEventListener('click', (event) => {
    event.preventDefault();
    var email = form_users.querySelector('#deletemail');
    fetch('/auth/delete', {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            email: email;
        })
    })
    .then((response) => {
        if(response.ok) {
            Swal.fire({
                title: 'Delete success!',
                text: 'The account with email' + ${email} + 'has been removed',
                icon: 'success',
                confirmButtonColor: '#0E5FA7'
            });
        } else if (response.status === 400){
            Swal.fire({
                title: 'Oops...',
                text: 'The account with email' + ${email} + 'does not exist',
                icon: 'warning',
                confirmButtonColor: '#0E5FA7'
            });
        }else{
            Swal.fire({
                icon: 'error',
                title: 'Internal server error!',
                text: 'Status code: ' + response.status,
                confirmButtonColor: '#0E5FA7',
                footer: '<a href="#404">Contact support for more information.</a>'
            })
        }
    });
});

