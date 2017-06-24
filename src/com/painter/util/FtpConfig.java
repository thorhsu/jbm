package com.painter.util;

public class FtpConfig {
	private String server = "TPEXSGH436075S";  
    private int port = 21;
	private String user = "jbm";  
    private String password = "1qazxsw2";  
    private String location = ""; 

    
    public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public void init(){
		server = Util.getString("jbm.backupFtp");  
	    port = Integer.parseInt(Util.getString("jbm.backupFtpPort"));
		user = Util.getString("jbm.backupFtpUser");  
	    try {
			password = Util.dehash(Util.getString("jbm.backupFtpPwd"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	     
	}

}
