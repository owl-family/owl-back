package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStudyGroup is a Querydsl query type for StudyGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyGroup extends EntityPathBase<StudyGroup> {

    private static final long serialVersionUID = -1364592833L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStudyGroup studyGroup = new QStudyGroup("studyGroup");

    public final DateTimePath<java.time.Instant> createdDate = createDateTime("createdDate", java.time.Instant.class);

    public final StringPath criteriaDetail = createString("criteriaDetail");

    public final NumberPath<Integer> curMember = createNumber("curMember", Integer.class);

    public final StringPath goal = createString("goal");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> maxMember = createNumber("maxMember", Integer.class);

    public final StringPath name = createString("name");

    public final QStudyJoinProcess processStudyJoinProcess;

    public final QStudyCriteria studyCriteria;

    public final StringPath studyInformation = createString("studyInformation");

    public final QStudyStatus studyStatus;

    public final QUser user;

    public QStudyGroup(String variable) {
        this(StudyGroup.class, forVariable(variable), INITS);
    }

    public QStudyGroup(Path<? extends StudyGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStudyGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStudyGroup(PathMetadata metadata, PathInits inits) {
        this(StudyGroup.class, metadata, inits);
    }

    public QStudyGroup(Class<? extends StudyGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.processStudyJoinProcess = inits.isInitialized("processStudyJoinProcess") ? new QStudyJoinProcess(forProperty("processStudyJoinProcess")) : null;
        this.studyCriteria = inits.isInitialized("studyCriteria") ? new QStudyCriteria(forProperty("studyCriteria")) : null;
        this.studyStatus = inits.isInitialized("studyStatus") ? new QStudyStatus(forProperty("studyStatus")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

