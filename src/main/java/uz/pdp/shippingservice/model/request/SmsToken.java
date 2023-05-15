package uz.pdp.shippingservice.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.shippingservice.entity.Token;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsToken {

    private String message;

    @JsonProperty("data")
    private Token data;

    private String token_type;
}
