CREATE DEFINER=`root`@`%` PROCEDURE `usersByUsernameQuery`(IN loginid VARCHAR(50))
BEGIN
	select email as username, password_hash as password, email_validated as active 
    from user 
    where email=loginid or login=loginid;
END