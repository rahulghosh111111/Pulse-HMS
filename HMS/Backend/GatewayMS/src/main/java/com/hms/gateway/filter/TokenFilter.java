package com.hms.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class TokenFilter extends AbstractGatewayFilterFactory<TokenFilter.Config> {
    private static final String SECRET = "d44c3b4d70200c65b24ba7d10339868e580d071a5c7cef7aa21f3ac82286dafd04cd708e2e9182b9b8ad715cc5603922a9c81ce82bd735175c4ed559e11c878b";

    public TokenFilter() {
        super(Config.class);
    }

    public static class Config {
        // Configuration properties for the filter can be added here
    }

    @Override
    public GatewayFilter apply(Config config) {
        // TODO Auto-generated method stub
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().toString();
            if (path.matches(".*/user/(login|register).*")) {

                return chain.filter(exchange.mutate()
                        .request(r -> r.header("X-Secret-Key", "SECRET")).build());
            }
            HttpHeaders headers = exchange.getRequest().getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Authorization header is missing");
            }
            String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new RuntimeException("Invalid Authorization header");
            }
            String token = authHeader.substring(7);
            // Here you would typically validate the token and set the authentication in the
            // SecurityContext
            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();

                exchange = exchange.mutate()
                        .request(r -> r.header("X-Secret-Key", "SECRET")).build();

            } catch (Exception e) {
                throw new RuntimeException("Invalid token");
            }
            return chain.filter(exchange);
        };
    }

}
