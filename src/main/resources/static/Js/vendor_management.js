// ======================================================
// REUSABLE MODAL HANDLER
// ======================================================

function setupModal(triggerSelector, modalId, closeSelector) {
    const modal = document.getElementById(modalId);
    if (!modal) {
        console.error(`Modal not found: ${modalId}`);
        return;
    }

    const triggers = document.querySelectorAll(triggerSelector);
    const closeButtons = modal.querySelectorAll(closeSelector);

    function openModal() {
        modal.style.display = "flex";
    }

    function closeModal() {
        modal.style.display = "none";
    }

    // Open modal when trigger clicked
    triggers.forEach(trigger => trigger.addEventListener("click", openModal));

    // Close modal when close button clicked
    closeButtons.forEach(btn => btn.addEventListener("click", closeModal));

    // Close modal if clicked outside content (overlay)
    modal.addEventListener("click", e => {
        if (e.target === modal) closeModal();
    });
}

// ======================================================
// INITIALIZATION ON PAGE LOAD
// ======================================================

document.addEventListener("DOMContentLoaded", () => {

    // ---------------- VENDOR MODAL ----------------
    setupModal("#openVendorModal", "vendorModal", "#closeVendorModal, #cancelVendorModal");

    // ---------------- DELETE MODAL ----------------
    setupModal(".delete", "deleteModal", "#closeDelete");

    // ---------------- EDIT MODAL ----------------
    setupModal(".edit", "editModal", "#closeEdit");

    // ---------------- AUTO-HIDE ALERT ----------------
    const alertBox = document.getElementById("alertBox");
    if (alertBox) {
        setTimeout(() => alertBox.style.display = "none", 5000);
    }


    const searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.addEventListener("keyup", function(event) {
            if (event.key === 'Enter') {
                event.preventDefault();
                const query = this.value.trim();
                if (query) {
                    console.log("Searching for:", query);
                    window.location.href = `/vendor_management?searchQuery=${encodeURIComponent(query)}`;
                }
            }
        });
    }

});



