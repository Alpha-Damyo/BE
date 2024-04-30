package com.damyo.alpha.api.picture.controller.dto;

import java.util.UUID;

public record UploadPictureRequest (
    UUID userId,
    String smokingAreaId
){
}
