package project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String email;
    private String password;
    private Long phone;
    private String companyName;

    public CustomerDto(Long id, String email, String companyName, Long phone) {
        this.id = id;
        this.email = email;
        this.companyName = companyName;
        this.phone = phone;
    }
}