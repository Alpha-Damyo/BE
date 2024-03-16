package com.damyo.alpha.dto;

import java.util.List;

public record SmokingDatasRequest(
        List<SmokingDataRequest> dataRequests
) {
}
