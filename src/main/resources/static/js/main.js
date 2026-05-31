// Main JavaScript file for Music Label Platform

document.addEventListener('DOMContentLoaded', function() {

    // Password strength indicator
    const passwordInputs = document.querySelectorAll('input[type="password"][data-strength]');
    passwordInputs.forEach(input => {
        input.addEventListener('input', function() {
            const strengthIndicator = document.getElementById(this.getAttribute('data-strength'));
            if (strengthIndicator) {
                const strength = calculatePasswordStrength(this.value);
                updateStrengthIndicator(strengthIndicator, strength);
            }
        });
    });

    // File upload preview
    const fileInputs = document.querySelectorAll('input[type="file"][data-preview]');
    fileInputs.forEach(input => {
        input.addEventListener('change', function() {
            const previewElement = document.getElementById(this.getAttribute('data-preview'));
            if (previewElement && this.files.length > 0) {
                previewElement.innerHTML =
                    '<div class="file-info">' +
                    '<strong>Selected file:</strong> ' + this.files[0].name + '<br>' +
                    '<strong>Size:</strong> ' + (this.files[0].size / 1024 / 1024).toFixed(2) + ' MB<br>' +
                    '<strong>Type:</strong> ' + this.files[0].type +
                    '</div>';
            }
        });
    });

    // Auto-dismiss alerts
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(alert => {
        setTimeout(() => alert.remove(), 5000);
    });
});

function calculatePasswordStrength(password) {
    let strength = 0;
    if (password.length >= 8) strength++;
    if (password.match(/[a-z]/) && password.match(/[A-Z]/)) strength++;
    if (password.match(/\d/)) strength++;
    if (password.match(/[^a-zA-Z\d]/)) strength++;
    return strength;
}

function updateStrengthIndicator(indicator, strength) {
    const colors = ['#f44336', '#ff9800', '#2196f3', '#4caf50'];
    const texts = ['Very Weak', 'Weak', 'Good', 'Strong'];
    const widths = ['25%', '50%', '75%', '100%'];

    const idx = Math.min(strength, 3);

    indicator.style.width = widths[idx];
    indicator.style.backgroundColor = colors[idx];

    const container = indicator.closest('.password-strength-container');
    const textElement = container ? container.querySelector('.strength-text') : null;
    if (textElement) {
        textElement.textContent = texts[idx];
    }
}