window.onload = function () {
    obtenerPerfil();

    document.getElementById('btn-logout').addEventListener('click', logout);
    document.getElementById('btn-baja').addEventListener('click', darseDeBaja);
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
            const idioma = perfil.idioma || 'Sin clase';
            document.getElementById('idioma-clase').textContent = idioma;
        })
        .catch(error => {
            mostrarMensaje('✖ ' + error.message);
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
        .catch(error => mostrarMensaje('✖ ' + error.message));
}

function darseDeBaja() {
    if (!confirm('¿Estás seguro de que quieres darte de baja?')) return;

    fetch('/api/users/me', {
        method: 'DELETE',
        credentials: 'include'
    })
        .then(response => {
            if (response.ok) {
                window.location.href = 'login.html'; // ✅ No se intenta cerrar sesión
            } else {
                throw new Error('No se pudo eliminar la cuenta');
            }
        })
        .catch(error => mostrarMensaje('✖ ' + error.message));
}

function mostrarMensaje(texto) {
    document.getElementById('mensaje').textContent = texto;
}
function flipCard(card) {
  const original = card.dataset.original;
  const translation = card.dataset.translation;
  if (card.textContent === original) {
    card.textContent = translation;
  } else {
    card.textContent = original;
  }
}
