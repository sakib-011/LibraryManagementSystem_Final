document.addEventListener("DOMContentLoaded", () => {

  const modals = {
    purchase: document.getElementById("purchaseModal"),
    invoice: document.getElementById("invoicePopup"),
    delete: document.getElementById("deleteConfirmationModal"),
    edit: document.getElementById("editModal")
  };

  const openModal = (modal) => modal?.classList.add("show");
  const closeModal = (modal) => modal?.classList.remove("show");

  // ===== Purchase Modal =====
  document.getElementById("openModal")?.addEventListener("click", () => openModal(modals.purchase));
  modals.purchase?.querySelector(".close")?.addEventListener("click", () => closeModal(modals.purchase));
  modals.purchase?.querySelector(".cancel")?.addEventListener("click", () => closeModal(modals.purchase));

  // ===== Invoice Modal (only open UI modal, allow href to work for backend) =====
  document.querySelectorAll(".fa-file-invoice").forEach(btn => {
    btn.addEventListener("click", () => openModal(modals.invoice));
  });
  document.getElementById("closeInvoicePopUp")?.addEventListener("click", () => closeModal(modals.invoice));

  // ===== Delete Modal =====
  document.querySelectorAll(".delete-icon").forEach(btn => {
    btn.addEventListener("click", () => openModal(modals.delete));
  });
  document.getElementById("closeDeleteModalBtn")?.addEventListener("click", () => closeModal(modals.delete));
  document.getElementById("cancelDeleteBtn")?.addEventListener("click", () => closeModal(modals.delete));

  // ===== Edit Modal =====
  document.querySelectorAll(".editPurchaseBook").forEach(btn => {
    btn.addEventListener("click", () => openModal(modals.edit));
  });
  document.getElementById("closeEditModal")?.addEventListener("click", () => closeModal(modals.edit));

  // ===== Close when clicking outside =====
  window.addEventListener("click", (e) => {
    Object.values(modals).forEach(modal => {
      if (modal && e.target === modal) closeModal(modal);
    });
  });

  // ===== Auto-hide alert =====
  const alertBox = document.getElementById("alertBox");
  if (alertBox) setTimeout(() => alertBox.classList.add("hide"), 4000);

  // ===== Calculate Total =====



  document.getElementById("quantity")?.addEventListener("input", calc);
  document.getElementById("price")?.addEventListener("input", calc);

});


const calc = () => {
  const qty = parseFloat(document.querySelector("input[name='quantity']")?.value) || 0;
  const price = parseFloat(document.querySelector("input[name='pricePerBook']")?.value) || 0;
  document.getElementById("total").value = (qty * price).toFixed(2);
};


// Trigger search with backend request
function triggerSearch(query) {
  const trimmedQuery = query.trim();
  if (!trimmedQuery) return;

  // Redirect browser to search page with query parameter
  window.location.href = `/purchase_books?searchQuery=${encodeURIComponent(trimmedQuery)}`;
}

// --- Search on Enter ---
const searchInput = document.getElementById("searchInput");
if (searchInput) {
  searchInput.addEventListener('keyup', function(event) {
    if (event.key === 'Enter') {
      console.log("Search Input here " , searchInput.value);
      event.preventDefault();
      triggerSearch(this.value);
    }
  });
}