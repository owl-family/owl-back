package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyJoinProcess is a Querydsl query type for StudyJoinProcess
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyJoinProcess extends EntityPathBase<StudyJoinProcess> {

    private static final long serialVersionUID = 819463845L;

    public static final QStudyJoinProcess studyJoinProcess = new QStudyJoinProcess("studyJoinProcess");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QStudyJoinProcess(String variable) {
        super(StudyJoinProcess.class, forVariable(variable));
    }

    public QStudyJoinProcess(Path<? extends StudyJoinProcess> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyJoinProcess(PathMetadata metadata) {
        super(StudyJoinProcess.class, metadata);
    }

}

