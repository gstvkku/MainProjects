const customerId = localStorage.getItem("customerId"); // Get customerId from localStorage

const proposalService = {
  async submitProposal(formElement) {
    const formData = new FormData(formElement);
    const jsonData = {};

    for (const [key, value] of formData.entries()) {
      jsonData[key] = value;
    }

    jsonData.id = null;
    jsonData.response = null;
    jsonData.approved = null;
    jsonData.customerId = customerId;

    try {
      const loadingOverlay = document.createElement("div");
      loadingOverlay.className = "loading-overlay";

      // Add a popup-style content box
      loadingOverlay.innerHTML = `
      <div class="loading-popup">
      <p>Loading proposal...</p>
      <div class="spinner"></div>
      </div>
      `;

      // Append it to the document body
      document.body.appendChild(loadingOverlay);

      const response = await fetch(
        `http://localhost:8080/apollo-proposals/api/ai/${customerId}/generate-proposal`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(jsonData),
          mode: "cors",
        }
      );

      loadingOverlay.remove();

      if (!response.ok) {
        const error = await response.text();
        throw new Error(error);
      }

      const result = await response.json();

      console.log(result);

      this.showModal(result.response, result.requestId);

      formElement.reset();

      return result;
    } catch (error) {
      alert("Error sending proposal: " + error.message);
    }
  },

  showModal(responseText, requestId) {
    const modalHTML = `
      <div class="custom-modal-overlay">
        <div class="custom-modal">
          <h2>Proposal Generated Successfully</h2>
          <div class="modal-content">
            <p>${responseText}</p>
            <div class="icon">
              <img src="/resources/favicon.png" alt="Our Logo" />
            </div>
          </div>
          <div class="modal-actions">
            <button id="rate-btn">Evaluate Proposal</button>
            <button id="download-pdf-btn">Download PDF</button>
          </div>
          <div id="rating-popup" class="rating-popup hidden">
            <h3>Rate this Proposal</h3>
            <div class="stars">
              ${[1, 2, 3, 4, 5]
                .map(
                  (num) => `<span class="star" data-rating="${num}">★</span>`
                )
                .join("")}
            </div>
            <textarea id="comment-box" placeholder="Additional comments..."></textarea>
            <button id="submit-rating">Submit Review</button>
          </div>
        </div>
      </div>
    `;

    document.body.insertAdjacentHTML("beforeend", modalHTML);

    document.getElementById("rate-btn").addEventListener("click", () => {
      document.getElementById("rating-popup").classList.remove("hidden");
    });

    document.querySelectorAll(".star").forEach((star) => {
      star.addEventListener("click", () => {
        const rating = star.dataset.rating;
        document.querySelectorAll(".star").forEach((s) => {
          s.style.color = s.dataset.rating <= rating ? "#f5b301" : "#ccc";
        });
        document.getElementById("rating-popup").dataset.rating = rating;
      });
    });

    document.getElementById("submit-rating").addEventListener("click", () => {
      const rating =
        parseInt(document.getElementById("rating-popup").dataset.rating) || 0;
      if (rating === 0) {
        alert("Please select a rating before submitting.");
        return;
      }

      const comment = document.getElementById("comment-box").value;
      this.submitRate(requestId, rating); // Updated fetch logic here
      alert(
        `Thank you for your feedback!\nRating: ${rating} stars\nComment: ${comment}`
      );
      document.getElementById("rating-popup").classList.add("hidden");
      this.closeModal();
    });

    document.getElementById("download-pdf-btn").addEventListener("click", () => {
      const { jsPDF } = window.jspdf;
      const pdf = new jsPDF();
    
      const title = "✅ Proposal Generated Successfully";
      const content = responseText; // Replace this with your actual content
      pdf.setFont("helvetica", "bold");
      pdf.setFontSize(16);
      pdf.setTextColor(40, 40, 40);
      pdf.text(title, 10, 20);
    
      pdf.setFont("times", "normal");
      pdf.setFontSize(12);
      pdf.setTextColor(60, 60, 60);
    
      const marginLeft = 10;
      const marginTop = 35; // Start position on the page
      const lineHeight = 10; // Height of each line
      const pageHeight = pdf.internal.pageSize.getHeight();
      const pageWidth = pdf.internal.pageSize.getWidth();
      const maxLineWidth = pageWidth - marginLeft * 2;
      const lines = pdf.splitTextToSize(content, maxLineWidth);
    
      let cursorY = marginTop;
    
      // Loop through each line and add it to the PDF
      lines.forEach((line) => {
        if (cursorY + lineHeight > pageHeight - 10) {
          // If the text exceeds the page height, add a new page
          pdf.addPage();
          cursorY = 10; // Reset the cursor position for the new page
        }
        pdf.text(line, marginLeft, cursorY);
        cursorY += lineHeight; // Move the cursor down
      });
    
      pdf.save("proposal_response.pdf");
    });
  },

  closeModal() {
    const modalOverlay = document.querySelector(".custom-modal-overlay");
    if (modalOverlay) modalOverlay.remove();
  },

  submitRate(requestId, rating) {
    fetch(
      `http://localhost:8080/apollo-proposals/api/requests/${requestId}/${rating}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
  },
};

export default proposalService;
