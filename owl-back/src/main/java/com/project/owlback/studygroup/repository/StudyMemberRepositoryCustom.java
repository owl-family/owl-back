package com.project.owlback.studygroup.repository;

import com.project.owlback.studygroup.model.StudyMember;
import com.project.owlback.studygroup.dto.StudyGroupCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.project.owlback.studygroup.model.QStudyMember.studyMember;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class StudyMemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Optional<StudyMember> getStudyMember(StudyGroupCondition condition) {
        Optional<StudyMember> member = Optional.ofNullable(queryFactory.selectFrom(studyMember)
                .where(studyIdAndUserIdEq(condition.getStudyId(), condition.getUserId()),
                        descriptionEq(condition.getDescription()))
                .fetchOne());
        return member;
    }

    private BooleanExpression studyIdAndUserIdEq(long studyId, long userId) {
        return studyMember.studyGroup.studyGroupId.eq(studyId)
                .and(studyMember.user.userId.eq(userId));
    }

    private BooleanExpression studyIdEq(long studyId) {
        return studyId != 0 ? studyMember.studyGroup.studyGroupId.eq(studyId) : null;
    }

    private BooleanExpression userIdEq(long userId) {
        return userId != 0 ? studyMember.studyGroup.studyGroupId.eq(userId) : null;
    }

    private BooleanExpression descriptionEq(String description) {
        return hasText(description) ? studyMember.memberStatus.description.eq(description) : null;
    }
}
