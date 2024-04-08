package com.damyo.alpha.dto.request;

import java.util.UUID;

public record UploadPictureRequest (
    UUID userId,
    String smokingAreaId
){
}
