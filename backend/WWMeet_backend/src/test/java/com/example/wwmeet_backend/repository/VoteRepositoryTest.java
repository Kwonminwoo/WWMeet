package com.example.wwmeet_backend.repository;

import com.example.wwmeet_backend.vote.domain.Vote;
import com.example.wwmeet_backend.vote.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;


@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.ANY)
@TestPropertySource("classpath:application-test.properties") //test용 properties 파일 설정
@DataJpaTest
class VoteRepositoryTest {
    @Autowired
    private VoteRepository voteRepository;
    @Test
    void saveVoteSchedule(){
        Vote vote = new Vote(1L, null, null);

        Vote savedVote = voteRepository.save(vote);
        Assertions.assertThat(savedVote.getId()).isEqualTo(vote.getId());
    }
}