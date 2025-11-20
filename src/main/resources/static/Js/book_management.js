// ===================================================
// --- Utility Functions ---
// ===================================================

// Clear form fields
function clearFormFields(form) {
  if (!form) return;
  form.querySelectorAll('input, textarea').forEach(input => {
    if (input.type === 'file') input.value = null;
    else if (input.type !== 'submit' && input.type !== 'button') input.value = '';
  });
}

// Image preview
function previewImage(event) {
  const reader = new FileReader();
  const output = document.getElementById('imagePreview');
  if (!output) return;

  reader.onload = () => {
    output.src = reader.result;
    output.style.display = 'block';
  };

  if (event.target.files && event.target.files.length > 0) {
    reader.readAsDataURL(event.target.files[0]);
  } else {
    output.src = "";
    output.style.display = 'none';
  }
}

// Trigger search with backend request
function triggerSearch(query) {
  const trimmedQuery = query.trim();
  if (!trimmedQuery) return;

  // Redirect browser to search page with query parameter
  window.location.href = `/book_management?searchQuery=${encodeURIComponent(trimmedQuery)}`;
}

// ===================================================
// --- Main Application Logic ---
// ===================================================
document.addEventListener('DOMContentLoaded', function() {

  // Helper to clean URL query params
  const cleanUrl = () => {
    const url = window.location.href.split('?')[0];
    window.history.replaceState({}, document.title, url);
  };

  // --- Add Book Modal ---
  const openBookModalButton = document.getElementById('openBookModalButton');
  const addBookModal = document.getElementById('addBookModal');
  const closeModalBtn = document.getElementById('closeModalBtn');
  const cancelBookModalButton = document.getElementById('cancelBookModalButton');
  const addBookForm = document.getElementById('addBookForm');

  if (openBookModalButton && addBookModal && closeModalBtn && cancelBookModalButton && addBookForm) {
    const closeAndClear = () => {
      addBookModal.style.display = 'none';
      clearFormFields(addBookForm);
    };

    openBookModalButton.onclick = () => {
      addBookModal.style.display = 'flex';
      clearFormFields(addBookForm);
    };
    closeModalBtn.onclick = closeAndClear;
    cancelBookModalButton.onclick = closeAndClear;
  }

  // --- Edit Book Modal ---
  const editBookModal = document.getElementById('editBookModal');
  const closeEditBtn = document.getElementById('closeEdit');
  const cancelEditBtn = document.getElementById('cancelEdit');
  const editButtons = document.querySelectorAll('.edit-n');

  if (editBookModal && closeEditBtn && cancelEditBtn) {
    const closeEditModal = () => {
      editBookModal.classList.remove("show");
      cleanUrl();
    };

    editButtons.forEach(btn => {
      btn.addEventListener('click', () => editBookModal.classList.add("show"));
    });

    closeEditBtn.addEventListener('click', closeEditModal);
    cancelEditBtn.addEventListener('click', closeEditModal);

  }

  // --- Search on Enter ---
  const searchInput = document.getElementById("searchInput");
  if (searchInput) {
    searchInput.addEventListener('keyup', function(event) {
      if (event.key === 'Enter') {
        event.preventDefault();
        triggerSearch(this.value);
      }
    });
  }

  const alertBox = document.getElementById('alertBox');
  if (alertBox) {
    setTimeout(() => {
      alertBox.classList.add('hide');
    }, 5000);
  }


});
