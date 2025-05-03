import historyService from "../services/historyService.js";

function renderPage() {
  const container = document.getElementById("container");

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
      <section class="history">
        <div class="interface">
          <div class="flex">
            <div class="history-box-container">
              <div class="history-box">
                <h2>Last Proposals</h2>
                <table class="table" id="proposals-table">
                  <thead>
                    <tr>
                      <th scope="col">Project Name</th>
                      <th scope="col">Sector</th>
                      <th scope="col">Evaluation</th>
                      <th scope="col">View Proposal</th>
                      <th scope="col">Delete</th>
                    </tr>
                  </thead>
                  <tbody></tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  `;

  // Fetch and render proposals
  setTimeout(async () => {
    try {
      const tableBody = document.querySelector("#proposals-table tbody");
      const proposals = await historyService.fetchProposals();

      if (!Array.isArray(proposals) || proposals.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="5">No proposals found.</td></tr>`;
        return;
      }

      proposals.forEach((proposal) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${proposal.projectName}</td>
          <td>${proposal.sector}</td>
          <td>${proposal.evaluation}</td>
          <td><button class="view-btn" data-id="${proposal.id}" data-response="${encodeURIComponent(proposal.response)}">View</button></td>
          <td><button class="delete-btn" data-id="${proposal.id}">Delete</button></td>
        `;
        tableBody.appendChild(row);
      });
      
      // Handle view actions
      document.querySelectorAll(".view-btn").forEach((button) => {
        button.addEventListener("click", async () => {
          const proposalId = button.getAttribute("data-id");
          const responseText = decodeURIComponent(button.getAttribute("data-response")); // Decode the response content
          historyService.showModal(responseText, proposalId);
        });
      });

      // Handle delete actions
      document.querySelectorAll(".delete-btn").forEach((button) => {
        button.addEventListener("click", async () => {
          const id = button.dataset.id;
          const confirmed = confirm("Are you sure you want to delete this proposal?");
          if (confirmed) {
            const success = await historyService.deleteProposal(id);
            if (success) {
              button.closest("tr").remove();
            } else {
              alert("Failed to delete proposal.");
            }
          }
        });
      });
    } catch (error) {
      console.error("Error loading proposals:", error);
      document.querySelector("#proposals-table tbody").innerHTML = `
        <tr><td colspan="5">Error loading proposals. Please try again later.</td></tr>
      `;
    }
  }, 0);
}

export default { renderPage };