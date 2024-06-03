alter table task_definition add column assign_role varchar(128);


update task_definition
set assign_role = 'manager'
where definition_key='defaultValidationTask';

update task_definition
set assign_role = 'client'
where definition_key='defaultConfigurationTask';

update task_definition
set assign_role = 'client'
where definition_key='defaultDistributionTask';