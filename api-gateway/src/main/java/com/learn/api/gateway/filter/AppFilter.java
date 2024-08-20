package com.learn.api.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AppFilter implements GlobalFilter {

    final Logger logger = LoggerFactory.getLogger(AppFilter.class);

    private static final List<String> WHITELISTED_PATHS = List.of(
            "/auth/login",
            "/auth/register"
//            "/public/**"  // Use wildcard if you want to whitelist a whole directory
    );

    private boolean isWhitelisted(String path) {
        // Check if the path matches any whitelisted paths
        return WHITELISTED_PATHS.stream().anyMatch(path::startsWith);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        String path = request.getPath().toString();

        // Check if the request path is whitelisted
        if (isWhitelisted(path)) {
            return chain.filter(exchange);
        }
        
        String header = headers.get("Authorization").toString(); // TODO FIX 500 internal server error
        
        if (header == null || header.isEmpty()) {
            return sendErrorResponse(exchange, "Authorization header is missing");
        }

        if (header != null
                && !header.isBlank()
                && header.length() > 8
                && header.startsWith("[Bearer ")) {
            String token = header.substring(8, header.length() - 1);

            try {
                Claims claims =
                        Jwts.parser().setSigningKey("TEAM1FYNDNATECHCORP").parseClaimsJws(token).getBody();
                String emailId = claims.getSubject();
                // if we want to send the email too
                //        ServerHttpRequest modifiedRequest = request.mutate().header("X-Email-Id",
                // emailId).build();
                //        return chain.filter(exchange.mutate().request(modifiedRequest).build());
                return chain.filter(exchange);

            } catch (Exception e) {
                return sendErrorResponse(exchange, "Token is invalid");
            }

        } else {
            return sendErrorResponse(exchange, "Token is missing");
            // I have to send error
        }
    }

    private Mono<Void> sendErrorResponse(ServerWebExchange exchange, String errorMessage) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String errorJson = String.format("{\"error\": \"%s\"}", errorMessage);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(errorJson.getBytes());
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }
}
