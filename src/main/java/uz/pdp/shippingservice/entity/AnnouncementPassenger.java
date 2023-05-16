package uz.pdp.shippingservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.pdp.shippingservice.model.request.AnnouncementPassengerDto;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AnnouncementPassenger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    private Region fromRegion;
    @ManyToOne
    private Region toRegion;

    @ManyToOne
    private City fromCity;
    @ManyToOne
    private City toCity;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private double fromLatitude;

    private double fromLongitude;

    private double toLongitude;

    private double toLatitude;

    private boolean active;

    private boolean deleted;

    private LocalDateTime timeToSend;

    private LocalDateTime createdTime;

    private String info;

    private double price;

    public static AnnouncementPassenger from(AnnouncementPassengerDto announcementRequestDto) {
        return AnnouncementPassenger.builder()
                .fromLatitude(announcementRequestDto.getFromLatitude())
                .fromLongitude(announcementRequestDto.getFromLongitude())
                .toLatitude(announcementRequestDto.getToLatitude())
                .toLongitude(announcementRequestDto.getToLongitude())
                .timeToSend(announcementRequestDto.getTimeToSend())
                .info(announcementRequestDto.getInfo())
                .createdTime(LocalDateTime.now())
                .price(announcementRequestDto.getPrice())
                .active(true)
                .deleted(false)
                .build();
    }
}
