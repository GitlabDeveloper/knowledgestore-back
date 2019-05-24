package com.eklib.knowledgestore.repository;

import com.eklib.knowledgestore.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOptionRepository extends JpaRepository<Role, Long> {

}
