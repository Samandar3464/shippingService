package uz.pdp.shippingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.User;
import uz.pdp.shippingservice.entity.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private UUID id;

    private String fullName;

    private String phone;

    private int age;

    private double status;

    private Gender gender;

    private String profilePhotoUrl;


    public static UserResponseDto from(User user) {
        double status= (double) (user.getStatus().getStars()) /user.getStatus().getCount();
        return UserResponseDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .age(LocalDate.now().getYear() - user.getBirthDate().getYear())
                .gender(user.getGender())
                .status(status)
                .build();
    }
}
