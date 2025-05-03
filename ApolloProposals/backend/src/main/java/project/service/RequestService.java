package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.dao.Interfaces.RequestDao;
import project.dao.Interfaces.CustomerDao;
import project.dto.RequestDto;
import project.model.Request;
import project.model.Customer;
import project.repository.RequestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestDao requestDao;
    private final CustomerDao customerDao;
    private final RequestRepository requestRepository;

    // Create a new Request (includes content)
    public RequestDto createRequest(RequestDto requestDto) {
        Customer customer = customerDao.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Request request = mapToEntity(requestDto);
        return mapToDto(requestDao.save(request));
    }

    // Get Request by ID
    public RequestDto getRequestById(Long id) {
        Request request = requestDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return mapToDto(request);
    }

    // Get all Requests
    public List<RequestDto> getAllRequests() {
        return requestDao.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Update Request
    public RequestDto updateRequest(Long id, RequestDto requestDto) {
        Request existingRequest = requestDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        Customer customer = customerDao.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Update all fields, including former RequestContent fields
        existingRequest.setCustomer(customer);
        existingRequest.setSector(requestDto.getSector());
        existingRequest.setProjectName(requestDto.getProjectName());
        existingRequest.setResponse(requestDto.getResponse());
        existingRequest.setEvaluation(requestDto.getEvaluation());

        existingRequest.setGeneralDescription(requestDto.getGeneralDescription());
        existingRequest.setObjectives(requestDto.getObjectives());
        existingRequest.setProblems(requestDto.getProblems());
        existingRequest.setTypeOfServices(requestDto.getTypeOfServices());
        existingRequest.setExpectedFeatures(requestDto.getExpectedFeatures());
        existingRequest.setPreferredTechnologies(requestDto.getPreferredTechnologies());
        existingRequest.setRestrictionsOrRequests(requestDto.getRestrictionsOrRequests());
        existingRequest.setBudget(requestDto.getBudget());
        existingRequest.setDeadline(requestDto.getDeadline());
        existingRequest.setAdditionalComments(requestDto.getAdditionalComments());

        return mapToDto(requestDao.save(existingRequest));
    }

    // Delete Request
    public void deleteRequest(Long id) {
        if (!requestDao.existsById(id)) {
            throw new RuntimeException("Request not found");
        }
        requestDao.deleteById(id);
    }

    // Map Entity to DTO
    private RequestDto mapToDto(Request request) {
        return new RequestDto(
                request.getId(),
                request.getCustomer().getId(),
                request.getSector(),
                request.getProjectName(),
                request.getResponse(),
                request.getEvaluation(),
                request.getGeneralDescription(),
                request.getObjectives(),
                request.getProblems(),
                request.getTypeOfServices(),
                request.getExpectedFeatures(),
                request.getPreferredTechnologies(),
                request.getRestrictionsOrRequests(),
                request.getBudget(),
                request.getDeadline(),
                request.getAdditionalComments()
        );
    }

    // Map DTO to Entity
    private Request mapToEntity(RequestDto dto) {

        Customer customer = customerDao.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return Request.builder()
                .customer(customer)
                .sector(dto.getSector())
                .projectName(dto.getProjectName())
                .response(dto.getResponse())
                .evaluation(dto.getEvaluation())
                .generalDescription(dto.getGeneralDescription())
                .objectives(dto.getObjectives())
                .problems(dto.getProblems())
                .typeOfServices(dto.getTypeOfServices())
                .expectedFeatures(dto.getExpectedFeatures())
                .preferredTechnologies(dto.getPreferredTechnologies())
                .restrictionsOrRequests(dto.getRestrictionsOrRequests())
                .budget(dto.getBudget())
                .deadline(dto.getDeadline())
                .additionalComments(dto.getAdditionalComments())
                .build();
    }

    public List<RequestDto> getRequestsByUser(Long customerId) {
        // Fetch all requests for the user
        List<Request> customerRequests = requestRepository.findByCustomerId(customerId);
        return customerRequests.stream()
                .map(request -> new RequestDto(
                        request.getId(),
                        request.getCustomer().getId(),
                        request.getSector(),
                        request.getProjectName(),
                        request.getResponse(),
                        request.getEvaluation(),
                        request.getGeneralDescription(),
                        request.getObjectives(),
                        request.getProblems(),
                        request.getTypeOfServices(),
                        request.getExpectedFeatures(),
                        request.getPreferredTechnologies(),
                        request.getRestrictionsOrRequests(),
                        request.getBudget(),
                        request.getDeadline(),
                        request.getAdditionalComments()
                ))
                .collect(Collectors.toList());
    }

}
