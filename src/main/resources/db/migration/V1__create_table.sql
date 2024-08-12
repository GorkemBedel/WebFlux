CREATE TABLE course(
course_id uuid NOT NULL,
name varchar(255) NOT NULL,
description varchar(255) NULL,
duration int4 NOT NULL,
teacher varchar(255) NOT NULL,
course_metadata jsonb NOT NULL,
PRIMARY KEY (course_id)
);

CREATE TABLE student(
id uuid NOT NULL,
name varchar(255) NOT NULL,
date_of_birth date NOT NULL,
email varchar(255) NOT NULL,
course_id varchar[] NOT NULL,
PRIMARY KEY (id)
);
