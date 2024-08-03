package com.damyo.alpha.api.smokingarea.controller.dto;

import java.math.BigDecimal;

public record SearchLocateRequest(
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal range,
        Boolean status,
        Boolean opened,
        Boolean closed,
        Boolean indoor,
        Boolean outdoor
) {
}
