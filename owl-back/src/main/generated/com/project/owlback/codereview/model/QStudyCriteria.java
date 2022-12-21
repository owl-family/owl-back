package com.project.owlback.codereview.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudyCriteria is a Querydsl query type for StudyCriteria
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStudyCriteria extends EntityPathBase<StudyCriteria> {

    private static final long serialVersionUID = 726148767L;

    public static final QStudyCriteria studyCriteria = new QStudyCriteria("studyCriteria");

    public final StringPath description = createString("description");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public QStudyCriteria(String variable) {
        super(StudyCriteria.class, forVariable(variable));
    }

    public QStudyCriteria(Path<? extends StudyCriteria> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudyCriteria(PathMetadata metadata) {
        super(StudyCriteria.class, metadata);
    }

}

