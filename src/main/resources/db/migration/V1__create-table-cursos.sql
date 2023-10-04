CREATE TABLE cursos(

   ID bigint not null auto_increment,
   Nombre varchar(200) not null unique,

   PRIMARY KEY(ID)
);