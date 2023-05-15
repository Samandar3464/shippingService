package uz.pdp.shippingservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.model.request.CarRegisterRequestDto;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String carNumber;

    private String color;

    private String texPassport;

    @OneToMany
    private List<Attachment> autoPhotos;

    @OneToOne
    private Attachment texPassportPhoto;

    @OneToOne
    private Attachment photoDriverLicense;

    @JsonIgnore
    @ManyToOne
    private User user;

    private boolean active;

    public static Car from(CarRegisterRequestDto carRegisterRequestDto) {
        return Car.builder()
                .color(carRegisterRequestDto.getColor())
                .texPassport(carRegisterRequestDto.getTexPassport())
                .carNumber(carRegisterRequestDto.getCarNumber())
                .active(true)
                .build();
    }

}
