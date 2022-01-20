package com.maciejBigos.poAwarii.repository;

import com.maciejBigos.poAwarii.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select m from messages m where sender = :var1 and recipient = :var2" )
    Stream<Message> findAllBySenderAndByRecipient(String var1, String var2);
}
