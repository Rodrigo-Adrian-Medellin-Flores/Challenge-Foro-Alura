CREATE TABLE usuarios(

   ID bigint not null auto_increment,
   Nombre varchar(200) not null,
   Email varchar(100) not null unique,
   Contrasena varchar(300) not null,

   PRIMARY KEY(ID)
);