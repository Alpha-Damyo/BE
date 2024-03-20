insert into users (id, name, email, created_at, profile_url, contribution, gender, age) values
(UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), '유저1', 'jjhinu104@gmail.com', '2023-05-16 15:48:57.450179', 'sfsdfsdfw', 100, 'male', 25);
insert into users (id, name, email, created_at, profile_url, contribution, gender, age) values
(UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), '유저2', 'jjhinu105@gmail.com', '2023-05-16 16:48:57.450179', 'profile2', 100, 'female', 25);

insert into smoking_area (id, name, created_at, status, address) values ('area1', '국민대', '2023-05-16 15:48:57.450179', true, '길음');
insert into smoking_area (id, name, created_at, status, address) values ('area2', '고려대', '2023-05-16 15:48:57.450179', true, '길음');


insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (1, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area1', 'url1', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (2, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area2', 'url2', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (3, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area1', 'url3', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (4, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area2', 'url4', '2023-05-16 16:48:57.450179', 4);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (5, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area1', 'url5', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (6, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area2', 'url6', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (7, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area1', 'url7', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (8, UNHEX(REPLACE("3f06af63-a93c-11e4-9797-00505690773f", "-","")), 'area2', 'url8', '2023-05-16 16:48:57.450179', 0);

insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (9, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area1', 'url9', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (10, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area2', 'url10', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (11, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area1', 'url11', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (12, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area2', 'url12', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (13, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area1', 'url13', '2023-05-16 16:48:57.450179', 10);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (14, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area2', 'url14', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (15, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area1', 'url15', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (16, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area2', 'url16', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (17, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area1', 'url17', '2023-05-16 16:48:57.450179', 0);
insert into picture (id, user_id, smoking_area_id, picture_url, created_at, likes) values (18, UNHEX(REPLACE("8fbcec60-e527-11ee-bd3d-0242ac120002", "-","")), 'area2', 'url18', '2023-05-16 16:48:57.450179', 0);

