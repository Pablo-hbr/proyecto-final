window.onload = function() {
  cargarClases();
};

function cargarClases() {
  fetch('/api/clases')
      .then(response => response.json())
      .then(clases => {
        const select = document.getElementById('clase');
        select.innerHTML = '';
        clases.forEach(clase => {
          const option = document.createElement('option');
          option.value = clase.id;
          option.textContent = clase.nombre;
          select.appendChild(option);
        });

        console.log('Valor por defecto seleccionado:', select.value);
      })
      .catch(error => {
        console.error('Error al cargar las clases:', error);
      });
}

function compruebaPass() {
  let correcto = false;
  correcto = document.getElementById("password").value === 
             document.getElementById("password2").value;
  if (correcto) mostrarAviso();
  else mostrarAviso('✖︎ Contraseña inválida', 'error');
  return correcto;
}

function registrarUsuario(datosJsonFormulario) {
  if (!compruebaPass()) return;
  fetch('/api/users', {method: 'post', body: datosJsonFormulario, headers: {'content-type': 'application/json'}})
    .then(response => {
      if (response.ok) location.href = 'login.html?registrado';
      else if (response.status === 409) mostrarAviso('✖︎ Usuario ya registrado', 'error');
      else mostrarAviso('✖︎ Error en el registro', 'error');
    });
}

function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function form2json(event) {
  const select = document.getElementById('clase');
  console.log(select.value); // Muestra el valor seleccionado
  event.preventDefault();
  const data = new FormData(event.target);
  const obj = Object.fromEntries(data.entries());
  // Renombra y convierte el campo correctamente
  if (obj.clase) {
    obj.idClase = Number(obj.clase);
    delete obj.clase;
  }
  return JSON.stringify(obj);
}


