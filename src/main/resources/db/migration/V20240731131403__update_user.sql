update user_entity set role_id = (select id from role where libelle ='ADMINISTRATEUR')
where username ='admin';