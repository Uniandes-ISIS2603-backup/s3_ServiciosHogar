delete from ReferenciaEntity;
delete from HojaDeVidaEntity;
delete from HabilidadEntity;
delete from PrestadorEntity;
delete from CalificacionEntity;
delete from ServicioEntity;
delete from FacturaEntity;
delete from SolicitudEntity;
delete from TarjetaCreditoEntity;
delete from ClienteEntity;

insert into ClienteEntity ( nombre, direccion, correo, contrasena) values ('Carlos Eduardo Robles', '82899 Beilfuss Avenue', 'acornilleau0@economist.com', 'iHLabox65');
insert into ClienteEntity ( nombre, direccion, correo, contrasena) values ('Daniela Rocha', '76 Melrose Lane', 'acornilleau0@economist.com', 'bcVEODPs');
insert into ClienteEntity ( nombre, direccion, correo, contrasena) values ('Steven Tarazona', '8 Marquette Plaza', 'acornilleau0@economist.com', 'YoP5sKpjKPc2');

insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (123, '10/24', '5322054936662839', 'Carlos Eduardo Robles', 1);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (721, '11/29', '4026569908034768', 'Carlos Eduardo Robles', 1);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (852, '12/25', '3570329995405336', 'Carlos Eduardo Robles', 1);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (123, '10/24', '6399719581997591', 'Daniela Rocha', 2);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (721, '11/29', '3528861051826230', 'Daniela Rocha', 2);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (852, '12/25', '5100131460378053', 'Daniela Rocha', 2);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (123, '10/24', '3533293788971159', 'Steven Tarazona', 3);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (721, '11/29', '3528060797466654', 'Steven Tarazona', 3);
insert into TarjetaCreditoEntity (codeseguridad, fechavencimiento, numero, titular, cliente_id) values (852, '12/25', '5466702614488358', 'Steven Tarazona', 3);

insert into SolicitudEntity (direccion, fecha, cliente_id) values ('82899 Beilfuss Avenue', '10/10/2018', 1);
insert into SolicitudEntity (direccion, fecha, cliente_id) values ('82899 Beilfuss Avenue', '10/10/2018', 1);
insert into SolicitudEntity (direccion, fecha, cliente_id) values ('82899 Beilfuss Avenue', '10/10/2018', 1);
insert into SolicitudEntity (direccion, fecha, cliente_id) values ('76 Melrose Lane', '10/10/2018', 2);
insert into SolicitudEntity (direccion, fecha, cliente_id) values ('76 Melrose Lane', '10/10/2018', 2);
insert into SolicitudEntity (direccion, fecha, cliente_id) values ('8 Marquette Plazat', '10/10/2018', 3);
insert into SolicitudEntity (direccion, fecha, cliente_id) values ('8 Marquette Plaza', '10/10/2018', 3);

--Todavia exige id.
insert into PrestadorEntity (id, cedula, contrasena, correo, nombre) values (1, 2018123456, 'fHuLGEMR', 'rsinyard0@ovh.net', 'Maria Jose Ocampo'); 
insert into PrestadorEntity (id, cedula, contrasena, correo, nombre) values (2, 2018654321, 'XXlRz17MiEtF', 'vscrancher1@statcounter.com', 'Adriana Trujillo'); 
insert into PrestadorEntity (id, cedula, contrasena, correo, nombre) values (3, 2017123456, 'PTPQilwIJSn', 'scraze2@dagondesign.com', 'Sebastian Gomez'); 

insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 1, 1);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 1, 2);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 1, 3);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 2, 4);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 2, 5);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 2, 6);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 3, 7); 
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 3, 1);
insert into ServicioEntity (descripcion, requerimientos, prestador_id, solicitud_id) values ('Un servicio', 'Un requerimiento', 3, 2); 

