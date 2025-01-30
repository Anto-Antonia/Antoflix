document.addEventListener('DOMContentLoaded', function() {
    // Get the password field and the toggle eye icon
    const passwordField = document.getElementById('password');
    const togglePassword = document.getElementById('togglePassword');

    // Check if the elements exist before attaching event listener
    if (passwordField && togglePassword) {
        // Add event listener for click on the eye icon
        togglePassword.addEventListener('click', function() {
            // Toggle password visibility
            if (passwordField.type === 'password') {
                passwordField.type = 'text'; // Show password
                togglePassword.classList.replace('fa-eye', 'fa-eye-slash'); // Change icon
            } else {
                passwordField.type = 'password'; // Hide password
                togglePassword.classList.replace('fa-eye-slash', 'fa-eye'); // Revert icon
            }
        });
    }
});