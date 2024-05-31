alter table process_definition
    add column description varchar;
update process_definition
set description='Configuration distribution and validation flow involves distributing configuration settings to systems, verifying the integrity and correctness of these configurations, and ensuring compliance with predefined standards. This process enhances system reliability and performance.';

alter table process
drop
column created_at,
drop
column created_by,
drop
column modified_at,
drop
column modified_by;

alter table process
    add column created_at timestamp(6);
alter table process
    add column created_by varchar(36);
alter table process
    add column updated_at timestamp(6);
alter table process
    add column updated_by varchar(36);

alter table task
drop
column created_at,
drop
column created_by,
drop
column modified_at,
drop
column modified_by;

alter table task
    add column created_at timestamp(6);
alter table task
    add column created_by varchar(36);
alter table task
    add column updated_at timestamp(6);
alter table task
    add column updated_by varchar(36);

alter table document
drop
column created_at,
drop
column created_by,
drop
column modified_at,
drop
column modified_by;

alter table document
    add column created_at timestamp(6);
alter table document
    add column created_by varchar(36);
alter table document
    add column updated_at timestamp(6);
alter table document
    add column updated_by varchar(36);

create table z_aud_process
(
    rev                      integer not null,
    revtype                  integer not null,
    id                       UUID    not null,
    process_instance_id      UUID    not null,
    process_definition_id_fk UUID    not null,
    status                   varchar not null,
    created_at               timestamp(6),
    created_by               varchar(36),
    updated_at               timestamp(6),
    updated_by               varchar(36),
    constraint z_aud_process_pk primary key (rev, id)
);

create table z_aud_task
(
    rev                   integer     not null,
    revtype               integer     not null,
    id                    UUID        not null,
    task_definition_id_fk uuid        not null,
    status                varchar(50) not null,
    flowable_task_id      uuid        not null,
    process_id_fk         uuid        not null,
    form_data             varchar,
    custom_task_name      varchar(255),
    created_at            timestamp(6),
    created_by            varchar(36),
    updated_at            timestamp(6),
    updated_by            varchar(36),
    constraint z_aud_task_pk primary key (rev, id)
);

create table z_aud_document
(
    rev           integer not null,
    revtype       integer not null,
    id            UUID    not null,
    process_id_fk uuid    not null,
    name          varchar not null,
    content       bytea   not null,
    status        varchar not null,
    created_at    timestamp(6),
    created_by    varchar(36),
    updated_at    timestamp(6),
    updated_by    varchar(36),
    constraint z_aud_document_pk primary key (rev, id)
);

create table revinfo
(
    rev      integer not null,
    revtstmp bigint  not null,
    primary key (rev)
);

create sequence revinfo_seq increment by 50;