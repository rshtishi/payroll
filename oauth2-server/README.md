# OAuth2 Server

*OAuth2 Server* is the service we utilize for validating the tokens of services requests without the client having to present their credentials to each service 
processing their request.

## Business Case

In microservice, we need to implement security in multiple services. We need an authentication service that will authenticate the user credentials and issue a token.
Every time the user tries to access a service protected by the authentication service, the authentication service will validate that the OAuth2 token was issued by it and that it hasn’t expired. The authentication service needs to:

- authenticate users (validate if the user is who they say they are)
- check their authorities (validate if the user have has permission to do what they are trying to do)
- keep the service running on patched and up-to-date to minimize the risk of vulnerabilities

## Technology

*OAuth2 Server* is using the following technologies:

- Java [version: 11] (the language used to write the application)
- Maven [version: 3.6] (the tool for managing dependencies and building the project)
- Lombok [version: 1.18.8] (for generating the POJO boilerplate code)
- Liquibase [version: 3.8.7] (for managing the database changes)
- H2 Database [version: 1.4.200] (for providing a relational database)
- Spring-Boot [version: 2.2.5.RELEASE] (the framework for creating spring application that just run)
- Spring-Boot-Data-JPA [version: 2.2.5.RELEASE] (the dependency for easier access and manipulation of relational database)
- Spring-Cloud [version: Hoxton.SR3] (the framework for building some of the common patterns in distributed systems)
- Spring-Cloud-Config-Client [version: 2.2.2.RELEASE] (the dependency for binding the client to the Configuration Server Service)
- Spring-Cloud-Netflix-Client [version: 2.2.2.RELEASE] (the dependency for register the client to the Eureka Server Service)
- Spring-Cloud-Security [version: 2.2.1.RELEASE] (the dependency that provides features related to token-based security )
- Spring-Cloud-OAUTH2 [version: 2.1.2.RELEASE] (the dependency that provides needed for implementing the OAUTH2 server)

## Architecture

