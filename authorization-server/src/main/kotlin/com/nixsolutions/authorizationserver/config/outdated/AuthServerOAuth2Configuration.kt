package com.nixsolutions.authorizationserver.config.outdated

private const val CLIENT_ID = "financialApp";

/*
@Configuration
@EnableAuthorizationServer
open class AuthServerOAuth2Configuration : AuthorizationServerConfigurerAdapter() {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private lateinit var authenticationManager: AuthenticationManager;

    @Autowired
    private lateinit var dataSource: DataSource;

    @Override
    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource)
                .withClient(CLIENT_ID)
                .authorizedGrantTypes("implicit")
                .scopes("read")
                .autoApprove(true)
                .and()
                .withClient("clientIdPassword")
                .secret("secret")
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("read");
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore())
                .authenticationManager(authenticationManager);
    }

    @Bean
    open fun tokenStore(): TokenStore {
        return JdbcTokenStore(dataSource)
    }
}*/
