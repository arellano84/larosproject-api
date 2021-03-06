package com.laros.api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.laros.api.config.property.LarosProjectApiProperty;
import com.laros.api.config.token.CustomTokenEnhancer;

@Profile("oauth-security")
@Configuration
/*	25.3. Modificações para o Spring Security 5
@EnableAuthorizationServer*/
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private LarosProjectApiProperty larosProjectApiProperty;
	
	@Autowired
	private UserDetailsService userDetailsService; //25.3. Modificações para o Spring Security 5
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient(larosProjectApiProperty.getSeguridad().getWithClientOne()) //25.00.00 Mejoras: externalización de datos de Authorization.
			.secret(larosProjectApiProperty.getSeguridad().getSecretClientOne())
			.scopes("read","write")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(larosProjectApiProperty.getSeguridad().getAccessTokenValiditySeconds()) // 30 mins 1800 // The accessToken is valid for X time:
			.refreshTokenValiditySeconds(larosProjectApiProperty.getSeguridad().getRefreshTokenValiditySeconds()) // The resource token is valid for 1 day (3600*24).
		.and() 
			.withClient(larosProjectApiProperty.getSeguridad().getWithClientTwo())
			.secret(larosProjectApiProperty.getSeguridad().getSecretClientTwo())
			.scopes("read")
			.authorizedGrantTypes("password", "refresh_token")
			.accessTokenValiditySeconds(larosProjectApiProperty.getSeguridad().getAccessTokenValiditySeconds())
			.refreshTokenValiditySeconds(larosProjectApiProperty.getSeguridad().getRefreshTokenValiditySeconds())
			;
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		
		//7.5. Nome do usuário no token JWT
		//Token con mas detalles
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		//Agregando cadena de tokens, incrementando la información.
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		
		//
		endpoints
			.tokenStore(tokenStore())
//			.accessTokenConverter(accessTokenConverter())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)
			.userDetailsService(userDetailsService) // 25.3. Modificações para o Spring Security 5
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		accessTokenConverter.setSigningKey("algaworks");//senha que valida token
		return accessTokenConverter;
	}
	
	@Bean
	public TokenStore tokenStore() {
//		return new InMemoryTokenStore();//Podria estar en BD también.
		return new JwtTokenStore(accessTokenConverter());
	}
	
	/*
	 * Agrega el nuevo token con datos de Nombre Usuario
	 * */
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer();
	}

}
