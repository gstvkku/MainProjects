package project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.dto.RequestDto;
import project.service.RequestService;
import project.model.Request;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    // Create a new Request (POST)
    @PostMapping
    public ResponseEntity<RequestDto> createRequest(@RequestBody RequestDto requestDto) {
        RequestDto createdRequest = requestService.createRequest(requestDto);
        return ResponseEntity.ok(createdRequest);
    }

    // Get a Request by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Long id) {
        RequestDto request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<RequestDto>> getRequestsByCustomer(@PathVariable Long customerId) {
        List<RequestDto> customerRequests = requestService.getRequestsByUser(customerId);
        return ResponseEntity.ok(customerRequests);
    }

    // Get all Requests (GET)
    @GetMapping
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    // Update a Request by ID (PUT)
    @PutMapping("/{id}/{evaluation}")
    public ResponseEntity<RequestDto> updateRequest(@PathVariable Long id, @PathVariable Double evaluation) {

        RequestDto requestDto = requestService.getRequestById(id);
        requestDto.setEvaluation(evaluation);

        RequestDto updatedRequest = requestService.updateRequest(id, requestDto);

        return ResponseEntity.ok(updatedRequest);
    }

    // Delete a Request by ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}