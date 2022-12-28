package com.wjw.takeaway.filter;

import com.alibaba.fastjson.JSON;
import com.wjw.takeaway.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: 登录过滤器
 *
 * @author wjw
 * @date 2022年12月28日 14:30
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    /**
     * 路径匹配器，支持通配符
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 设置白名单
     */
    protected static List<String> urls = new ArrayList<>();

    // 静态代码块
    static {
        urls.add("/employee/login");
        urls.add("/employee/logout");
        urls.add("/backend/**");
        urls.add("/front/**");
    }

    /**
     * 代表登录存放在session的用户id
     */
    protected static final String EMP_ID = "emp_id";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 1. 获取本次请求的URI
        String requestURI = request.getRequestURI();

        // 2. 判断本次请求是否需要处理
        boolean check = check(requestURI);
        // 3. 若不需要处理，直接放行
        if (check) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute(EMP_ID) != null) {
            log.info("用户已登录，用户ID = {}", request.getSession().getAttribute(EMP_ID));
            // 放行
            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        // 5. 如果未登录，返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
    }

    /**
     * 路径匹配，检查本次请求是否放行
     *
     * @param requestURI
     * @return
     */
    public boolean check(String requestURI) {
        boolean match = false;
        for (String url : urls) {
            match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
