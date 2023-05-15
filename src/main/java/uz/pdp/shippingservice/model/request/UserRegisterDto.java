package uz.pdp.shippingservice.model.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.shippingservice.entity.enums.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDto {

    private MultipartFile profilePhoto;

    private String fullName;

    @NotBlank
    @Size(min = 9, max = 9)
    private String phone;

    private LocalDate birthDate;

    @NotBlank
    @Size(min = 6)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
