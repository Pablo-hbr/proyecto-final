window.onload = function () {
    cargarAlumnos();
    cargarClases();

    document.getElementById('form-clase').addEventListener('submit', function (e) {
        e.preventDefault();
        agregarClase();
    });
};

document.getElementById('btn-logout').addEventListener('click', function () {
    logout();
});

function cargarAlumnos() {
    fetch('/api/admin/datos')
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) throw new Error('No autenticado');
                if (response.status === 403) throw new Error('Acceso denegado');
                throw new Error('Error al obtener datos');
            }
            return response.json();
        })
        .then(alumnos => {
            console.log('Respuesta del backend (alumnos):', alumnos);
            const tbody = document.querySelector('#tabla-alumnos tbody');
            tbody.innerHTML = '';
            alumnos.forEach(alumno => {
                const fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${alumno.nombre}</td>
                    <td>${alumno.email}</td>
                    <td>${alumno.clase ? alumno.clase : 'Sin clase'}</td>
                `;
                tbody.appendChild(fila);
            });
        })
        .catch(error => {
            mostrarMensaje(`✖ ${error.message}`, 'error');
        });
}

function cargarClases() {
    fetch('/api/clases')
        .then(response => {
            if (!response.ok) throw new Error('Error al cargar clases');
            return response.json();
        })
        .then(clases => {
            console.log('Clases recibidas:', clases);
            const tbody = document.querySelector('#tabla-clases tbody');
            tbody.innerHTML = '';
            clases.forEach(clase => {
                const fila = document.createElement('tr');
                fila.innerHTML = `
                    <td>${clase.nombre}</td>
                    <td>${clase.idioma}</td>
                    <td>${clase.aforo}</td>
                `;
                tbody.appendChild(fila);
            });
        })
        .catch(error => {
            mostrarMensaje(`✖ ${error.message}`, 'error');
        });
}

function agregarClase() {
    const nombre = document.getElementById('nombre').value.trim();
    const aforo = Number(document.getElementById('aforo').value);
    const idioma = document.getElementById('idioma').value;

    if (!nombre || !aforo || aforo < 1 || !idioma) {
        mostrarMensaje('✖ Datos inválidos', 'error');
        return;
    }

    const nuevaClase = {
        nombre: nombre,
        aforo: aforo,
        idioma: idioma
    };

    fetch('/api/clases', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'include',
        body: JSON.stringify(nuevaClase)
    })
        .then(response => {
            if (response.ok) {
                mostrarMensaje('✔ Clase añadida con éxito', 'success');
                document.getElementById('form-clase').reset();
                cargarAlumnos();
                cargarClases();
                return;
            }
            if (response.status === 401) throw new Error('No autenticado');
            if (response.status === 403) throw new Error('Acceso denegado');
            throw new Error('Error al añadir la clase');
        })
        .catch(error => {
            mostrarMensaje(`✖ ${error.message}`, 'error');
        });
}

function mostrarMensaje(texto, tipo) {
    const mensajeDiv = document.getElementById('mensaje');
    if (mensajeDiv) {
        mensajeDiv.textContent = texto;
        mensajeDiv.className = tipo;
    } else {
        alert(texto);
    }
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
                throw new Error('No se pudo cerrar la sesión');
            }
        })
        .catch(error => {
            console.error('✖ Error al cerrar sesión:', error);
            alert('Error al cerrar sesión');
        });
}
