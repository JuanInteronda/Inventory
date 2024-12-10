document.addEventListener('DOMContentLoaded', function () {
    handleErrorModal();
    initializeSearch();
    initializeCreateModal();
    initializeUpdateModal();
});

// Manejo del modal de error
function handleErrorModal() {
    const errorMessage = document.body.getAttribute('data-error-message');

    if (errorMessage) {
        const modalBody = document.getElementById('errorModalBody');
        modalBody.innerHTML = errorMessage;

        const errorModal = new bootstrap.Modal(document.getElementById('errorModalGlobal'));
        errorModal.show();
    }
}

// Manejo de la búsqueda en tiempo real
function initializeSearch() {
    const searchInput = document.querySelector('.search-input');
    if (searchInput) {
        searchInput.addEventListener('input', debounce(filterTable, 300)); // Agregamos debounce para evitar múltiples solicitudes
    }
}

function filterTable() {
    const input = document.querySelector('.search-input');
    const filter = input.value.toLowerCase();
    const tableBody = document.querySelector('.custom-table tbody');

    tableBody.innerHTML = '<tr><td colspan="7" class="text-center">Cargando...</td></tr>';

    fetch(`/tableDepositos/search?name=${filter}&page=0&size=15`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al buscar depósitos');
            }
            return response.text();
        })
        .then(html => {
            const parser = new DOMParser();
            const doc = parser.parseFromString(html, 'text/html');
            const newTableBody = doc.querySelector('.custom-table tbody');
            const pagination = doc.querySelector('.pagination');
            tableBody.innerHTML = newTableBody ? newTableBody.innerHTML : '<tr><td colspan="7" class="text-center">No se encontraron resultados</td></tr>';
            document.querySelector('.pagination').innerHTML = pagination ? pagination.innerHTML : '';
            initializeUpdateModal(); // Re-inicializar los eventos de los botones de edición
        })
        .catch(error => {
            console.error('Error al realizar la búsqueda:', error);
            tableBody.innerHTML = '<tr><td colspan="7" class="text-center text-danger">Error al cargar los datos</td></tr>';
        });
}

// Manejo del modal "Crear Depósito"
function initializeCreateModal() {
    const createDepositoModal = new bootstrap.Modal(document.getElementById('createDepositoModal'), {
        keyboard: false
    });

    const createButton = document.querySelector('.buttonPersistDeposito');
    if (createButton) {
        createButton.addEventListener('click', function () {
            createDepositoModal.show();
        });
    }
}

// Inicializar modal de actualización para depósitos
function initializeUpdateModal() {
    const updateModal = new bootstrap.Modal(document.getElementById('updateDepositoModal'));

    const updateButtons = document.querySelectorAll('.buttonUpdateDeposito[data-id]');
    updateButtons.forEach(button => {
        button.addEventListener('click', function () {
            const id = this.getAttribute('data-id');
            populateUpdateModal(id);
            updateModal.show();
        });
    });

    const updateModalElement = document.getElementById('updateDepositoModal');
        updateModalElement.addEventListener('hidden.bs.modal', function () {
            const form = updateModalElement.querySelector('form');
            form.reset();
        });
    }

    // Limpiar el modal cuando se cierra
    const updateModalElement = document.getElementById('updateDepositoModal');
    updateModalElement.addEventListener('hidden.bs.modal', function () {
        // Limpiar los campos del formulario
        document.getElementById('id').value = '';
        document.getElementById('nombre').value = '';
        document.getElementById('provincia').value = '';
        document.getElementById('direccion').value = '';
        document.getElementById('contactoDeposito').value = '';

        // Opcional: limpiar clases de validación, si usas alguna
        const form = updateModalElement.querySelector('form');
        if (form) form.reset();
    });
}

function populateUpdateModal(id) {
    fetch(`/tableDepositos/${id}`)
        .then(response => response.json())
        .then(data => {
            const updateModalElement = document.getElementById('updateDepositoModal');
            updateModalElement.querySelector('#id').value = data.id;
            updateModalElement.querySelector('#nombre').value = data.nombre;
            updateModalElement.querySelector('#provincia').value = data.provincia;
            updateModalElement.querySelector('#direccion').value = data.direccion;
            updateModalElement.querySelector('#contactoDeposito').value = data.contactoDeposito;
        })
        .catch(error => console.error('Error al cargar los datos del depósito:', error));
}

// Utilidad: Función de debounce para limitar solicitudes frecuentes
function debounce(func, delay) {
    let timeout;
    return function (...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), delay);
    };
}