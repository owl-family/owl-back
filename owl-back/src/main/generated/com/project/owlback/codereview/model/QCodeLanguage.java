package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCodeLanguage is a Querydsl query type for CodeLanguage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCodeLanguage extends EntityPathBase<CodeLanguage> {

    private static final long serialVersionUID = -1184623826L;

    public static final QCodeLanguage codeLanguage = new QCodeLanguage("codeLanguage");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QCodeLanguage(String variable) {
        super(CodeLanguage.class, forVariable(variable));
    }

    public QCodeLanguage(Path<? extends CodeLanguage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCodeLanguage(PathMetadata metadata) {
        super(CodeLanguage.class, metadata);
    }

}