insert into HabilidadEntity (id, descripcion, tipo, prestador_id) values (1, 'descripcion1', 'tipo1', 1); 
insert into HabilidadEntity (id, descripcion, tipo, prestador_id) values (2, 'descripcion2', 'tipo2', 1); 
insert into HabilidadEntity (id, descripcion, tipo, prestador_id) values (3, 'descripcion3', 'tipo1', 2); 
insert into HabilidadEntity (id, descripcion, tipo, prestador_id) values (4, 'descripcion4', 'tipo2', 2); 
insert into HabilidadEntity (id, descripcion, tipo, prestador_id) values (5, 'descripcion5', 'tipo1', 3); 
insert into HabilidadEntity (id, descripcion, tipo, prestador_id) values (6, 'descripcion6', 'tipo3', 3); 

--La fecha esta mes, dia, año
insert into HojaDeVidaEntity (direccion, email, fechaNacimiento, formacion, telefono, trayectoria, prestador_id) values ('4 Pennsylvania Trail', 'cablott0@blogspot.com', '05/04/1983', 'cursus vestibulum proin eu mi nulla ac enim', 6212653342, 'et ultrices posuere cubilia curae duis faucibus accumsan odio curabitur convallis duis', 1);
insert into HojaDeVidaEntity (direccion, email, fechaNacimiento, formacion, telefono, trayectoria, prestador_id) values ('53 Ridgeway Junction', 'tdrewell1@fda.gov', '11/25/1975', 'quis tortor id nulla ultrices aliquet maecenas leo', 8744432169, 'amet sapien dignissim vestibulum vestibulum ante ipsum primis in faucibus orci luctus et ultrices', 2);
insert into HojaDeVidaEntity (direccion, email, fechaNacimiento, formacion, telefono, trayectoria, prestador_id) values ('60649 Gina Junction', 'bsingers2@ustream.tv', '01/26/1975', 'nunc donec quis orci eget orci', 7574043274, 'leo odio condimentum id luctus nec molestie sed justo pellentesque viverra pede ac diam cras pellentesque volutpat', 3);

insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('congue', 'bplatt0@geocities.com', 'Johns Group', 1, 'Bonnee Platt', 'ninguno', 6803106529, 1);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('nulla', 'bfereday1@prlog.org', 'Lesch-Schmidt', 2, 'Barbey Fereday', 'ninguno', 6831029070, 2);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('tortor duis', 'jgoulthorp2@sbwire.com', 'Ebert-Connelly', 3, 'Jeramey Goulthorp', 'ninguno', 5201909677, 3);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('dolor', 'tthripp3@msn.com', 'Nitzsche and Sons', 4, 'Tommie Thripp', 'ninguno', 5859639661, 1);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('libero nam', 'lthurlborn4@usnews.com', 'Ledner-Jaskolski', 5, 'Lenci Thurlborn', 'ninguno', 8117914243, 2);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('faucibus', 'hpallant5@fotki.com', 'Gaylord-Ziemann', 6, 'Hershel Pallant', 'ninguno', 6407658744, 3);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('pellentesque', 'ndanilchenko6@last.fm', 'Walker Inc', 7, 'Natividad Danilchenko', 'ninguno', 9692496226, 1);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('magna ac', 'voluby7@qq.com', 'Hudson-Swift', 8, 'Verena O''Luby', 'ninguno', 8738634841, 2);
insert into ReferenciaEntity (cargo, email, empresa, idRemitente, nombreRemitente, parentesco, telRemitente, hojaDeVida_id) values ('vel', 'rborer8@goodreads.com', 'Mosciski LLC', 9, 'Rivi Borer', 'ninguno', 6312533518, 3);

insert into CalificacionEntity (calificacion, comentario, servicio_id) values (4.5, 'Muy buen trabajo', 1);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (3.0, 'Un trabajo regular', 2);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (4.0, 'Buen trabajo', 3);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (4.1, 'Buen trabajo', 4);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (2.0, 'Mal trabajo', 5);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (3.5, 'Buen trabajo', 6);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (5.0, 'Muy buen trabajo', 7);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (2.5, 'Mal trabajo', 8);
insert into CalificacionEntity (calificacion, comentario, servicio_id) values (1.0, 'Pesimo trabajo', 9);