package project.dao.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Long> {
    // You can add custom queries if needed, for example:
    // List<Customer> findByEmail(String email);
}