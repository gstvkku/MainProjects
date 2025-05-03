function renderPage() {
  const container = document.getElementById("container");

  // Retrieve the logged-in user's name from localStorage
  const companyName = localStorage.getItem("companyName") || "User Name"; // Default to "User" if not found

  container.innerHTML = `
    <header class="scrollreveal-header">
      <div class="interface">
        <div class="logo">
          <a href="homeView.html">
            <img class="logo" src="/resources/apollo-logo.png" alt="logo" />
          </a>
        </div>

        <nav class="menu-desktop">
          <ul>
            <li><a href="/home">Home</a></li>
            <li><a href="/history">History</a></li>
            <li><a href="/" class="logout-link">LogOut</a></li>
            <li>
              <a href="/request">
                <button class="generate-button">Generate Proposal</button>
              </a>
            </li>
          </ul>
        </nav>
      </div>
    </header>

    <main>
      <section class="home">
        <div class="interface">
          <div class="flex scrollreveal-final-box">
            <div class="circles-container">
              <div class="circle1">
                <div class="pulse"></div>
              </div>
              <div class="circle2">
                <div class="pulse"></div>
              </div>
            </div>
            <h1 class="scrollreveal-final-txt">Welcome, ${companyName}!</h1>
            <p>Power Your Team. Perfect Your Proposals.</p>
            <a href="/request">
              <button class="generate-button">Generate Proposal</button>
            </a>
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
}

export default { renderPage };
