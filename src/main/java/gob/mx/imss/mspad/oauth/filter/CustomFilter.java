package gob.mx.imss.mspad.oauth.filter;

import gob.mx.imss.mspad.oauth.dao.UsuarioRepository;
import gob.mx.imss.mspad.oauth.model.response.ErrorResponse;
import gob.mx.imss.mspad.oauth.service.IUsuarioService;
import gob.mx.imss.mspad.oauth.util.Crypto;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Itzi B. Enriquez R. LT
 * @Date 28 abr. 2022
 * @IMSS
 */
@Configuration
public class CustomFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomFilter.class);

    @Autowired
    IUsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("########## Initiating Custom filter OAUTH##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        LOGGER.info("########## Filter##########");

        Crypto crypto = new Crypto();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String getMethod = null;
        if (servletRequest instanceof HttpServletRequest) {
            getMethod = ((HttpServletRequest) servletRequest).getServletPath().toString();

        }

        if (!getMethod.contains("aplicacion")) {

            try {
                String pass2 = servletRequest.getParameter("password");
                String pass = crypto.encrypt(servletRequest.getParameter("password"));
             
                usuarioService.setPasswordAux(pass);
                String user = String.valueOf(servletRequest.getParameter("username"));
                usuarioService.loadUserByUsername(user).getUsername();
                
                LOGGER.info("Logging Request  {} : {}", request.getMethod(), request.getRequestURI());

                Map<String, String[]> modParams = new HashMap<String, String[]>(request.getParameterMap());
                modParams.put("username", new String[]{user});
                modParams.put("password", new String[]{pass});
                modParams.put("grant_type", new String[]{"password"});
                modParams.put("appId", new String[]{"AD"});
                HttpServletRequest modRqst = new ModParamHttpServletRequest(request, modParams);

                filterChain.doFilter(modRqst, servletResponse);
                LOGGER.info("Logging Response :{}", response.getContentType());
            } catch (UsernameNotFoundException e) {
            	
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.setCode(401);
                errorResponse.setMessage(e.getMessage());
                byte[] responseToSend = restResponseBytes(errorResponse);
                ((HttpServletResponse) response).setHeader("Content-Type", "application/json");
                ((HttpServletResponse) response).setStatus(401);
                response.getOutputStream().write(responseToSend);

                LOGGER.info("Exception e :{}", e.getMessage());

                e.printStackTrace();
            } 
        } else {
            LOGGER.info("Solicitando credenciales de header........");
            filterChain.doFilter(servletRequest, servletResponse);

        }

    }

    private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
        return serialized.getBytes();
    }
}