package com.alextim.batch;

import com.alextim.domain.nosql.AuthorNosql;
import com.alextim.domain.relational.AuthorRelation;
import com.alextim.repository.nosql.AuthorNosqlRepository;
import com.alextim.repository.relation.AuthorRelationRepository;
import com.alextim.service.RelationToNosqlTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

@EnableBatchProcessing
@Configuration
@Slf4j
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job toNosqlJob(Step toNosqlStep, Step relationRemoveStep) {
        return jobBuilderFactory.get("toNosqlJob")
                .incrementer(new RunIdIncrementer())
                .flow(toNosqlStep)
                .next(relationRemoveStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Start job");
                    }
                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("Finish job");
                    }
                })
                .build();
    }

    @Bean
    public Step toNosqlStep(RepositoryItemReader<AuthorRelation> authorRelationRepositoryItemReader,
                            ItemProcessor toAuthorNosqlItemProcessor,
                            RepositoryItemWriter authorNosqlItemWriter) {
        return stepBuilderFactory.get("toNosqlStep")
                .chunk(5)
                .reader(authorRelationRepositoryItemReader)
                .processor(toAuthorNosqlItemProcessor)
                .writer(authorNosqlItemWriter)
                .build();
    }


    @Bean
    public RepositoryItemReader<AuthorRelation> authorRelationRepositoryItemReader(AuthorRelationRepository repository) {
        Map<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);

        return new RepositoryItemReaderBuilder<AuthorRelation>()
                .repository(repository)
                .sorts(sorts )
                .methodName("findAll")
                .saveState(false)
                .build();
    }

    @Bean
    public ItemProcessor toAuthorNosqlItemProcessor() {
        return (ItemProcessor<AuthorRelation, AuthorNosql>) RelationToNosqlTransformer::toNosqlAuthor;
    }

    @Bean
    public RepositoryItemWriter<AuthorNosql> authorNosqlItemWriter(AuthorNosqlRepository mongoAuthorRepository) {
        return new RepositoryItemWriterBuilder<AuthorNosql>()
                .repository(mongoAuthorRepository)
                .methodName("save")
                .build();
    }



    @Bean
    public Step authorRelationRemoveStep(RepositoryItemReader<AuthorRelation> authorRelationRepositoryItemReader,
                           ItemProcessor<Object, AuthorRelation> authorRelationRemoveProcessor ) {
        return stepBuilderFactory.get("authorRelationRemoveStep")
                .chunk(5)
                .reader(authorRelationRepositoryItemReader)
                .processor(authorRelationRemoveProcessor)
                .build();
    }

    @Bean
    public ItemProcessor authorRelationRemoveProcessor(AuthorRelationRepository repository) {
        return (ItemProcessor<AuthorRelation, Object>) item -> {
            repository.delete(item);
            return null;
        };
    }
}