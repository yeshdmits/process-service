create table process_definition
(
    id           UUID primary key,
    display_name varchar(255),
    process_name varchar(255)
);

insert into process_definition(id, display_name, process_name)
values (gen_random_uuid(), 'Default Process', 'defaultProcess');

create table process
(
    id                       uuid primary key,
    process_instance_id      uuid unique not null,
    process_definition_id_fk uuid        not null,
    status                   varchar     not null,
    constraint fk_process_definition_id foreign key (process_definition_id_fk) references process_definition (id)
);

create table task_definition
(
    id               uuid primary key,
    name             varchar(255) not null,
    definition_key   varchar(255) not null,
    schema           json,
    custom_task_name varchar(255)
);

insert into task_definition(id, name, definition_key, schema, custom_task_name)
values (gen_random_uuid(),
        'Configuration Task',
        'defaultConfigurationTask',
        '{
          "title": "A registration form",
          "description": "A simple form example.",
          "type": "object",
          "required": [
            "firstName",
            "lastName"
          ],
          "properties": {
            "firstName": {
              "type": "string",
              "title": "First name",
              "default": "Chuck"
            },
            "lastName": {
              "type": "string",
              "title": "Last name"
            },
            "age": {
              "type": "integer",
              "title": "Age"
            },
            "bio": {
              "type": "string",
              "title": "Bio"
            },
            "password": {
              "type": "string",
              "title": "Password",
              "minLength": 3
            },
            "telephone": {
              "type": "string",
              "title": "Telephone",
              "minLength": 10
            }
          }
        }',
        null);
insert into task_definition(id, name, definition_key, schema, custom_task_name)
values (gen_random_uuid(),
        'Distribution Task',
        'defaultDistributionTask',
        null,
        'DistributionComponent');

insert into task_definition(id, name, definition_key, schema, custom_task_name)
values (gen_random_uuid(),
        'Validation Task',
        'defaultValidationTask',
        '{
          "title": "Validate Document",
          "description": "Document validation example",
          "type": "object",
          "properties": {
            "decision": {
              "type": "string",
              "title": "Provide decision regarding document",
              "format": "radio",
              "enum": [
                "Approved",
                "Rejected"
              ]
            },
            "Comment": {
              "type": "string"
            }
          },
          "if": {
            "properties": {
              "decision": {
                "enum": [
                  "Rejected"
                ]
              }
            }
          },
          "then": {
            "required": [
              "Comment"
            ]
          },
          "required": [
            "decision"
          ]
        }',
        null);

create table task
(
    id                    uuid primary key,
    task_definition_id_fk uuid        not null,
    status                varchar(50) not null,
    flowable_task_id      uuid        not null,
    process_id_fk         uuid        not null,
    form_data             varchar,
    custom_task_name      varchar(255),
    constraint fk_task_definition_id foreign key (task_definition_id_fk) references task_definition (id),
    constraint fk_process_id foreign key (process_id_fk) references process (id)
);


create table document
(
    id            uuid primary key,
    process_id_fk uuid    not null,
    name          varchar not null,
    content       bytea   not null,
    status        varchar not null,
    constraint fk_process_id foreign key (process_id_fk) references process (id)
);