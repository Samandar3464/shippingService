package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.model.request.AnnouncementDriverDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AnnouncementDriver {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private UUID id;

     private double currentLatitude;

     private double currentLongitude;

     private String info;

     private boolean active;

     private boolean deleted;

     private boolean goAnotherRegion;

     private LocalDateTime createdTime;

     @ManyToOne
     private Region currentRegion;

     @ManyToOne
     private City currentCity;

     @ManyToOne
     @OnDelete(action = OnDeleteAction.CASCADE)
     private User user;

     @ManyToOne
     private Car car;

     public static AnnouncementDriver from(AnnouncementDriverDto announcementRequestDto) {
          return AnnouncementDriver.builder()
                  .currentLatitude(announcementRequestDto.getCurrentLatitude())
                  .currentLongitude(announcementRequestDto.getCurrentLongitude())
                  .info(announcementRequestDto.getInfo())
                  .createdTime(LocalDateTime.now())
                  .goAnotherRegion(announcementRequestDto.isGoAnotherRegion())
                  .active(true)
                  .deleted(false)
                  .build();
     }



}
