package com.coe.kafkaconsumer.repository;

import com.coe.kafkaconsumer.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, Integer> {
}
