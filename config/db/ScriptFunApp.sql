create table USUARIOS (
    usu_noControl varchar(8) not null,
    usu_nombre varchar(20),
    usu_password varchar(20) not null,
    usu_fotoPerfil image
)
alter table USUARIOS add constraint pk_usuarios primary key (usu_noControl)

create table PUBLICACIONES (
    usu_noControl varchar(8) not null,
    pub_fecha datetime not null,
    pub_comentario varchar(150) not null,
    pub_contadorApoyos int not null,
    pub_contadorComentarios int not null
)

alter table PUBLICACIONES add constraint pk_publicaciones primary key (usu_noControl, pub_fecha)
alter table PUBLICACIONES add constraint fk_publicaciones foreign key (usu_noControl) references USUARIOS (usu_noControl)

create table COMENTARIOS (
    pub_noControlAutorPrincipal varchar(8) not null,
    pub_fechaPublicacionPrincipal datetime not null,
    com_noControlAutorComentario varchar(8) not null,
    com_fechaPublicacionComentario datetime not null,
    com_comentarioRetro varchar(100) not null
)

alter table COMENTARIOS add constraint pk_comentarios primary key (pub_noControlAutorPrincipal, pub_fechaPublicacionPrincipal, com_noControlAutorComentario, com_fechaPublicacionComentario)
alter table COMENTARIOS add constraint fk_comentarios foreign key (pub_noControlAutorPrincipal, pub_fechaPublicacionPrincipal) references PUBLICACIONES (usu_noControl, pub_fecha)

create procedure PA_InsertarPublicacion
    @noControl varchar(8),
    @comentario varchar(150)
as
    begin try
        Begin tran
        declare @fecha datetime
        set @fecha = GETDATE()
        insert into PUBLICACIONES values (@noControl, @fecha, @comentario, 0, 0)
        commit tran
    end try
    begin catch
        rollback tran
    end catch

create procedure PA_ValidarUsuario
    @noControl varchar(8),
    @contrasenia varchar(20)
as
    begin try
        begin tran
        declare @resultado varchar(8)
        set @resultado = (select usu_noControl from USUARIOS where usu_noControl = @noControl and usu_password = @contrasenia)
        select @resultado
        commit tran
    end try
    begin catch
        rollback tran
        select null
    end catch

create procedure PA_InsertarComentario
    @noControlAutorPublicacion varchar(8),
    @fechaPublicacion datetime,
    @noControlAutorComentario varchar(8),
    @comentario varchar(150)
as
    begin try
        begin tran
        update PUBLICACIONES set pub_contadorComentarios += 1 where usu_noControl = @noControlAutorPublicacion and pub_fecha = @fechaPublicacion
        declare @fecha datetime
        set @fecha = GETDATE()
        insert into COMENTARIOS values (@noControlAutorPublicacion, @fechaPublicacion, @noControlAutorComentario, @fecha, @comentario)
        commit tran
    end try
    begin catch
        rollback tran
    end catch
