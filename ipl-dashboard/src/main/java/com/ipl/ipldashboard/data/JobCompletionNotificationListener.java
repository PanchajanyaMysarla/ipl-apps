package com.ipl.ipldashboard.data;

import com.ipl.ipldashboard.model.Match;
import com.ipl.ipldashboard.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final EntityManager entityManager;

    @Autowired
    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            Map<String, Team> teamData = new HashMap<>();



            entityManager.createQuery("select m.team1, count(*) from Match m group by m.team1")
            .getResultList()
                    .stream()
                    .map(e -> new Team((String) e[0], (long) e[1]))
                    .forEach(team -> teamData.put(((Team) team).getTeamName(),(Team) team));

//
//            jdbcTemplate.query("SELECT team1, team2, date FROM match",
//                    (rs, row) ->  "Team1"+ rs.getString(1)+ "Team2" +rs.getString(2) + "Date" +rs.getString(3)
//            ).forEach(match -> log.info("Found <" + match + "> in the database."));
        }
    }
}