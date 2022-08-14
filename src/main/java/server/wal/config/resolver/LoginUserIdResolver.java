package server.wal.config.resolver;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import server.wal.common.exception.custom.InternalServerException;
import server.wal.config.interceptor.Auth;
import server.wal.config.security.JwtConstants;

@Component
public class LoginUserIdResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUserId.class) && parameter.getParameterType().equals(Long.class);
    }

    @NotNull
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (parameter.getMethodAnnotation(Auth.class) == null) {
            throw new InternalServerException("@Auth 어노테이션이 필요한 컨트롤러입니다");
        }
        Object object = webRequest.getAttribute(JwtConstants.USER_ID, 0);
        if (object == null) {
            throw new InternalServerException(String.format("USER_ID 를 가져오지 못했습니. (%s - %s)", parameter.getClass(), parameter.getMethod()));
        }
        return object;
    }

}
