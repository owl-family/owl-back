package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeReview is a Querydsl query type for CodeReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeReview extends EntityPathBase<CodeReview> {

    private static final long serialVersionUID = -1832225618L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeReview codeReview = new QCodeReview("codeReview");

    public final QCodeLanguage codeLanguage;

    public final QCodeScope codeScope;

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final DateTimePath<java.time.Instant> createdDate = createDateTime("createdDate", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> modifiedDate = createDateTime("modifiedDate", java.time.Instant.class);

    public final QStudyGroup studyGroup;

    public final StringPath title = createString("title");

    public final NumberPath<Integer> versionCount = createNumber("versionCount", Integer.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public final QUser writer;

    public QCodeReview(String variable) {
        this(CodeReview.class, forVariable(variable), INITS);
    }

    public QCodeReview(Path<? extends CodeReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeReview(PathMetadata metadata, PathInits inits) {
        this(CodeReview.class, metadata, inits);
    }

    public QCodeReview(Class<? extends CodeReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeLanguage = inits.isInitialized("codeLanguage") ? new QCodeLanguage(forProperty("codeLanguage")) : null;
        this.codeScope = inits.isInitialized("codeScope") ? new QCodeScope(forProperty("codeScope")) : null;
        this.studyGroup = inits.isInitialized("studyGroup") ? new QStudyGroup(forProperty("studyGroup"), inits.get("studyGroup")) : null;
        this.writer = inits.isInitialized("writer") ? new QUser(forProperty("writer")) : null;
    }

}

