// Support both signup and login pages
const form = document.getElementById('signupForm') || document.getElementById('loginForm');
const toggle = document.getElementById('toggleEye');
const pass = document.getElementById('password');

// Toggle password visibility
if (toggle && pass) {
  toggle.addEventListener('click', () => {
    const isPwd = pass.type === 'password';
    pass.type = isPwd ? 'text' : 'password';
    toggle.textContent = isPwd ? 'Hide' : 'Show';
  });
}

function isValidEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email);
}

if (form) {
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(form).entries());
    const isSignup = form.id === 'signupForm';

    // Basic validation
	if (!data.email || !data.password || (isSignup && !data.username)) {
	  alert('Please fill in all fields.');
	  return;
	}
    if (!isValidEmail(data.email)) {
      alert('Please enter a valid email.');
      return;
    }
    if (data.password.length < 8) {
      alert('Password must be at least 8 characters.');
      return;
    }

    try {
      const url = isSignup
        ? "http://localhost:8080/api/users/signup"
        : "http://localhost:8080/api/users/login";

      const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
      });

      const result = await res.json();
      console.log("🔹 Backend response:", result);

      if (res.ok) {
        if (isSignup) {
          alert("✅ Signup successful! You can now log in.");
          window.location.href = "login.html";
        } else {
          // Store logged-in info
          localStorage.setItem("userId", result.id);
          localStorage.setItem("username", result.username);
          localStorage.setItem("email", result.email);
          alert("✅ Login successful!");
          window.location.href = "index.html";
        }
      } else {
        alert(`❌ ${result.message || 'Invalid credentials'}`);
      }

    } catch (err) {
      console.error("⚠️ Error connecting to backend:", err);
      alert("⚠️ Could not reach server. Is backend running?");
    }
  });
}

// Navigation helpers
function goLogin() { window.location.href = 'login.html'; }
function goSignup() { window.location.href = 'signup.html'; }
function goBack() { window.history.back(); }