![OAuth2 Server Architecture](https://github.com/rshtishi/payroll/blob/master/oauth2-server/src/main/resources/static/images/oauth2-server.jpeg)

## Implemenations Details

### Configuring Communication With *Configuration Server*

The configuration information for *OAuth2 Server* is located outside the service code. Below is the dependency needed to communicate with *Configuration Server* for retrieving *OAuth2 Server* configuration information:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-client</artifactId>
		</dependency>
```

After the Maven dependencies have been defined, you need to tell the *OAuth2 Server* service where to contact the *Configuration Server*. We have configured the application name for the service, the application profile, and the URI to connect to a *Configuration Server* in the bootstrap.properties file. Below it is the configuration information:

```
spring.application.name=oauth2
spring.profiles.active=default
cloud.config.uri=http://localhost:8888
```

### Configuring Communication With *Eureka Server*

Second, we need to register *Oauth2 Server* to service discovery. The first thing to be done is adding the Spring Eureka Client dependency to the *OAuth2 Server* service’s pom.xml file like below:

```
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
```

Then we need to add the configuration in the application.properties file of the *OAuth2 Server* to enable communication with Eureka. Below are the configuration information that are added:

```
#Eureka
eureka.instance.preferIpAddress=true
eureka.client.registerWithEureka=true
eureka.client.fetchRegistry=true
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
ribbon.ReadTimeout=60000
```

Every service registered with Eureka will have two components associated with it: the application ID and the instance ID.The application ID is used to represent a group service instance. In a Spring-Boot-based microservice, the application ID will always be the value set by the ```spring.application.name property```. The ```eureka.instance.preferIpAddress``` property tells Eureka that you want to register the service’s IP address to Eureka rather than its hostname. The ```eureka.client.registerWithEureka``` attribute is the trigger to tell the Gateway Server service to register itself with Eureka, and the ```eureka.client.fetchRegistry``` attribute is used to tell the Spring Eureka Client to fetch a local copy of the registry. Every 30 seconds, the Gateway Server will re-contact the Eureka Server service for any changes to the registry. The last attribute, the ```eureka.serviceUrl.defaultZone``` attribute, holds a comma-separated list of Eureka Server services that the Gateway Server will use to resolve to service locations.

### Configuring Communication With the Database

Next, we add the dependencies needed for the *OAuth2 Server* to communicate with the database. Below are the dependencies:

```
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
                <dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
    
```

Below we have configured the *OAuth2 Server* to communicate with H2 database:

```
#H2 DB 
spring.jpa.hibernate.ddl-auto=none
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:departmentdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password={cipher}AQApFhvGmUI3R8ekFYlYo0n/TmYAJMuYIqPpE65jRzdQjmjWwgyLMnevmjytmD2krEDIasLMt5UaQwUjA+TiF036Oilny2nHnCJeqkn/ZKpxavyrrmBG9EACBUt/5I7ztZRNQGAfHflB1OM4WamBJ+7aD1MFSjqervyXOzE547DvWsRYH++WF/380jY5oCCLwiPf1QBCDW6qYE6MgmbZYkq4Rk27c0xgMq4NdSJFrUSpd0D9cuG1eKRFS2ZXwUOAGb5ksiJrWAbliaYq0g5MKvbw1YwLXKhnHysdwMyIZ6faWRF2OYuagf40fB1N3T2sp8o9MZo7x1Kxt9mc1CeigYjH6OOK/pfLn6HqLuoKV9ZPNVCpwysBV8b/FBgC+wqHPuk=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

### Configuring Liquibase for Managing to Manage Database Schema Changes

 We have chosen **Liquibase** to manage database schema changes. Below is the dependency needed for including Liquibase:
 
 ```
 <dependency>
    <groupId>org.liquibase</groupId>
    <artifactId>liquibase-core</artifactId>
</dependency>
 ```
 
Below is the configuration information for Liquibase:

```
#Liquibase 
spring.liquibase.change-log=classpath:db/liquibase-changelog.xml
spring.liquibase.enabled=true
```

Liquibase uses a changelog to explicitly list database changes in order. The changelog acts as a ledger of changes and contains a list of changesets (units of change) that Liquibase can execute on a database. The property ```spring.liquibase.enabled``` enable Liquibase in our application and we determine the location of changelog
file in ```spring.liquibase.change-log``` property.

Below is the content of **liquibase-changelog.xml**:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">

	<include file="changelog/01-create-scheme.xml"
		relativeToChangelogFile="true" />
	<include file="changelog/02-data-insert.xml"
		relativeToChangelogFile="true" />
		
</databaseChangeLog>
```

As we can see above, we have grouped the changeset in two files that are included in liquibase-changelog.xml.

The **01-create-scheme.xml** file has the changeset for creating the schema of the database, as we can see below:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet id="01" author="rshtishi">

		<createTable tableName="users">
			<column name="username" type="varchar(50)">
				<constraints nullable="false" primaryKey="true" />
			</column>
			<column name="password" type="varchar(100)">
				<constraints nullable="false" />
			</column>
			<column name="enabled" type="tinyint">
				<constraints nullable="false" />
			</column>
		</createTable>

		<createTable tableName="authorities">
			<column name="username" type="varchar(50)">
				<constraints nullable="false" foreignKeyName="users"
					references="users(username)" />
			</column>
			<column name="authority" type="varchar(50)">
				<constraints nullable="false" />
			</column>
		</createTable>

		<createIndex indexName="ix_auth_username"
			tableName="authorities" unique="true">
			<column name="username" />
			<column name="authority" />
		</createIndex>

	</changeSet>
</databaseChangeLog>
```

The **02-data-insert.xml** file has the changeset for populating the tables with information. Below is the content of the file:

```
<databaseChangeLog
	xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog
         http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.1.xsd">
	<changeSet id="02" author="rshtishi">
	
		<insert tableName="users">
			<column name="username" value="rando" />
			<column name="password" value="$2y$12$Pz8l0N8a3e.H0vYijmyOLu5hkD5ym1ecLVji7UkxjhHyee4oPFiY6" />
			<column name="enabled" valueNumeric="1" />
		</insert>
		
		<insert tableName="authorities">
			<column name="username" value="rando" />
			<column name="authority" value="ROLE_USER" />
		</insert>
		
	</changeSet>
</databaseChangeLog>
```

### Configuring the *OAuth2 Server*

Finally, we add the dependencies needed for creating the OAuth2 authentication service.

```
     <dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-oauth2</artifactId>
     </dependency>

     <dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-security</artifactId>
     </dependency>

     <dependency>
       <groupId>org.springframework.security.oauth.boot</groupId>
       <artifactId>spring-security-oauth2-autoconfigure</artifactId>
       <version>2.1.2.RELEASE</version>
     </dependency>
```

After importing the dependencies, we add the bootstrap below:

```
@SpringBootApplication
@EnableResourceServer
@EnableAuthorizationServer
public class Oauth2ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(Oauth2ServerApplication.class, args);
	}

}
```

The ```@EnableAuthorizationServer``` annotation tells Spring Cloud that this service will be used as an OAuth2 authentication service and to add several REST-based endpoints that will be used in the OAuth2 authentication and authorization processes. The ```@EnableResourceServer``` annotation tells Spring Cloud and Spring Security that the service is a protected resource. As a result, all incoming calls to the service are intercepted by a filter to check if a valid access token is present in the incoming call's HTTP header.

We have added an endpoint ```/user```. Below is the implementation of the endpoint:

```
@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@GetMapping(produces="application/json")
	public Map<String,Object> getUser(OAuth2Authentication user){
		Map<String,Object> userInfo = new HashMap<>();
		userInfo.put("user",user.getUserAuthentication().getPrincipal());
		userInfo.put("authorities", user.getUserAuthentication().getAuthorities());
		return userInfo;
	}

}
```

This endpoint is called by the protected service to validate the OAuth2 access token and retrieve the assigned roles of the user accessing the protected service.

Below is the OAuth2 configuration information for *OAuth2 Server* service:

```
#OAuth2
oauth2.client.id=payroll
oauth2.client.secret={cipher}AQCDI1A0zx63kc+PQa2RJt781XoVCoOo+n44NiE6V0q5GKPGKbncd++xzWnziENixnVd8rRRhmuJewVN0Mi1tzHuB8GeV1RGwNGVrHVqhzU1dyMkMwjjSN078OVI+M8HflS7ddicEJecxlvNLTiWSz6Hw1qfhHXs6M1kB3134sIBRBomTBR3IYxG6laulh1NvC8JF1uCR2MNyzShOyNGtjDTpitAYTrAUKGiS9yweG8wtKtUiPxPouN90XeuFihzR93iuWGwyg0wh6m3a661kcna7XZtFhs/hOOkl1nNPMUYHI4DO6zNMYOCI+8Z4U6TOYuBFGrCWPgWEehb+ffehe2FOJ3zRmpmciWN1EW47ewz1P3sLk4a+ylm7dWbld/HkVE=

oauth2.jwt.key-store=classpath:oauth2cer.jks
oauth2.jwt.key-store-password={cipher}AQAcKaqe5PHVZKpy2o7aQ3N8pxcHG0TXRJKZm5ynq/taeFMwXSUFjX8GefiBtqsAaatUZMN0e3h2EFs+6ZLEVQHeNKd/GbFxT2IFRlBTEb1aMFenAbCEHk2XobBm321quammYSWMpQXSzrTKJGyZp6LZ3Rbg6vS5X2tANav0Lp+I1YYhUKqQELLnF81zcsVeibgQQXzMOAEDt+hfkMY3M8JXRKO/ydhEeWRIZfNZ/c3P4I9U/jTavIWWiMBPYlqznuWl2jSl9KzbKCtw9dy5qyEDqMoiOkvht/MToc+k4OnzkqcWw+BnrFoxnAvHw/nZfNijNGWKG0LGg1JdzY5l4HSqfFZlEog/buCjRu7rC4DY5k08wo1xUNdDPcx+ZpNEfk0=
oauth2.jwt.key-pair-alias=oauth2signedcer
oauth2.jwt.key-pair-password={cipher}AQAcKaqe5PHVZKpy2o7aQ3N8pxcHG0TXRJKZm5ynq/taeFMwXSUFjX8GefiBtqsAaatUZMN0e3h2EFs+6ZLEVQHeNKd/GbFxT2IFRlBTEb1aMFenAbCEHk2XobBm321quammYSWMpQXSzrTKJGyZp6LZ3Rbg6vS5X2tANav0Lp+I1YYhUKqQELLnF81zcsVeibgQQXzMOAEDt+hfkMY3M8JXRKO/ydhEeWRIZfNZ/c3P4I9U/jTavIWWiMBPYlqznuWl2jSl9KzbKCtw9dy5qyEDqMoiOkvht/MToc+k4OnzkqcWw+BnrFoxnAvHw/nZfNijNGWKG0LGg1JdzY5l4HSqfFZlEog/buCjRu7rC4DY5k08wo1xUNdDPcx+ZpNEfk0=
```

We have mapped the OAuth2 configuration information to the class below:

```
@Getter
@Setter
@ConfigurationProperties(prefix = "oauth2", ignoreUnknownFields = false)
public class OAuth2ConfigParameters {
	
	private Client client;
	private Jwt jwt;

	@Getter
	@Setter
	public static class Client {
		private String id;
		private String secret;
	}
	
	@Getter
	@Setter
    public static class Jwt {
        private Resource keyStore;
        private String keyStorePassword;
        private String keyPairAlias;
        private String keyPairPassword;   
	}
}
```

The ```oauth2.client.id``` property holds the name application we are registering and ```oauth2.client.secret``` property holds the password that will be presented
when the payroll services call the *OAuth2 Server*  to receive the OAuth2 access token. The ```oauth2.jwt.*``` properties hold the information of the self-signed the certificate that we are going to use to sign the generated JWT tokens.


In the configuration class below, you’ll find all the required Spring ```@Beans``` for JWT token.

```
@Configuration
public class JWTTokenStoreConfig {

	@Autowired
	private OAuth2ConfigParameters oAuth2ConfigParameters;

	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(oAuth2ConfigParameters.getJwt().getKeyStore(),
				oAuth2ConfigParameters.getJwt().getKeyStorePassword().toCharArray());
		KeyPair keyPair = keyStoreKeyFactory.getKeyPair(oAuth2ConfigParameters.getJwt().getKeyPairAlias());
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setKeyPair(keyPair);
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	@Bean
	@Primary
	public DefaultTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}

	@Bean
	public OAuth2ConfigParameters oAuth2ConfigParameters() {
		return new OAuth2ConfigParameters();
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new JwtTokenEnhancer();
	}
	
}
```

The ```JWTTokenStoreConfig``` class is used to define how Spring will manage the creation, signing, and translation of a JWT token. The ```JwtAccessTokenConverter``` uses the self-signed certificate to sign the generated tokens. The ```JwtTokenStore``` reads data from the tokens themselves. The ```DefaultTokenServices``` uses the ```TokenStore``` to persist the tokens.

Below is the *OAuth2 Server* configuration class:

```
@Configuration
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	OAuth2ConfigParameters oauth2ConfigParameters;

	@Autowired
	private TokenStore tokenStore;
	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired
	private TokenEnhancer tokenEnhancer;
	@Autowired
	private DefaultTokenServices defaultTokenServices;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer, jwtAccessTokenConverter));
		defaultTokenServices.setTokenEnhancer(tokenEnhancerChain);
		endpoints.tokenStore(tokenStore).accessTokenConverter(jwtAccessTokenConverter).tokenEnhancer(tokenEnhancerChain)
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(oauth2ConfigParameters.getClient().getId())
				.secret(passwordEncoder.encode(oauth2ConfigParameters.getClient().getSecret()))
				.authorizedGrantTypes("refresh_token", "password", "client_credentials")
				.scopes("webclient", "mobileclient");
	}
}

```

The ```OAuth2Config``` class is the configuration of *OAuth2 Server* and extends the ```AuthenticationServerConfigurer``` class. The ```AuthenticationServerConfigurer``` class is a core piece of Spring Security. It provides the basic mechanisms for carrying out key authentication and authorization functions. 

The first method, ```configure()```, we configured to use the default authentication manager and user detail service that comes with Spring. Also, in the methods ```tokenStore()```, ```accessTokenConverter()``` and ```tokenEnhancer()``` we have injected the beans for creating and signing JWT token. This is the hook to tell
the Spring Security OAuth2 to use **signed JWT token**.

The second method, ```configure()```, is used to define what client applications are registered with your authentication service. The ```ClientDetailsServiceConfigurer``` class supports two different types of stores for application information: an in-memory store and a JDBC store. In this example,
we have chosen an in-memory store for simplicity reasons. The two method calls ```withClient()``` and ```secret()``` provide the name of the application (payroll) that you’re registering along with a secret (a password) that will be presented when the payroll application calls your *OAuth2 Server* to receive an OAuth2 access token.
The next method, ```authorizedGrantTypes()```, is passed a comma-separated list of the authorization grant types that will be supported by your *OAuth2 Server*. In our service, we’ll support the password and client credential grants. The ```scopes()``` method is used to define the boundaries that the calling application can operate in when they’re asking your *OAuth2 Server* for an access token.


Below is the class that we use to add additional information to JWT token:

```
public class JwtTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> info = new HashMap<>();
        info.put("details", authentication.getDetails());
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		return accessToken;
	}

}

```

Below we have configured the users and roles availabe in payroll application:

```
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder())
				.usersByUsernameQuery("select username,password,enabled from users where username = ?")
				.authoritiesByUsernameQuery("select username,authority from authorities where username = ?");
	}
}

```


The ```WebSecurityConfigurer``` class extends the ```WebSecurityConfigurerAdapter``` class and mark it with the ```@Configuration``` annotation. In the ```configure()``` method we have chosen the authentication by JDBC. The users and their roles information are stored in the H2 database. We have configured the queries
that need to retrieve the user and authorities that the user has. Also, we have configured BCrypt for encryption of passwords.



## Setup

Prerequisite needed before setup:

- Git
- GitHub
- Configuration Server should be started and running
- Eureka Server should be started and running

Execute the following commands:

- mvn clean install (to build the project)
- mvn spring-boot:run (to run the project)

To test authentication of user, create a request in postman with the following attributes:

- ```url: http://localhost:8901/oauth/token``` [Http Method: POST]
- ```Authentication Type: Basic``` [username:payroll, password: test]
- ```Body: Form Data``` [grant_type:password, scope:webclient, username:rando, password:test]

Now that you have a valid OAuth2 access token, we can use the /auth/user endpoint that we created in our authentication service to retrieve information about the user
associated with the token.

In postman create the request below to access the user information:

- ```url:http://localhost:8901/user``` [Http Method:Get]
- ```Authentication Type:Bearer Token``` [Token:access token]
