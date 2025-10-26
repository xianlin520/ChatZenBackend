create table t_user_account
(
    id            int unsigned auto_increment comment '数据ID'
        primary key,
    user_id       bigint                                 not null comment '用户ID, 使用雪花算法生成-唯一',
    name          varchar(255) default '新用户'          not null comment '用户昵称',
    account       varchar(255)                           not null comment '用户账号-唯一',
    password_hash varchar(255)                           not null comment '用户密码-加密',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '账号创建时间',
    constraint t_user_account_pk
        unique (user_id)
);


