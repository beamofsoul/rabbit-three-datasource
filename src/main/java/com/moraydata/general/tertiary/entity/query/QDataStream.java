package com.moraydata.general.tertiary.entity.query;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;

import com.moraydata.general.tertiary.entity.DataStream;
import com.querydsl.core.types.Path;


/**
 * QDataStream is a Querydsl query type for DataStream
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDataStream extends EntityPathBase<DataStream> {

    private static final long serialVersionUID = 910389804L;

    public static final QDataStream dataStream = new QDataStream("dataStream");

    public final StringPath author = createString("author");

    public final NumberPath<Integer> clickCount = createNumber("clickCount", Integer.class);

    public final NumberPath<Integer> commentCount = createNumber("commentCount", Integer.class);

    public final BooleanPath dataType1 = createBoolean("dataType1");

    public final BooleanPath dataType2 = createBoolean("dataType2");

    public final BooleanPath dataType3 = createBoolean("dataType3");

    public final BooleanPath dataType4 = createBoolean("dataType4");

    public final BooleanPath dataType5 = createBoolean("dataType5");

    public final DateTimePath<java.util.Date> downloadDate = createDateTime("downloadDate", java.util.Date.class);

    public final BooleanPath eventLevel = createBoolean("eventLevel");

    public final NumberPath<Double> fumianduCnt = createNumber("fumianduCnt", Double.class);

    public final BooleanPath highlyRelevant = createBoolean("highlyRelevant");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath istoilet = createBoolean("istoilet");

    public final BooleanPath isvalid = createBoolean("isvalid");

    public final NumberPath<Integer> mediaId = createNumber("mediaId", Integer.class);

    public final StringPath mediaName = createString("mediaName");

    public final NumberPath<Integer> quoteCount = createNumber("quoteCount", Integer.class);

    public final DateTimePath<java.util.Date> releaseDate = createDateTime("releaseDate", java.util.Date.class);

    public final NumberPath<Long> scenicId = createNumber("scenicId", Long.class);

    public final NumberPath<Integer> sourceType = createNumber("sourceType", Integer.class);

    public final NumberPath<Integer> tagBadCnt = createNumber("tagBadCnt", Integer.class);

    public final NumberPath<Integer> tagCnt = createNumber("tagCnt", Integer.class);

    public final NumberPath<Integer> tagGoodCnt = createNumber("tagGoodCnt", Integer.class);

    public final StringPath tagIds = createString("tagIds");

    public final StringPath title = createString("title");

    public final NumberPath<Integer> transmissibilityE = createNumber("transmissibilityE", Integer.class);

    public final NumberPath<Integer> transmissibilityM = createNumber("transmissibilityM", Integer.class);

    public final StringPath url = createString("url");

    public final StringPath urlCrc = createString("urlCrc");

    public QDataStream(String variable) {
        super(DataStream.class, forVariable(variable));
    }

    public QDataStream(Path<? extends DataStream> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDataStream(PathMetadata metadata) {
        super(DataStream.class, metadata);
    }

}

