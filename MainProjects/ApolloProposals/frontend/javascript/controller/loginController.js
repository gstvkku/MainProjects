const USE_MOCK = false; // ⬅️ switch to false when backend is ready

import authService from "../services/authService.js";
import mockUsers from "../mockData.js";

function loginWithMock(email, password) {
  const user = mockUsers.find(
    (user) => user.email === email && user.password === password
  );

  return new Promise((resolve, reject) => {
    setTimeout(() => {
      if (user) {
        resolve({ success: true, user });
        localStorage.setItem("username", result.username);
      } else {
        reject({ success: false, message: "Invalid email or password" });
      }
    }, 500);
  });
}

async function init() {
  const form = document.querySelector(".login-form form");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = form.querySelector('input[type="email"]').value;
    const password = form.querySelector('input[type="password"]').value;

    try {
      await authService.authenticate(email, password);
    } catch (error) {
      alert(error.message || "Login failed.");
    }
  });
}

export default { init };
