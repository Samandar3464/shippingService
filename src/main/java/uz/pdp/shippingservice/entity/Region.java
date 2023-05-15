package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.shippingservice.model.request.RegionRegisterRequestDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    public static Region from(RegionRegisterRequestDto regionRegisterRequestDto){
        return Region.builder().name(regionRegisterRequestDto.getName()).build();
    }
}
