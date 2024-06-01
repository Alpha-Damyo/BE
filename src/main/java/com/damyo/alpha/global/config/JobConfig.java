package com.damyo.alpha.global.config;

import com.damyo.alpha.api.contest.domain.ContestContributionInfo;
import com.damyo.alpha.api.picture.domain.Picture;
import com.damyo.alpha.api.user.service.UserService;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.*;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JobConfig {

    private static final int CHUNK_SIZE = 10;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final UserService userService;


    @Bean
    public Job calculateContributionJob() {
        return new JobBuilder("calculate contribution", jobRepository)
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return new StepBuilder("step", jobRepository)
                .<Picture, ContestContributionInfo>chunk(CHUNK_SIZE, transactionManager)
                .reader(jpaPagingItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<Picture> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<Picture>()
                .name("jpaPagingItemReader")
                .pageSize(CHUNK_SIZE)
                .entityManagerFactory(entityManagerFactory)
                .queryString("select new com.damyo.alpha.api.contest.domain.ContestContributionInfo(user, sum(p.likes)) " +
                             "from Picture p " +
                             "inner join p.user user " +
                             "where p.likes > 0 " +
                             "group by user.id")
                .build();
    }

    @Bean
    public ItemWriter<ContestContributionInfo> itemWriter() {
        return chunk -> {
            for (ContestContributionInfo info : chunk) {
                System.out.println(info.getUser().getName());
                System.out.println(info.getContestContribution());
                //  userService.updateContribution(info.getUserId(), info.getContestContribution());
            }
        };
    }
}
