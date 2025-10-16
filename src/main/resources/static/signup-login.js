// --- Support both signup and login pages ---
const form = document.getElementById('signupForm') || document.getElementById('loginForm');
const toggle = document.getElementById('toggleEye');
const pass = document.getElementById('password');

const API_BASE = "http://localhost:8080/api/users"; // backend base URL

// Toggle password visibility
if (toggle && pass) {
  toggle.addEventListener('click', () => {
    const isPwd = pass.type === 'password';
    pass.type = isPwd ? 'text' : 'password';
    toggle.textContent = isPwd ? 'Hide' : 'Show';
  });
}

// Handle signup / login
if (form) {
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const data = Object.fromEntries(new FormData(form).entries());
    const isSignup = form.id === 'signupForm';

    try {
      if (isSignup) {
        // --- Signup ---
        const res = await fetch(`${API_BASE}/signup`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            username: data.name,
            email: data.email,
            password: data.password
          })
        });

        if (!res.ok) {
          alert("❌ Signup failed (check console)");
          console.error(await res.text());
          return;
        }

        alert("✅ Signup successful!");
        window.location.href = "login.html"; // redirect to login after signup

      } else {
        // --- Login ---
        const res = await fetch(`${API_BASE}/login`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            email: data.email,
            password: data.password
          })
        });

        const user = await res.json();

        if (!res.ok) {
          alert("❌ Login failed: " + user.message);
          return;
        }

        localStorage.setItem("username", user.username);
        localStorage.setItem("email", user.email);
        localStorage.setItem("userId", user.userId);

        alert(`✅ Welcome ${user.username}!`);
        window.location.href = "index.html"; // ✅ changed from home.html to index.html
      }

    } catch (err) {
      console.error("⚠️ Error:", err);
      alert("⚠️ Backend not reachable — check if Spring Boot is running.");
    }
  });
}

// Navigation helpers
function goLogin() {
  window.location.href = "login.html";
}

function goSignup() {
  window.location.href = "signup.html";
}

function goBack() {
  if (window.history.length > 1) {
    window.history.back();
  } else {
    window.location.href = "index.html";
  }
}
