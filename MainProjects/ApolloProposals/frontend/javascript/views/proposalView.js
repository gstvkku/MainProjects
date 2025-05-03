import proposalService from "../services/proposalService.js";

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
                    <li><a href="/" class="logout-link">Logout</a></li>
                    <li>
                      <a href="proposal.html">
                        <button class="generate-button">Generate Proposal</button>
                      </a>
                    </li>
                </ul>
            </nav>
        </div>
    </header>

    <main>
        <section class="proposal-form">
            <div class="interface" id="proposal-interface">
              <div class="form-box">
                <h2>Submit New Proposal</h2>
                <form id="proposal-form">
                  <input type="text" name="projectName" placeholder="Company Name?" required />
                  <select name="sector" required>
                    <option value="">Select Sector</option>
                    <option value="Architecture">Architecture</option>
                    <option value="Sales">Sales</option>
                    <option value="Marketing">Marketing</option>
                    <option value="Shoes">Shoes</option>
                    <option value="Car Dealer">Car Dealer</option>
                    <option value="Healthcare">Healthcare</option>
                    <option value="Education">Education</option>
                    <option value="Technology">Technology</option>
                  </select>
                  <textarea name="generalDescription" placeholder="General Description" required></textarea>
                  <textarea name="problems" placeholder="Problems to Solve" required></textarea>
                  <textarea name="typeOfServices" placeholder="Type of Services" required></textarea>
                  <textarea name="expectedFeatures" placeholder="Expected Features" required></textarea>
                  <textarea name="preferredTechnologies" placeholder="Preferred Technologies" required></textarea>
                  <textarea name="restrictionsOrRequests" placeholder="Restrictions or Requests"></textarea>
                  <input type="number" name="budget" placeholder="Budget" required />
                  <div class="date-input-container">
                    <span class="placeholder">Deadline</span>
                    <input type="date" name="deadline" required />
                  </div>
                  <input type="file" name="files" multiple />
                  <textarea name="additionalComments" placeholder="Additional Comments"></textarea>
                  <button type="submit">Submit</button>
                </form>
                <div id="result-message"></div>
              </div>
            </div>
        </section>
    </main>
  `;

  setTimeout(() => {
    const form = document.getElementById("proposal-form");
    const resultMessage = document.getElementById("result-message");

    if (form) {
      form.addEventListener("submit", async function (e) {
        e.preventDefault();

        // Clear previous messages
        resultMessage.textContent = "";
        resultMessage.className = "";

        try {
          // Additional validation (if necessary)
          const budget = form.querySelector('input[name="budget"]').value;
          if (budget <= 0) {
            resultMessage.textContent = "The budget must be greater than zero.";
            resultMessage.className = "error-message";
            return;
          }

          const deadline = new Date(form.querySelector('input[name="deadline"]').value);
          if (deadline < new Date()) {
            resultMessage.textContent = "The deadline must be in the future.";
            resultMessage.className = "error-message";
            return;
          }

          // Send the proposal
          await proposalService.submitProposal(form);

          // Display success message 
          resultMessage.className = "success-message";

          // Reset the form
          form.reset();
        } catch (error) {
          // Display error message
          resultMessage.textContent = "Error submitting proposal: " + error.message;
          resultMessage.className = "error-message";
        }
      });
    }
  }, 0);
}


export default { renderPage };