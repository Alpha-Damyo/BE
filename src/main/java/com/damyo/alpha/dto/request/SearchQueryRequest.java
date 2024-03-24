package com.damyo.alpha.dto.request;

public record SearchQueryRequest(
        String word,
        boolean status,
        boolean opened,
        boolean closed,
        boolean hygeine,
        boolean airOut
) {
}
