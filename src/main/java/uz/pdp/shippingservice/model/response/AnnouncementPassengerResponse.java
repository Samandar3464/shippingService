package uz.pdp.shippingservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.AnnouncementPassenger;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnnouncementPassengerResponse {
    private UUID id;
    private String fromRegion;
    private String toRegion;
    private String fromCity;
    private String toCity;

    private double fromLatitude;
    private double fromLongitude;
    private double toLatitude;
    private double toLongitude;

    private String info;
    private double price;
    private String timeToSend;
    private UserResponseDto userResponseDto;

    public static AnnouncementPassengerResponse from(AnnouncementPassenger announcementPassenger, UserResponseDto userResponseDto) {
        return AnnouncementPassengerResponse.builder()
                .id(announcementPassenger.getId())
                .fromRegion(announcementPassenger.getFromRegion().getName())
                .toRegion(announcementPassenger.getToRegion().getName())
                .fromCity(announcementPassenger.getFromCity().getName())
                .toCity(announcementPassenger.getToCity().getName())
                .price(announcementPassenger.getPrice())
                .fromLatitude(announcementPassenger.getFromLatitude())
                .fromLongitude(announcementPassenger.getFromLongitude())
                .toLatitude(announcementPassenger.getToLatitude())
                .toLongitude(announcementPassenger.getToLongitude())
                .info(announcementPassenger.getInfo())
                .timeToSend(announcementPassenger.getTimeToSend().toString())
                .userResponseDto(userResponseDto)
                .build();
    }
}
