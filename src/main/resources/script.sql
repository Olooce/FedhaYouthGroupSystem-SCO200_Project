create table members
(
    member_id     bigint auto_increment
        primary key,
    honorific     varchar(10)                         null,
    surname       varchar(50)                         not null,
    first_name    varchar(50)                         not null,
    other_name    varchar(50)                         null,
    date_of_birth date                                null,
    id_number     varchar(20)                         null,
    id_type       varchar(20)                         null,
    tax_id        varchar(20)                         null,
    email         varchar(100)                        null,
    phone_number  varchar(20)                         null,
    address       varchar(200)                        null,
    status        varchar(20)                         null,
    date_created  timestamp default CURRENT_TIMESTAMP null,
    date_modified timestamp                           null,
    constraint id_number
        unique (id_number),
    constraint tax_id
        unique (tax_id)
);

create table deposits
(
    deposit_id   bigint auto_increment
        primary key,
    member_id    bigint                             null,
    amount       decimal(10, 2)                     not null,
    date_created datetime default CURRENT_TIMESTAMP not null,
    constraint deposits_ibfk_1
        foreign key (member_id) references members (member_id)
);

create index member_id
    on deposits (member_id);

create table loans
(
    loan_id           bigint auto_increment
        primary key,
    member_id         bigint         null,
    type              varchar(50)    null,
    amount            decimal(10, 2) not null,
    repayment_period  int            null,
    interest_rate     decimal(5, 2)  null,
    guaranteed_amount decimal(10, 2) null,
    status            varchar(20)    null,
    constraint loans_ibfk_1
        foreign key (member_id) references members (member_id)
);

create table guarantors
(
    guarantor_id     bigint unsigned auto_increment
        primary key,
    loan_id          bigint                              not null,
    member_id        bigint                              not null,
    guarantee_amount double                              not null,
    guaranteed_at    timestamp default CURRENT_TIMESTAMP null,
    constraint guarantor_id
        unique (guarantor_id),
    constraint guarantors_ibfk_1
        foreign key (loan_id) references loans (loan_id),
    constraint guarantors_ibfk_2
        foreign key (member_id) references members (member_id)
);

create index loan_id
    on guarantors (loan_id);

create index member_id
    on guarantors (member_id);

create index member_id
    on loans (member_id);

create table shares
(
    member_id    bigint auto_increment
        primary key,
    total_shares decimal(10, 2) not null,
    constraint shares_ibfk_1
        foreign key (member_id) references members (member_id)
);

create table staff_members
(
    staff_id      bigint auto_increment
        primary key,
    name          varchar(100)                        null,
    position      varchar(50)                         null,
    email         varchar(100)                        null,
    phone_number  varchar(20)                         null,
    date_created  timestamp default CURRENT_TIMESTAMP null,
    date_modified timestamp default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
);

create table users
(
    user_id       bigint auto_increment
        primary key,
    username      varchar(50)                         not null,
    password_hash varchar(255)                        not null,
    role          varchar(50)                         null,
    member_id     bigint                              null,
    date_created  timestamp default CURRENT_TIMESTAMP null,
    date_modified timestamp                           null,
    constraint username
        unique (username)
);

create definer = root@localhost trigger validate_user_role
    before insert
    on users
    for each row
BEGIN
    IF NEW.role = 'STAFF' THEN
        IF NOT EXISTS (SELECT 1 FROM staff_members WHERE staff_id = NEW.member_id) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid member_id for STAFF role';
        END IF;
    ELSEIF NEW.role = 'MEMBER' THEN
        IF NOT EXISTS (SELECT 1 FROM members WHERE member_id = NEW.member_id) THEN
            SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Invalid member_id for MEMBER role';
        END IF;
    END IF;
END;


