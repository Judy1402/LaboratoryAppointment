create table if not exists `user`(
    id char(19) primary key ,
    name varchar(20) not null ,
    profession varchar(64) default null,
    phone char(11) not null,
    description varchar(200) default null,
    account varchar(24) not null ,
    password varchar(60) not null,
    role char(4) not null ,
    status tinyint unsigned default 1 not null ,
    create_time datetime default current_timestamp,
    update_time datetime default current_timestamp on update current_timestamp,
    login_time datetime default null,

    unique (account),
    index(name, status, create_time)
);
create table if not exists `course`(
    id char(19) primary key ,
    name varchar(40) not null ,
    quantity tinyint unsigned not null comment '人数',
    teacher_id char(19) not null,
    profession varchar(64) not null,
    year char(4) not null,
    term tinyint default 0 not null,
    class_number char(2) default null comment '班级，eg: 04',
    lesson tinyint unsigned not null comment '节数',
    credit float not null ,
    theoretical_hours tinyint not null ,
    experimental_hours tinyint not null,

    index(teacher_id),
    index(year, term, class_number)
);


create table if not exists `classroom`(
    id char(19) primary key ,
    name varchar(40) default null,
    role char(4) not null comment '普通、实验...',
    manager_id char(19) default null,
    manager_name varchar(20) default null,
    quantity tinyint unsigned not null ,
    description varchar(200) default null,
    status tinyint unsigned not null default 1,

    index(status, role, quantity)
);
create table if not exists `appointment`(
    id char(19) primary key ,
    course json not null  comment '{"id", "name"}',
    teacher json not null comment '{"id", "name"}',
    classroom json not null comment  '{"id", "name"}',
    week tinyint unsigned not null ,
    dayofweek tinyint unsigned not null ,
    section tinyint unsigned not null ,

    unique ((cast(classroom ->> '$.id' as char(19)) collate utf8mb4_bin), week, dayOfWeek, section),
    index((cast(course ->> '$.id' as char(19)) collate  utf8mb4_bin)),
    index ((cast(teacher ->> '$.id' as char (19)) collate  utf8mb4_bin))
);

/*
# insert into `appointment`(id, course, teacher, classroom, week, dayOfWeek, section)
# values ('1111111111111111111', '{"id": "1", "name": "test"}', '{"id": "1", "name": "test"}', '{"id": "1", "name": "test"}', 1, 1, 1);

 */
# select * from course c, user u where c.teacher_id = u.id
