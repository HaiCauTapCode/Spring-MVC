create database employee_manager;
use employee_manager;

create table employee
(
    id         int primary key auto_increment,
    full_name  varchar(50),
    birth_date date,
    gender     boolean,
    salary double,
    phone_number varchar(15) not null unique,
    department_id int,
    foreign key (department_id) references department(id)
);

create table department
(
    id   int primary key auto_increment,
    name varchar(50) not null
);

create table user
(
    id   int primary key auto_increment,
    username varchar(100) not null unique,
    password varchar(100) not null
);

insert into user(username, password) values ('user', 'user');

insert into department(id, name) values (1, 'Quản Lý');
insert into department(id, name) values (2, 'Kế Toán');
insert into department(id, name) values (3, 'Sale-Marketing');
insert into department(id, name) values (4, 'Sản Xuất');

insert into employee (full_name, birth_date, gender, salary, phone_number, department_id)
VALUES ('Hoàng Văn Hải','1990-01-15', true, 15000000, '0975123542', 1);
insert into employee (full_name, birth_date, gender, salary, phone_number, department_id)
VALUES ('Trần Thị Hoài','1985-05-20', false, 14500000, '0967869868', 2);
insert into employee (full_name, birth_date, gender, salary, phone_number, department_id)
VALUES ('Lê Văn Sỹ','1992-03-10', true, 15000000, '0988881110', 3);
insert into employee (full_name, birth_date, gender, salary, phone_number, department_id)
VALUES ('Phạm Duy Khánh','1988-07-05', true, 14800000, '0986555333', 4);

select id, username, password from user where username = ?;