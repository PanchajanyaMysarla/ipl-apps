package com.ipl.ipldashboard.data;

import com.ipl.ipldashboard.model.Match;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Bean
    public FlatFileItemReader<Match> reader(){

        return new FlatFileItemReaderBuilder<Match>()
                .name("matchItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .linesToSkip(1)
                .delimited()
                .names("id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision","winner","result","result_margin","eliminator","method","umpire1","umpire2")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Match>(){{
                    setTargetType(Match.class);
                }})
                .build();
    }

    @Bean
    public MatchDataProcessor processor(){
        return new MatchDataProcessor();
    }


    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)")
                .dataSource(dataSource)
                .build();
    }


    @Bean
    public Job importMatchData(Step step1){

        return jobBuilderFactory
                .get("importMatchData")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();

    }

    @Bean
    public Step step1(){
        return stepBuilderFactory
                .get("step1")
                .chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(null)
                .build();
    }

}