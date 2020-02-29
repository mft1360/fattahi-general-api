package com.fattahi.general.test;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fattahi.general.feignclient.LoginResponse;
import com.fattahi.general.feignclient.LoginService;

/**
 * Base Test for all the test classes. By, default every query will be rolled
 * back because of @Transactional To run real/integration test change the
 * profile to dev
 *
 * @author mohsenfattahi81@gmail.com
 */

@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class BaseTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private LoginService loginService;

	@Value("${oauth.client.id}")
	String clientId;

	@Value("${oauth.client.password}")
	String clientPassword;

	@Value("${oauth.grant.type}")
	String grantType;

	@Value("${oauth.username}")
	String username;

	@Value("${oauth.password}")
	String password;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	protected MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void test() {
	}

	protected LoginResponse logintest() {

		String authStringEnc = null;
		String authorization = clientId + ":" + clientPassword;
		byte[] authEncBytes = Base64.encode(authorization.getBytes());
		authStringEnc = new String(authEncBytes);
		LoginResponse reponse = null;
		try {
			Object obj = loginService.login("Basic " + authStringEnc, grantType, username, password);
			return reponse;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
