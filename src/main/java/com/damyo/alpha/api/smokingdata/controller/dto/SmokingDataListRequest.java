package com.damyo.alpha.api.smokingdata.controller.dto;

import java.util.List;

public record SmokingDataListRequest(
        List<SmokingDataRequest> dataRequests
) {
}
