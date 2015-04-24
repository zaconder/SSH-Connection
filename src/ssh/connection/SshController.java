package ssh.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshController
{

	private JTextArea msgs;

	private String[] stuff;

	public String[] getWordage()
	{
		return stuff;
	}

	public void setWordage(String[] wordage)
	{
		this.stuff = wordage;
	}

	public SshController()
	{

		msgs = new JTextArea(15, 30);
		// THIS VERSION WORKS! //

		JSch jsch = new JSch();
		String remoteHostUserName = null;
		String RemoteHostName = "localhost";
		String remoteHostpassword = "8215199";
		Session session = null;
		try
		{
			session = jsch.getSession(remoteHostUserName, RemoteHostName, 22);
		} catch (JSchException e)
		{
			e.printStackTrace();
		}

		session.setPassword(remoteHostpassword);
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		try
		{
			session.connect();
		} catch (JSchException e)
		{
			e.printStackTrace();
		}

		ChannelExec channel = null;
		try
		{
			channel = (ChannelExec) session.openChannel("exec");
		} catch (JSchException e)
		{
			e.printStackTrace();
		}
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(channel.getInputStream()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		channel.setCommand("w;");
		try
		{
			channel.connect();
		} catch (JSchException e)
		{
			e.printStackTrace();
		}

		String msg = null;
		String tempString = "";
		JFrame temp = new JFrame();
		temp.setSize(500, 500);
		try
		{
//			tempString.trim().split("\\s");
			
			while ((msg = in.readLine()) != null)
			{
				tempString += msg;
				
				if(tempString.contains("\\s+"))
				{
					tempString += "\n";
				}
				
				System.out.println(msg);
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		}

		JTextArea msgs = new JTextArea(tempString);
		msgs.setLineWrap(true);
		msgs.setWrapStyleWord(true);
//		tempString.trim().split("\t");
		if(tempString.contains("\\s+"))
		{
			tempString += "\n";
		}
		JScrollPane scrollPane = new JScrollPane(msgs);
		temp.add(scrollPane);
		temp.setVisible(true);
		temp.setResizable(false);

		ChannelExec channel2 = null;
		try
		{
			channel2 = (ChannelExec) session.openChannel("exec");
		} catch (JSchException e)
		{
			e.printStackTrace();
		}
		BufferedReader in2 = null;
		try
		{
			in2 = new BufferedReader(new InputStreamReader(channel2.getInputStream()));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		channel2.setCommand("date;");
		try
		{
			channel2.connect();
		} catch (JSchException e)
		{
			e.printStackTrace();
		}

		String msg2 = "yeah";
		try
		{
			while ((msg2 = in2.readLine()) != null)
			{
				System.out.println(msg2);
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		channel.disconnect();
		channel2.disconnect();
		session.disconnect();

	}
	

	// String username = "benjamin";
	// String host = "192.168.17.75";
	// String password = "starwarsE1";
	// String remoteFilename = "ssh_config";
	// String localFilename = "stuff.txt";
	//
	//
	//
	// Session session = null;
	// Channel channel = null;
	//
	// try {
	//
	// JSch jsch = new JSch();
	// session = jsch.getSession(username, host, 22);
	// java.util.Properties config = new java.util.Properties();
	// config.put("StrictHostKeyChecking", "no");
	// session.setConfig(config);
	// session.setPassword(password);
	// session.connect();
	//
	// // exec 'scp -f rfile' remotely
	// String command = "scp -f " + remoteFilename;
	// channel = session.openChannel("exec");
	// ((ChannelExec) channel).setCommand(command);
	//
	// // get I/O streams for remote scp
	// OutputStream out = channel.getOutputStream();
	// InputStream in = channel.getInputStream();
	//
	// channel.connect();
	//
	// byte[] buf = new byte[1024];
	//
	// // send '\0'
	// buf[0] = 0;
	// out.write(buf, 0, 1);
	// out.flush();
	//
	// while (true) {
	// int c = checkAck(in);
	// if (c != 'C') {
	// break;
	// }
	//
	// // read '0644 '
	// in.read(buf, 0, 5);
	//
	// long filesize = 0L;
	// while (true) {
	// if (in.read(buf, 0, 1) < 0) {
	// // error
	// break;
	// }
	// if (buf[0] == ' ') {
	// break;
	// }
	// filesize = filesize * 10L + (long) (buf[0] - '0');
	// }
	//
	// String file = null;
	// for (int i = 0;; i++) {
	// in.read(buf, i, 1);
	// if (buf[i] == (byte) 0x0a) {
	// file = new String(buf, 0, i);
	// break;
	// }
	// }
	//
	// // send '\0'
	// buf[0] = 0;
	// out.write(buf, 0, 1);
	// out.flush();
	//
	// // read a content of lfile
	// FileOutputStream fos = null;
	//
	// fos = new FileOutputStream(localFilename);
	// int foo;
	// while (true) {
	// if (buf.length < filesize) {
	// foo = buf.length;
	// } else {
	// foo = (int) filesize;
	// }
	// foo = in.read(buf, 0, foo);
	// if (foo < 0) {
	// // error
	// break;
	// }
	// fos.write(buf, 0, foo);
	// filesize -= foo;
	// if (filesize == 0L) {
	// break;
	// }
	// }
	// fos.close();
	// fos = null;
	//
	// if (checkAck(in) != 0) {
	// System.exit(0);
	// }
	//
	// // send '\0'
	// buf[0] = 0;
	// out.write(buf, 0, 1);
	// out.flush();
	//
	// channel.disconnect();
	// session.disconnect();
	// }
	//
	// } catch (JSchException jsche) {
	// System.err.println(jsche.getLocalizedMessage());
	// } catch (IOException ioe) {
	// System.err.println(ioe.getLocalizedMessage());
	// } finally {
	// channel.disconnect();
	// session.disconnect();
	// }
	//
	// }

	// String hostname = null;
	// String username = null;
	// String password = null;
	//
	// // // create new Ssh instance
	// // Ssh ssh = new Ssh(hostname, username, password);
	// //
	// // // connect
	// // ssh.connect();
	//
	//
	// Ssh ssh = null;
	//
	// try
	// {
	// BufferedReader bin = new BufferedReader(new
	// InputStreamReader(System.in));
	// System.out.print("Enter SSH hostname: ");
	// hostname = bin.readLine();
	//
	// System.out.print("Enter SSH username: ");
	// username = bin.readLine();
	//
	// System.out.print("Enter SSH password: ");
	// password = bin.readLine();
	//
	// // create new Ssh instance
	// ssh = new Ssh(hostname, username, password);
	//
	// // register to capture events
	// ssh.addSshListener(this);
	//
	// System.out.println("Connecting please wait...");
	//
	// // connect
	// ssh.connect();
	//
	// // get output stream for writing data to SSH server
	// OutputStream out = ssh.getOutputStream();
	//
	// // holds line entered at console
	// String line = null;
	//
	// // read data from console
	// while (connected && (line = bin.readLine()) != null)
	// {
	// // send line with CRLF to SSH server
	// line += "\r\n";
	// try
	// {
	// out.write(line.getBytes());
	// out.flush();
	// } catch (Exception ioe)
	// {
	// connected = false;
	// }
	// }
	// } catch (Exception e)
	// {
	// e.printStackTrace();
	// } finally
	// {
	// try
	// {
	// if (connected)
	// {
	// ssh.disconnect();
	// }
	// } catch (Exception e)
	// {
	//
	// }
	// }

	// String user = "john";
	// String password = "mypassword";
	// String host = "192.168.100.23";
	// int port = 22;
	//
	// String remoteFile = "/home/john/test.txt";
	//
	// try
	// {
	// JSch jsch = new JSch();
	// Session session = jsch.getSession(user, host, port);
	// session.setPassword(password);
	// session.setConfig("StrictHostKeyChecking", "no");
	// System.out.println("Establishing Connection...");
	// session.connect();
	// System.out.println("Connection established.");
	// System.out.println("Crating SFTP Channel.");
	// ChannelSftp sftpChannel = (ChannelSftp) session.openChannel("sftp");
	// sftpChannel.connect();
	// System.out.println("SFTP Channel created.");
	//
	// InputStream out = null;
	// out = sftpChannel.get(remoteFile);
	// BufferedReader br = new BufferedReader(new InputStreamReader(out));
	// String line;
	// while ((line = br.readLine()) != null)
	// System.out.println(line);
	// br.close();
	// }
	// catch (Exception e)
	// {
	// System.err.print(e);
	// }
	// private int checkAck(InputStream in)
	// {
	// // TODO Auto-generated method stub
	// return 0;
	// }

	//
	// public void connected(SshConnectedEvent ev)
	// {
	// System.out.println("Connected: " + ev.getHost());
	// connected = true;
	// }
	//
	// public void disconnected(SshDisconnectedEvent ev)
	// {
	// System.out.println("Disconnected: " + ev.getHost() +
	// ". Press Enter to exit");
	// connected = false;
	// }
	//
	public void start()
	{

	}
}
