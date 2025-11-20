// ======================================================
// REUSABLE MODAL & POPUP HANDLER FUNCTIONS
// Handles opening & closing of all modals
// ======================================================

function setupModal(triggerSelector, modalId, closeSelector) {
    const modal = document.getElementById(modalId);
    if (!modal) {
        console.error(`Modal not found for ID: ${modalId}`);
        return;
    }

    const triggers = document.querySelectorAll(triggerSelector);
    const closeButtons = modal.querySelectorAll(closeSelector);

    function closeModal() {
        modal.style.display = "none";
        modal.classList.remove("show");
    }

    function openModal() {
        modal.style.display = "flex";
        modal.classList.add("show");
    }

    // Open modal
    triggers.forEach(trigger => {
        trigger.addEventListener("click", openModal);
    });

    // Close modal buttons
    closeButtons.forEach(btn => {
        btn.addEventListener("click", closeModal);
    });

    // Close modal on outside click
    window.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });
}

// ======================================================
// INITIALIZATION ON PAGE LOAD
// ======================================================

document.addEventListener("DOMContentLoaded", () => {

    // ---------------- MODALS ----------------
    setupModal("#filterBtn", "filterPopup", "#closePopup, .modal-close-btn");
    setupModal(".add-student", "addStudentModal", "#closeModalBtn, #cancelStudentModalButton");
    setupModal(".action-btn.edit", "editStudentModal", "#closeEditModalBtn, #cancelEditStudentModalButton");
    // Delete modal
    setupModal(".action-btn.delete", "deleteConfirmationModal", "#closeDeleteModalBtn, #cancelDeleteBtn, #confirmDeleteBtn");

    // ---------------- FORM SUBMISSIONS ----------------
    const addForm = document.getElementById("addStudentForm");
    if (addForm) {
        addForm.addEventListener("submit", () => console.log("Submitting Add Student Form..."));
    }

    const editForm = document.getElementById("editStudentForm");
    if (editForm) {
        editForm.addEventListener("submit", () => console.log("Submitting Edit Student Form..."));
    }

    // ---------------- SEARCH INPUT ----------------
    const searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.addEventListener("keyup", function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();
                const query = this.value.trim();
                if (query) {
                    console.log("Searching for:", query);
                    window.location.href = `/student_management?searchQuery=${encodeURIComponent(query)}`;
                }
            }
        });
    }

    // ---------------- AUTO-HIDE ALERT ----------------
    const alertBox = document.getElementById("alertBox");
    if (alertBox) {
        setTimeout(() => {
            alertBox.classList.add("hide");
        }, 5000);
    }

});
