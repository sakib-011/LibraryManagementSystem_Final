document.addEventListener("DOMContentLoaded", () => {
  // Open popup when invoice icon clicked
  const invoiceIcon = document.querySelector(".fa-file-invoice");
  const popup = document.getElementById("invoicePopup");
  const closeBtn = document.querySelector(".close-btn");

  if (invoiceIcon && popup && closeBtn) {
    invoiceIcon.addEventListener("click", () => {
      popup.classList.add("show");
    });

    closeBtn.addEventListener("click", () => {
      popup.classList.remove("show");
    });

    popup.addEventListener("click", (e) => {
      if (e.target === popup) popup.classList.remove("show");
    });
  }

  // Filter popup
  const filterBtn = document.getElementById("filterBtn");
  const filterPopup = document.getElementById("filterPopup");
  const closePopUp = document.getElementById("closePopUp");

  if (filterBtn && filterPopup && closePopUp) {
    filterBtn.addEventListener("click", () => {
      filterPopup.style.display = "block";
    });

    closePopUp.addEventListener("click", () => {
      filterPopup.style.display = "none";
    });

    window.addEventListener("click", (e) => {
      if (e.target === filterPopup) {
        filterPopup.style.display = "none";
      }
    });
  }

  // --- Search on Enter ---
  const searchInput = document.getElementById("searchInput");

  function triggerSearch(query) {
    const trimmedQuery = query.trim();
    if (!trimmedQuery) return;
    window.location.href = `/submission?searchQuery=${encodeURIComponent(trimmedQuery)}`;
  }

  if (searchInput) {
    searchInput.addEventListener("keyup", function(event) {
      if (event.key === "Enter") {
        console.log("Search Input here:", this.value);
        event.preventDefault();
        triggerSearch(this.value);
      }
    });
  }
});
