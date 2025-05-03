package project.dao.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Request;

import java.util.List;

public interface RequestDao extends JpaRepository<Request, Long> {
    // Find requests by customer ID
    List<Request> findByCustomerId(Long customerId);

    // Find requests by sector
    List<Request> findBySector(String sector);

    // Example of a custom query to find requests based on approval status
    List<Request> findByEvaluation(Double evaluation);

    // Other custom queries can go here, for example:
    // List<Request> findByCustomerIdAndApproved(Long customerId, Boolean approved);
}
