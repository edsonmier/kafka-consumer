package com.coe.kafkaconsumer.repository;

import com.coe.kafkaconsumer.entity.GroupMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity, Integer> {
}
