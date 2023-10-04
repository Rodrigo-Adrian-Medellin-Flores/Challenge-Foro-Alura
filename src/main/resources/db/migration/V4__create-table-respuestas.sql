CREATE TABLE respuestas(

   ID bigint not null auto_increment,
   Mensaje varchar(1500) not null,
   Topico_ID bigint not null,
   Fecha_Creacion datetime not null,
   Autor_ID bigint not null,
   Solucion bit(1)not null,

   PRIMARY KEY(ID),

   constraint fk_respuestas_Topico_ID foreign key(Topico_ID) references topicos(ID),
   constraint fk_respuestas_Autor_ID foreign key(Autor_ID) references usuarios(ID)
);