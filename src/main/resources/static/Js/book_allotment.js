document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("submissionModal");
    const closeBtn = modal.querySelector(".close");
    const cancelBtn = document.getElementById("cancelBtn");
    const openBtn = document.getElementById("submitCartBtn");

     if(openBtn){
         openBtn.addEventListener("click", () => {
             modal.classList.add("show");
             console.log("bookId" , document.getElementById("bookId"));
         });
     }

    // Close modal
    const closeModal = () => modal.classList.remove("show")
    closeBtn.addEventListener("click", closeModal);
    cancelBtn.addEventListener("click", closeModal);

    // Click outside modal content to close
    window.addEventListener("click", (e) => {
        if (e.target === modal) closeModal();
    });
});


document.addEventListener("DOMContentLoaded", () => {

    // Trigger search with backend request
    function triggerSearch(query) {
        const trimmedQuery = query.trim();
        if (!trimmedQuery) return;
        window.location.href = `/book_allotment?searchQuery=${encodeURIComponent(trimmedQuery)}`;
    }

    // Search input listener
    const searchInput = document.getElementById("searchInput");
    if (searchInput) {
        searchInput.addEventListener("keyup", (event) => {
            if (event.key === "Enter") {
                console.log("Search Input:", searchInput.value);
                triggerSearch(searchInput.value);
            }
        });
    }

});
