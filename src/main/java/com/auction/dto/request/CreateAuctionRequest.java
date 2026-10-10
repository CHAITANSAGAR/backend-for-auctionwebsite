package com.auction.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateAuctionRequest {
    @NotBlank(message = "Item title is required")
    private String title;

    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Item condition is required")
    private String itemCondition;

    @NotNull(message = "Starting price is required")
    @DecimalMin(value = "0.01", message = "Starting price must be greater than 0")
    private BigDecimal startingPrice;

    private BigDecimal reservePrice;

    @NotNull(message = "Minimum increment is required")
    @DecimalMin(value = "1.00", message = "Increment must be at least 1.00")
    private BigDecimal minIncrement;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    private String primaryImageUrl;
}
