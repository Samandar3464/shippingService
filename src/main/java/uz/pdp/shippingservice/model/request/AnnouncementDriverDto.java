package uz.pdp.shippingservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDriverDto {

     private Integer currentRegionId;

     private Integer currentCityId;

     private double currentLatitude;

     private double currentLongitude;

     private String info;

}
