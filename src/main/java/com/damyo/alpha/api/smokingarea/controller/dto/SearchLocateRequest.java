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
        Boolean dirty,
        Boolean airOut,
        Boolean noExist,
        Boolean indoor,
        Boolean outdoor,
        Boolean big,
        Boolean small,
        Boolean crowded,
        Boolean quite,
        Boolean chair
) {
}
