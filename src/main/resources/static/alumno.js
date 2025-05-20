window.onload = function () {
  obtenerPerfil();

  const logoutBtn = document.getElementById('btn-logout');
  const bajaBtn = document.getElementById('btn-baja');

  if (logoutBtn) logoutBtn.addEventListener('click', logout);
  if (bajaBtn) bajaBtn.addEventListener('click', darseDeBaja);
};

function obtenerPerfil() {
  fetch('/api/users/me', { credentials: 'include' })
    .then(response => {
      if (!response.ok) {
        if (response.status === 401) {
          window.location.href = 'login.html';
        } else {
          throw new Error('Error al obtener perfil');
        }
      }
      return response.json();
    })
    .then(perfil => {
      const idioma = (perfil.idioma || '').toLowerCase().trim();

      // Redirige automáticamente a la página del idioma
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
          alert('No tienes un idioma asignado. Contacta con administración.');
          break;
      }
    })
    .catch(error => {
      alert('✖ ' + error.message);
    });
}

function logout() {
  fetch('/api/users/me/session', {
    method: 'DELETE',
    credentials: 'include'
  })
    .then(response => {
      if (response.ok) {
        window.location.href = 'login.html';
      } else {
        throw new Error('No se pudo cerrar sesión');
      }
    })
    .catch(error => alert('✖ ' + error.message));
}

function darseDeBaja() {
  if (!confirm('¿Estás seguro de que quieres darte de baja?')) return;

  fetch('/api/users/me', {
    method: 'DELETE',
    credentials: 'include'
  })
    .then(response => {
      if (response.ok) {
        window.location.href = 'login.html';
      } else {
        throw new Error('No se pudo eliminar la cuenta');
      }
    })
    .catch(error => alert('✖ ' + error.message));
}
