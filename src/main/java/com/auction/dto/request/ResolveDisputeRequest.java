package com.auction.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResolveDisputeRequest {
    @NotBlank(message = "Resolution action is required")
    private String action; // REFUND, RELEASE, DISMISS

    private String resolutionNotes;
}
