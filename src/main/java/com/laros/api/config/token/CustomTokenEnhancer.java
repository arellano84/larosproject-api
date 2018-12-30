package com.laros.api.config.token;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.laros.api.security.UsuarioSistema;

/*
 * 7.5. Nome do usuário no token JWT
 * Cara que aumenta información del Token.
 * */
public class CustomTokenEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accesToken, OAuth2Authentication autentication) {
		UsuarioSistema usuarioSistema = (UsuarioSistema) autentication.getPrincipal();
		
		Map<String, Object> addInfo = new HashMap<>();
		addInfo.put("usuario_nombre", usuarioSistema.getUsuario().getNome());
		
		((DefaultOAuth2AccessToken) accesToken).setAdditionalInformation(addInfo);
		return accesToken;
	}
}
