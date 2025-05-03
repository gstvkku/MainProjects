package project.service;

import org.springframework.stereotype.Service;
import project.dao.Interfaces.CustomerDao;
import project.dto.CustomerDto;
import project.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return mapToDto(customer);
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = mapToEntity(customerDto);
        Customer saved = customerDao.save(customer);
        return mapToDto(saved);
    }

    public void deleteCustomer(Long id) {
        if (!customerDao.existsById(id)) {
            throw new RuntimeException("Customer not found");
        }
        customerDao.deleteById(id);
    }

    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer existing = customerDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.setEmail(customerDto.getEmail());
        existing.setCompanyName(customerDto.getCompanyName());
        existing.setPhone(customerDto.getPhone()); // If needed

        return mapToDto(customerDao.save(existing));
    }

    public List<CustomerDto> getAllCustomers() {
        return customerDao.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Mapping helpers
    private CustomerDto mapToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getCompanyName(), customer.getPhone());
    }

    private Customer mapToEntity(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setEmail(dto.getEmail());
        customer.setCompanyName(dto.getCompanyName());
        customer.setPhone(dto.getPhone());
        customer.setPassword(dto.getPassword());
        return customer;
    }

    public CustomerDto login(String email, String password) {
        List<Customer> customers = customerDao.findAll();

        for (Customer customer : customers) {
            if (customer.getEmail().equals(email) && customer.getPassword().equals(password)) {
                return mapToDto(customer);
            }
        }

        return null;
    }
}