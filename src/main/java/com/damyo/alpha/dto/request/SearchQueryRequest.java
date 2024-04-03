package com.damyo.alpha.dto.request;

public record SearchQueryRequest(
        String word,
        Boolean status,
        Boolean opened,
        Boolean closed,
        Boolean hygeine,
        Boolean airOut,
        Boolean indoor,
        Boolean outdoor,
        Boolean big,
        Boolean small,
        Boolean crowded,
        Boolean quite,
        Boolean chair
) {
}
