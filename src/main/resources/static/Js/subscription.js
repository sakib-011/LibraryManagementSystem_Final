
document.addEventListener("DOMContentLoaded", () => {

    const addForm = document.getElementById("addStudentForm");
    const modal = document.getElementById("addSubscription");

    if (addForm) {
        addForm.addEventListener("click", (event) => {
            event.preventDefault();     // stop page reload
            modal.classList.add("open"); // now modal will appear
        });
    }


    const alertBox = document.getElementById('alertBox');
    if (alertBox) {
        setTimeout(() => {
            alertBox.classList.add('hide');
        }, 5000);
    }


    const editForm = document.getElementById("edit");
    const editModal = document.getElementById("editSubscription");

    if(editForm){
        editForm.addEventListener("submit" , (event) =>{
           event.preventDefault();
           editModal.classList.add("open");
        });
    }

    const deleteForm = document.getElementById("delete");
    const deleteModal = document.getElementById("deleteSubscription");

    if(deleteForm){
        deleteForm.addEventListener("submit" , (event) =>{
            event.preventDefault();
            deleteModal.classList.add("open");
        });
    }

});



// Trigger search with backend request
function triggerSearch(query) {
    const trimmedQuery = query.trim();
    if (!trimmedQuery) return;

    // Redirect browser to search page with query parameter
    window.location.href = `/subscription?searchQuery=${encodeURIComponent(trimmedQuery)}`;
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