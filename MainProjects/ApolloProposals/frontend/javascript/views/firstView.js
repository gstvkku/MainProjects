import loginController from "../controller/loginController.js";
import registerService from "../services/registerService.js";

function renderPage() {
  const container = document.querySelector("#container");

  container.innerHTML = `
    <header class="scrollreveal-header">
      <div class="interface">
        <div class="logo">
          <a id="header-logo">
            <img class="logo" src="/resources/apollo-logo.png" alt="logo" />
          </a>
        </div>
        <nav class="menu-desktop">
          <ul>
            <li><a href="#about">About us</a></li>
            <li><a href="#login">Login/Register</a></li>
            <li><a href="#contact">Contact Us</a></li>
            <li>
              <a href="#login"><button class="generate-button">Generate Proposal</button></a>
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <main>
      <section class="welcome">
        <div class="interface">
          <div class="flex scrollreveal-final-box">
            <div class="circles-container">
              <div class="circle1"><div class="pulse"></div></div>
              <div class="circle2"><div class="pulse"></div></div>
            </div>
            <h1 class="scrollreveal-final-txt">Welcome to Apollo Proposals!</h1>
            <p>Power Your Team. Perfect Your Proposals.</p>
            <a href="#login"><button class="generate-button">Generate Proposal</button></a>
          </div>
        </div>
      </section>

      <section class="about" id="about">
        <div class="interface">
          <div class="flex">
            <div class="icon">
              <img src="/resources/favicon.png" alt="Our Logo" />
            </div>
            <div class="txt">
              <h2 class="scrollreveal-final-txt">About us</h2>
              <p>At Apollo Proposals, we’re a team of four passionate developers building a powerful web application designed to streamline the way teams manage and respond to Requests for Proposals (RFPs).</p>
              <p>Our platform empowers businesses to organize, collaborate, and deliver winning proposals with greater speed, clarity, and consistency.</p>
              <p>We believe that smart tools create smarter workflows—and we're here to make proposal management simpler, faster, and more effective.</p>
            </div>
          </div>
        </div>
      </section>

      <section class="login-register" id="login">
        <div class="interface">
          <div class="flex">

            <div class="content">
              <div class="login-form">
                <h2>Login</h2>
                <form id="login-form" action="javascript:void(0)">
                  <input type="email" id="login-email" placeholder="Email:" required />
                  <input type="password" id="login-password" placeholder="Password:" required />
                  <p>Forget your password? <a href="#">Change here</a></p>
                  <button type="submit">Login</button>
                </form>
              </div>
            </div>

            <div class="content">
              <div class="register-form">
                <h2>Register</h2>
                <form id="register-form" action="javascript:void(0)">
                  <input type="text" id="companyName" placeholder="Company Name:" required />
                  <input type="email" id="email" placeholder="Email:" required />
                  <input type="tel" id="phone" placeholder="Phone:" required />
                  <input type="password" id="password" placeholder="Create a Password:" required />
                  <button type="submit">Register</button>
                </form>
              </div>
            </div>

          </div>
        </div>
      </section>

      <section class="contact" id="contact">
        <div class="interface">
          <div class="flex">
            <div class="contact-form">
              <h2>Contact us</h2>
              <form action="javascript:void(0)">
                <input type="text" placeholder="Name:" required />
                <input type="email" placeholder="Email:" required />
                <input type="number" placeholder="Phone:" />
                <textarea placeholder="Message:"></textarea>
                <button type="submit">Send</button>
              </form>
            </div>
          </div>
        </div>
      </section>
    </main>

     <footer>
        <div class="interface">
          <div class="footer-text scrollreveal-footer-txt">
            <p>® 2025 Apollo Proposals. All rights reserved.</p>
            <p>|</p>
            <p>Smart Proposals, Stellar Results.</p>
          </div>
          <div class="conect-icons scrollreveal-footer-icons">
            <a
              href="https://www.linkedin.com/in/gustavoparaiso/"
              target="_blank"
              aria-label="Gustavo Paraíso"
            >
              <img class="circle-img" src="resources/gustavo.png" alt="gustavo" />
            </a>
            <a
              href="https://www.linkedin.com/in/luissantos-dev/"
              target="_blank"
              aria-label="Luis Santos"
            >
              <img src="resources/luis.png" alt="luis" class="circle-img" />
            </a>
            <a
              href="https://www.linkedin.com/in/joao-parada/"
              target="_blank"
              aria-label="João Parada"
              ><img class="circle-img" src="resources/parada.png" alt="parada" />
            </a>
            <a
              href="https://www.linkedin.com/in/brunodanastacio/"
              target="_blank"
              aria-label="Bruno Anastácio"
              ><img class="circle-img" src="resources/bruno.png" alt="bruno" />
            </a>
          </div>
    </div>
    </footer>
    `;

  // Smooth scroll on hash load
  if (window.location.hash) {
    const targetId = window.location.hash.substring(1);
    const targetElement = document.getElementById(targetId);
    if (targetElement) {
      targetElement.scrollIntoView({ behavior: "smooth" });
    }
  }

  document.getElementById('header-logo').addEventListener('click', function () {
    // Recarregar a página
    location.reload();
  });

  // Handle Register Form Submit
  const registerForm = document.getElementById("register-form");

  registerForm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const customerData = {
      companyName: document.getElementById("companyName").value.trim(),
      email: document.getElementById("email").value.trim(),
      phone: document.getElementById("phone").value.trim(),
      password: document.getElementById("password").value
    };

    const result = await registerService.registerCustomer(customerData);

    if (result && result.success !== false) {
      alert("Registration successful! You can now log in.");
      registerForm.reset();
    } else {
      alert(result?.message || "Registration failed. Please try again.");
    }
  });
  
  // Initialize login logic (attach listeners, etc.)
  loginController.init();
}

export default { renderPage };
