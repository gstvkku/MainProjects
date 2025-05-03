const historyService = {
  async fetchProposals() {
    try {
      const customerId = localStorage.getItem("customerId");
      if (!customerId) {
        throw new Error("User ID not found");
      }

      const response = await fetch(
        `http://localhost:8080/apollo-proposals/api/requests/customer/${customerId}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          }
        }
      );

      if (!response.ok) {
        throw new Error("Failed to fetch proposals");
      }

      const proposals = await response.json();
      return proposals;

    } catch (error) {
      console.error("Error fetching proposals:", error);
      return [];
    }
  },

  async deleteProposal(requestId) {
    try {
      const response = await fetch(
        `http://localhost:8080/apollo-proposals/api/requests/${requestId}`,
        {
          method: "DELETE"
        }
      );

      if (!response.ok) {
        throw new Error("Failed to delete proposal");
      }

      return true;

    } catch (error) {
      console.error("Error deleting proposal:", error);
      return false;
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
            <button id="goback-btn">Go back</button>
            <button id="download-pdf-btn">Download PDF</button>
          </div>
        </div>
      </div>
    `;

    document.body.insertAdjacentHTML("beforeend", modalHTML);

    // Handle the "Reply Later" button click
    document.getElementById("goback-btn").addEventListener("click", () => {
      this.closeModal();
    });

    // Handle the "Download PDF" button click
    document.getElementById("download-pdf-btn").addEventListener("click", () => {
      const { jsPDF } = window.jspdf;
      const pdf = new jsPDF();
      const title = "✅ Proposal Generated Successfully";
      const content = responseText;
      pdf.setFont("helvetica", "bold");
      pdf.setFontSize(16);
      pdf.setTextColor(40, 40, 40);
      pdf.text(title, 10, 20);
      pdf.setFont("times", "normal");
      pdf.setFontSize(12);
      pdf.setTextColor(60, 60, 60);
      const marginLeft = 10;
      const marginRight = 10;
      const pageWidth = pdf.internal.pageSize.getWidth();
      const maxLineWidth = pageWidth - marginLeft - marginRight;
      const lines = pdf.splitTextToSize(content, maxLineWidth);
      pdf.text(lines, marginLeft, 35);
      pdf.save("proposal_response.pdf");
    });
  },

  closeModal() {
    const modalOverlay = document.querySelector(".custom-modal-overlay");
    if (modalOverlay) modalOverlay.remove();
  }
};

export default historyService;