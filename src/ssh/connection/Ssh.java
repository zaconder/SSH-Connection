package ssh.connection;

import java.io.OutputStream;

public class Ssh
{

	// create new Ssh instance
	
	

	public Ssh(String hostname, String username, String password)
	{
		Ssh ssh = new Ssh(hostname, username, password);
		ssh.connect();
	}


	public void connect()
	{
		
	}


	public void addSshListener(SshController sshController)
	{
		
		
	}


	public OutputStream getOutputStream()
	{
		
		return null;
	}
	 
	
}
