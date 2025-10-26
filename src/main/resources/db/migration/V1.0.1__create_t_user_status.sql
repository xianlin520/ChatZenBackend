create table t_user_status
(
    id      int auto_increment comment '主键_自增'
        primary key,
    user_id bigint                          not null comment '用户id-外键关联-唯一',
    points  int          default 0          not null comment '用户积分',
    level   int          default 1          not null comment '用户权限等级',
    name    varchar(255) default '普通用户' not null comment '用户权限等级名',
    constraint t_user_status_t_user_account_user_id_fk
        foreign key (user_id) references t_user_account (user_id)
)
    comment '储存用户积分, 用户等级, 用户权限等';


