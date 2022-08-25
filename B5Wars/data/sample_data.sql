-- clear tables
Truncate Table period_of_service;
Truncate Table unit;
Truncate Table user;


-- create user
Insert Into user (username, password, email_address, active, editor)
Values ('Swordsman', '123', 'alex@packwood.co.nz', true, true);


-- create sample units
Insert Into unit (type, model, version, definition, object, modified_by)
values ('EA Hyperion Heavy Cruiser', 'Theta Model', 3,
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Hyperion Heavy Cruiser (Theta Model).xml'),
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Hyperion Heavy Cruiser (Theta Model).ufo'), 'Swordsman');
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('EA Hyperion Heavy Cruiser', 'Theta Model', 3, 'EA', 2246);

Insert Into unit (type, model, version, definition, object, modified_by)
values ('EA Hyperion Heavy Cruiser', 'Theta Model', 2,
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Hyperion Heavy Cruiser (Theta Model) v2.xml'),
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Hyperion Heavy Cruiser (Theta Model).ufo'), 'Swordsman');
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('EA Hyperion Heavy Cruiser', 'Theta Model', 2, 'EA', 2246);

Insert Into unit (type, model, version, definition, object, modified_by)
values ('EA Hyperion Patrol Cruiser', 'Eta Model', 2,
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Hyperion Patrol Cruiser (Eta Model).xml'),
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Hyperion Patrol Cruiser (Eta Model).ufo'), 'Swordsman');
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('EA Hyperion Patrol Cruiser', 'Eta Model', 2, 'EA', 2246);

Insert Into unit (type, model, version, definition, object, modified_by)
values ('EA Nova Dreadnought', 'Beta Model', 3,
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Nova Dreadnought (Beta Model).xml'),
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/EA Nova Dreadnought (Beta Model).ufo'), 'Swordsman');
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('EA Nova Dreadnought', 'Beta Model', 3, 'EA', 2242);

Insert Into unit (type, model, version, definition, object, modified_by)
values ('Minbari Sharlin War Cruiser', '', 3,
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/Minbari Sharlin War Cruiser.xml'),
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/Minbari Sharlin War Cruiser.ufo'), 'Swordsman');
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('Minbari Sharlin War Cruiser', '', 3, 'Minbari', 2058);

Insert Into unit (type, model, version, definition, object, modified_by)
values ('Minbari White Star', 'Standard Model', 3,
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/Minbari White Star (Standard Model).xml'),
  load_file('/Personal/Gaming/Projects/B5Wars/data/sample/Minbari White Star (Standard Model).ufo'), 'Swordsman');
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('Minbari White Star', 'Standard Model', 3, 'Minbari', 2260);
Insert Into period_of_service (type, model, version, faction, in_service_from)
values ('Minbari White Star', 'Standard Model', 3, 'IA', 2260);
