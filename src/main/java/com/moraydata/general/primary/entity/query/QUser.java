package com.moraydata.general.primary.entity.query;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

import javax.annotation.Generated;

import com.moraydata.general.primary.entity.Role;
import com.moraydata.general.primary.entity.User;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.SetPath;
import com.querydsl.core.types.dsl.StringPath;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1836618619L;

    public static final QUser user = new QUser("user");

    public final QBaseAbstractEntity _super = new QBaseAbstractEntity(this);

    public final StringPath avatarUrl = createString("avatarUrl");

    public final StringPath company = createString("company");

    public final StringPath companyFax = createString("companyFax");

    public final StringPath companyLocation = createString("companyLocation");

    public final StringPath companyPhone = createString("companyPhone");

    public final StringPath companyTitle = createString("companyTitle");

    public final StringPath companyType = createString("companyType");

    public final NumberPath<Integer> countOfInvitationCodes = createNumber("countOfInvitationCodes", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> creator = createNumber("creator", Long.class);

    public final StringPath description = createString("description");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath nickname = createString("nickname");

    public final BooleanPath notified = createBoolean("notified");

    public final StringPath notifiedHotPublicSentiment = createString("notifiedHotPublicSentiment");

    public final StringPath notifiedNegativePublicSentiment = createString("notifiedNegativePublicSentiment");

    public final StringPath notifiedWarningPublicSentiment = createString("notifiedWarningPublicSentiment");

    public final StringPath openId = createString("openId");

    public final StringPath orderItemIds = createString("orderItemIds");

    public final NumberPath<Long> parentId = createNumber("parentId", Long.class);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final StringPath photo = createString("photo");

    public final SetPath<Role, QRole> roles = this.<Role, QRole>createSet("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedDate = _super.updatedDate;

    public final StringPath username = createString("username");

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

