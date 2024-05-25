package com.damyo.alpha.api.smokingarea.controller.dto;

import java.math.BigDecimal;

public record SearchLocateRequest(
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal range,
        Boolean status,
        Boolean opened,
        Boolean closed,
        Boolean hygiene,
        Boolean indoor,
        Boolean outdoor,
        Boolean chair
) {
}
