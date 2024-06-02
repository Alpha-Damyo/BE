package com.damyo.alpha.api.contest.service;

import com.damyo.alpha.api.picture.domain.Picture;
import com.damyo.alpha.api.picture.domain.PictureRepository;
import com.damyo.alpha.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContestService {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final PictureRepository pictureRepository;
    private final UserService userService;

    @Scheduled(cron = "0 18 18 2 6 ?", zone = "Asia/Seoul")
    public void calculateContribution() throws Exception {
        LocalDate now = LocalDate.now();
        int endYear = now.getYear();
        int endMonth = now.getMonthValue();
        int startYear = endMonth == 1 ? endYear - 1 : endYear;
        int startMonth = (endMonth + 9) % 12;
        LocalDateTime startDate = LocalDateTime.of(startYear, startMonth, 1, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(endYear, endMonth, 3, 0, 0, 0, 0);
        JobParameters jobParameters = new JobParametersBuilder()
                .addLocalDateTime("start_date", startDate)
                .addLocalDateTime("end_date", endDate)
                .addLocalDateTime("now", LocalDateTime.now())
                .toJobParameters();
        jobLauncher.run(job, jobParameters);

        List<Picture> top3Pictures = pictureRepository.findTop2ByCreatedAtBetweenOrderByLikesDesc(startDate, endDate);
        userService.updateContribution(top3Pictures.get(0).getUser().getId(), 300);
        userService.updateContribution(top3Pictures.get(1).getUser().getId(), 150);
        // userService.updateContribution(top3Pictures.get(2).getUser().getId(), 50);
    }
}
