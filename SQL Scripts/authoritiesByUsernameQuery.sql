CREATE DEFINER=`root`@`localhost` PROCEDURE `authoritiesByUsernameQuery`(IN username VARCHAR(50))
BEGIN
	SELECT email, role 
	FROM lagdaemon.user 
    join users_roles on users_roles.user_id = user.user_id 
    join role on users_roles.role_id = role.role_id 
    where user.email = username or login = username;
END