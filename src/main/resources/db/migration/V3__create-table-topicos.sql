CREATE TABLE topicos(

   ID bigint not null auto_increment,
   Titulo varchar(200) not null,
   Mensaje varchar(1500) not null,
   Fecha_Creacion datetime not null,
   Status varchar(50) not null,
   Autor_ID bigint not null,
   Curso_ID bigint not null,

   PRIMARY KEY(ID),

   constraint fk_topicos_Autor_ID foreign key(Autor_ID) references usuarios(ID),
   constraint fk_topicos_Curso_ID foreign key(Curso_ID) references cursos(ID)
);