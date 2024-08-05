package com.damyo.alpha.api.smokingarea.controller.dto;

public record SearchQueryRequest(
        String word,
        Boolean status,
        Boolean opened,
        Boolean closed,
        Boolean indoor,
        Boolean outdoor
) {
}
