package com.shortping.repository;

import com.shortping.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByMemberEmail(String memberEmail);

    boolean existsByMemberEmail(String memberEmail);
}
