package uk.co.froot.openid.auth;

import com.google.common.base.Optional;

public interface SessionProvider<U extends OpenIDUser> {

	Optional<U> getBySessionId(String sessionId);
	
}
