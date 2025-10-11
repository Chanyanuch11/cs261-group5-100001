// Support both signup and login pages
const form = document.getElementById('signupForm') || document.getElementById('loginForm');
const toggle = document.getElementById('toggleEye');
const pass = document.getElementById('password');

// Toggle password visibility (if present)
if (toggle && pass) {
  toggle.addEventListener('click', () => {
    const isPwd = pass.type === 'password';
    pass.type = isPwd ? 'text' : 'password';
    toggle.textContent = isPwd ? 'Hide' : 'Show';
  });
}

// Email validation helper function
function isValidEmail(email) {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return emailRegex.test(email);
}

// Form submission handler (only if a form exists)
if (form) {
  form.addEventListener('submit', (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(form).entries());
    const isSignup = form.id === 'signupForm';

    // Basic validation
    if (isSignup) {
      if (!data.name || !data.email || !data.password) {
        alert('Please fill in all fields.');
        return;
      }
    } else {
      if (!data.email || !data.password) {
        alert('Please fill in email and password.');
        return;
      }
    }

    // Email format validation
    if (!isValidEmail(data.email)) {
      alert('Please enter a valid email address.');
      return;
    }

    if (data.password && data.password.length < 8) {
      alert('Password must be at least 8 characters.');
      return;
    }

    if (isSignup) {
      alert(`Registered!\nName: ${data.name}\nEmail: ${data.email}`);
    } else {
      alert(`Logged in!\nEmail: ${data.email}`);
    }

    form.reset();
    if (pass && toggle) {
      pass.type = 'password';
      toggle.textContent = 'Show';
    }
  });
}

// Navigation helpers
function goLogin() {
  // Navigate to the login page
  window.location.href = 'login.html';
}

function goBack() {
  if (window.history.length > 1) {
    window.history.back();
  } else {
    // Fallback to signup page if no history
    window.location.href = 'index.html';
  }
}

function forgotPassword() {
  alert('Password reset flow not implemented.');
}

function goSignup() {
  // Navigate to the signup page
  window.location.href = 'signup.html';
}
