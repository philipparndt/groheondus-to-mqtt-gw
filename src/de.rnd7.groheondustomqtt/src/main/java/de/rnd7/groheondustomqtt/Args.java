package de.rnd7.groheondustomqtt;

import com.beust.jcommander.Parameter;

public class Args {
	@Parameter(names = "-login", description = "Fetch token")
	private boolean fetchtoken = false;

	@Parameter(names = "-username", description = "Username", required = false)
	private String username;

	@Parameter(names = "-password", description = "Password", required = false)
	private String password;
	
	public boolean isFetchtoken() {
		return this.fetchtoken;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getPassword() {
		return this.password;
	}

}