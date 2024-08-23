package com.damyo.alpha.api.user.controller.dto;

public record ReportRequest(
        Boolean notExist,
        Boolean incorrectTag,
        Boolean incorrectLocation,
        Boolean inappropriateWord,
        Boolean inappropriatePicture,
        String otherSuggestions

) {
}
