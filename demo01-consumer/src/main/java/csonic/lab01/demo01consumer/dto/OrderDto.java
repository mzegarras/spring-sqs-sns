package csonic.lab01.demo01consumer.dto;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private UUID id;
    private String customerId;

    public UUID getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }
}

