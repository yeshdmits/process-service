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
          "type": "object",
          "properties": {
            "full_name": {
              "type": "string",
              "title": "What is your full name?"
            },
            "date_of_birth": {
              "type": "string",
              "format": "date",
              "title": "What is your date of birth?"
            },
            "income_source": {
              "type": "string",
              "title": "What is your primary source of income?",
              "enum": [
                "Employment",
                "Business ownership",
                "Investments",
                "Other"
              ]
            },
            "account_purpose": {
              "type": "string",
              "title": "What is the purpose of opening this account?",
              "enum": [
                "Personal savings",
                "Checking for daily transactions",
                "Investment opportunities",
                "Other"
              ]
            },
            "transaction_frequency": {
              "type": "string",
              "title": "How often do you expect to make transactions on this account?",
              "enum": [
                "Daily",
                "Weekly",
                "Monthly",
                "Rarely"
              ]
            },
            "average_balance": {
              "type": "string",
              "title": "What is your estimated average account balance?",
              "enum": [
                "<$1,000",
                "$1,000 - $5,000",
                "$5,000 - $10,000",
                ">$10,000"
              ]
            },
            "additional_services": {
              "type": "string",
              "title": "Are you interested in additional banking services?",
              "enum": [
                "Credit cards",
                "Loans",
                "Investment advisory",
                "Other"
              ]
            },
            "account_management_preference": {
              "type": "string",
              "title": "How do you prefer to manage your account?",
              "enum": [
                "Online banking",
                "In-person visits",
                "Mobile app",
                "Other"
              ]
            },
            "specific_requirements": {
              "type": "string",
              "title": "Do you have any specific requirements or preferences for this account?",
              "enum": [
                "Interest-bearing",
                "No monthly fees",
                "Access to ATMs",
                "Other"
              ]
            },
            "banking_familiarity": {
              "type": "string",
              "title": "What is your familiarity with banking products and services?",
              "enum": [
                "Basic",
                "Intermediate",
                "Advanced",
                "Limited"
              ]
            }
          },
          "required": [
            "full_name",
            "date_of_birth",
            "income_source",
            "account_purpose",
            "transaction_frequency",
            "average_balance",
            "account_management_preference",
            "banking_familiarity"
          ]
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