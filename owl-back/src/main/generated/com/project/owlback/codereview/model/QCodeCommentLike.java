package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCodeCommentLike is a Querydsl query type for CodeCommentLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeCommentLike extends EntityPathBase<CodeCommentLike> {

    private static final long serialVersionUID = -397856000L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCodeCommentLike codeCommentLike = new QCodeCommentLike("codeCommentLike");

    public final QCodeComment codeComment;

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QUser user;

    public QCodeCommentLike(String variable) {
        this(CodeCommentLike.class, forVariable(variable), INITS);
    }

    public QCodeCommentLike(Path<? extends CodeCommentLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCodeCommentLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCodeCommentLike(PathMetadata metadata, PathInits inits) {
        this(CodeCommentLike.class, metadata, inits);
    }

    public QCodeCommentLike(Class<? extends CodeCommentLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.codeComment = inits.isInitialized("codeComment") ? new QCodeComment(forProperty("codeComment"), inits.get("codeComment")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

