//package com.example.Antoflix.jwt;
//
//import com.example.Antoflix.service.security.CustomUserDetailsService;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class JwtFilter extends OncePerRequestFilter {
//    private final JwtUtil jwtUtil;
//    private final CustomUserDetailsService service;
//
//    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService service) {
//        this.jwtUtil = jwtUtil;
//        this.service = service;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if(authHeader != null && authHeader.startsWith("Bearer ")){
//            String token = authHeader.substring(7);
//
//            try{
//                String email = jwtUtil.validateTokenAndRetrieveSubject(token);
//
//                UserDetails userDetails = service.loadUserByUsername(email);
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            } catch (Exception e){
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
