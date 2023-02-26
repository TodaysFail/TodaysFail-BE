package com.todaysfailbe.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todaysfailbe.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Boolean existsByName(String name);

	Optional<Member> findByName(String writer);
}
