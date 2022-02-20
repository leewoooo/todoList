package leewoooo.todo.interceptor;

import leewoooo.todo.config.Apikey;
import leewoooo.todo.dto.error.ErrorCode;
import leewoooo.todo.exception.todo.CustomHttpException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequiredArgsConstructor
@Component
public class HttpInterceptor implements HandlerInterceptor {

    private final Apikey apikey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // exclude Method get
        if (request.getMethod().equals(HttpMethod.GET.toString())) {
            return true;
        }

        if(apikey.getApikey().equals( request.getParameter("apikey"))){
            return true;
        }

        throw new CustomHttpException(ErrorCode.NOT_AUTHORIZED);
    }
}
