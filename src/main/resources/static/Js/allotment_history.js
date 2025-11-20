document.addEventListener("DOMContentLoaded", function () {
  const invoiceModal = document.getElementById("invoiceModal");
  const closeModal = document.getElementById("closeModal");
  const invoiceIcon = document.getElementById("invoiceOpen");

  invoiceIcon.addEventListener("click", () => {
    invoiceModal.classList.add("open");
    document.body.style.overflow = "hidden";
  });

  closeModal.addEventListener("click", () => {
    invoiceModal.classList.remove("open");
    document.body.style.overflow = "auto";
  });

  window.addEventListener("click", (e) => {
    if (e.target === invoiceModal) {
      invoiceModal.classList.remove("open");
      document.body.style.overflow = "auto";
    }
  });
});



// Trigger search with backend request
function triggerSearch(query) {
  const trimmedQuery = query.trim();
  if (!trimmedQuery) return;

  // Redirect browser to search page with query parameter
  window.location.href = `/allotment_history?searchQuery=${encodeURIComponent(trimmedQuery)}`;
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