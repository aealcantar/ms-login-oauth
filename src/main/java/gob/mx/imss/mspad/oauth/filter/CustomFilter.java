package gob.mx.imss.mspad.oauth.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import gob.mx.imss.mspad.oauth.dao.UsuarioRepository;
import gob.mx.imss.mspad.oauth.model.response.ErrorResponse;
import gob.mx.imss.mspad.oauth.service.IUsuarioService;
import gob.mx.imss.mspad.oauth.util.Constants;
import gob.mx.imss.mspad.oauth.util.Crypto;

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

	@Value("${ms.login.oauth.app.name}")
	private String appName;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info(" Initiating Custom filter");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		LOGGER.info("initDoFilter");

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String getMethod = null;
		if (servletRequest instanceof HttpServletRequest) {
			getMethod = ((HttpServletRequest) servletRequest).getServletPath();

		}
		Crypto crypto = new Crypto();
		if (getMethod != null && !getMethod.contains(Constants.APLICACION)) {

			try {
//				String pass2 = servletRequest.getParameter(Constants.PASSWORD);
				String pass = crypto.encrypt(servletRequest.getParameter("password"));


				LOGGER.info("set PasswordAux");
				usuarioService.setPasswordAux(pass);

				String user = servletRequest.getParameter(Constants.USERNAME);

				LOGGER.info("loadUserByUsername: {}", user);

				usuarioService.loadUserByUsername(user).getUsername();

				LOGGER.info("addModParameter");
				Map<String, String[]> modParams = new HashMap<String, String[]>(request.getParameterMap());
				modParams.put(Constants.USERNAME, new String[] { user });
				modParams.put(Constants.PASSWORD, new String[] { pass });
				modParams.put(Constants.GRANT_TYPE, new String[] { Constants.PASSWORD });
				modParams.put(Constants.APPID, new String[] { appName });
				HttpServletRequest modRqst = new ModParamHttpServletRequest(request, modParams);

				LOGGER.info("filterChain.doFilter");
				filterChain.doFilter(modRqst, servletResponse);
				LOGGER.info("CUSTOMFILTER Response :{}", response.getContentType());

			} catch (UsernameNotFoundException e) {

				ErrorResponse errorResponse = new ErrorResponse();
				errorResponse.setCode(401);
				errorResponse.setMessage(e.getMessage());
				byte[] responseToSend = restResponseBytes(errorResponse);
				response.setHeader(Constants.CONTENTTYPE, MediaType.APPLICATION_JSON_VALUE);
				response.setStatus(401);
				response.getOutputStream().write(responseToSend);

				LOGGER.info("UsernameNotFoundException e :{}", e.getMessage());

				e.printStackTrace();
			}
		} else {
			LOGGER.info("Solicitando credenciales de header:");
			filterChain.doFilter(servletRequest, servletResponse);

		}

	}

	private byte[] restResponseBytes(ErrorResponse eErrorResponse) throws IOException {
		String serialized = new ObjectMapper().writeValueAsString(eErrorResponse);
		return serialized.getBytes();
	}
}