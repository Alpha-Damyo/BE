package com.damyo.alpha.api.contest.domain;

import com.damyo.alpha.api.picture.domain.Picture;
import com.damyo.alpha.api.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ContestContributionInfo {
    private User user;
    private Long contestContribution;
    private Picture picture;
}
