package com.project.owlback.codereview.repository;

import com.project.owlback.codereview.model.CodeComment;
import com.project.owlback.codereview.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CodeCommentRepository extends JpaRepository<CodeComment, Long> {
    List<CodeComment> findTop1By();
    Page<CodeComment> findByWriterAndContentsContains(User writer, String word, Pageable pageable);
    @Query("select cc from CodeComment cc where cc.writer=:writer and cc.codeHistory.codeReview.title LIKE %:title%")
    Page<CodeComment> findByWriterAndCodeReviewTitle(
            @Param("writer") User writer, @Param("title") String title, Pageable pageable);

    @Query("select cc from CodeComment cc where cc.writer=:writer and cc.codeHistory.codeReview.writer.nickname LIKE %:nickName%")
    Page<CodeComment> findByWriterAndUserNickName(
            @Param("writer") User writer, @Param("nickName") String nickName, Pageable pageable);
}
