document.querySelectorAll('.dropdown-toggle').forEach(toggle => {
  toggle.addEventListener('click', e => {
    e.preventDefault();
    const parent = toggle.parentElement;
    parent.classList.toggle('active');
  });
})