package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeHistory is a Querydsl query type for CodeHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeHistory extends EntityPathBase<CodeHistory> {

    private static final long serialVersionUID = -1137438210L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeHistory codeHistory = new QCodeHistory("codeHistory");

    public final StringPath code = createString("code");

    public final QCodeReview codeReview;

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final StringPath contents = createString("contents");

    public final DateTimePath<java.time.Instant> createdDate = createDateTime("createdDate", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> like = createNumber("like", Integer.class);

    public final DateTimePath<java.time.Instant> modifiedDate = createDateTime("modifiedDate", java.time.Instant.class);

    public final StringPath subTitle = createString("subTitle");

    public final NumberPath<Integer> versionNum = createNumber("versionNum", Integer.class);

    public QCodeHistory(String variable) {
        this(CodeHistory.class, forVariable(variable), INITS);
    }

    public QCodeHistory(Path<? extends CodeHistory> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeHistory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeHistory(PathMetadata metadata, PathInits inits) {
        this(CodeHistory.class, metadata, inits);
    }

    public QCodeHistory(Class<? extends CodeHistory> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeReview = inits.isInitialized("codeReview") ? new QCodeReview(forProperty("codeReview"), inits.get("codeReview")) : null;
    }

}

