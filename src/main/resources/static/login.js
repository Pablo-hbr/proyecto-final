function inicializar() {
  if (location.search === '?registrado') {
    mostrarAviso('✓ ¡Registrado! Prueba a entrar', 'success');
  }
}

function entrar(datosJsonFormulario) {
  fetch('/api/users/me/session', {
    method: 'post',
    body: datosJsonFormulario,
    headers: { 'content-type': 'application/json' }
  })
    .then(response => {
      if (!response.ok) {
        mostrarAviso('✖︎ Credenciales incorrectas', 'error');
        return null;
      }
      return fetch('/api/users/me');
    })
    .then(res => {
      if (!res) return;
      return res.json();
    })
    .then(perfil => {
      if (!perfil) return;

      if (perfil.role === 'ADMINISTRADOR') {
        location.href = 'admin.html';
      } else {
        const idioma = (perfil.idioma || '').toLowerCase().trim();

        switch (idioma) {
          case 'FRANCES':
            window.location.href = 'frances.html';
            break;
          case 'INGLES':
            window.location.href = 'ingles.html';
            break;
          case 'ALEMAN':
            window.location.href = 'aleman.html';
            break;
          case 'ITALIANO':
            window.location.href = 'italiano.html';
            break;
          default:
            alert('No tienes idioma asignado. Contacta con soporte.');
            break;
        }
      }
    });
}


function mostrarAviso(texto, tipo) {
  const aviso = document.getElementById("aviso");
  aviso.textContent = texto;
  aviso.className = tipo;
}

function form2json(event) {
  event.preventDefault();
  const data = new FormData(event.target);
  return JSON.stringify(Object.fromEntries(data.entries()));
}