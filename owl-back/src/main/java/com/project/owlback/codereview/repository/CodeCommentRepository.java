package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.SQLException;
import java.util.List;

public interface CodeCommentRepository extends JpaRepository<CodeComment, Long> {


    @Query("select c " +
            "from CodeComment c " +
            "where c.codeHistory.id =:id " +
            "order by c.parent, c.createdDate ")
    Page<CodeComment> findByCodeHistoryId(@Param("id") int id, Pageable pageable) throws SQLException;

    @Query("select c " +
            "from CodeComment c " +
            "where c.codeHistory.id =:id " +
            "and c.startLine =:startLine " +
            "order by c.parent, c.createdDate ")
    Page<CodeComment> findByCodeHistoryIdAndStartLine(@Param("id") int id, @Param("startLine") int startLine, Pageable pageable) throws SQLException;

}
