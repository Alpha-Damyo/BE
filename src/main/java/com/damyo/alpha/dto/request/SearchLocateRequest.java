package com.damyo.alpha.dto.request;

import java.math.BigDecimal;

public record SearchLocateRequest(
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal range
) {
}
