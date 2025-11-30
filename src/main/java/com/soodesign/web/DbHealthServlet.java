package com.soodesign.web;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/db-health")
public class DbHealthServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void init() throws ServletException {
        super.init();
        // Spring Root WebApplicationContext 에서 SqlSessionTemplate 가져오기
        WebApplicationContext ctx =
                WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        this.sqlSessionTemplate = ctx.getBean(SqlSessionTemplate.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/plain; charset=UTF-8");

        try {
            Integer result = sqlSessionTemplate.selectOne("com.soodesign.mapper.TestMapper.selectOne");
            resp.getWriter().println("DB 연결 성공");
            resp.getWriter().println("SELECT 1 결과: " + result);
        } catch (Exception e) {
            resp.getWriter().println("DB 연결 실패");
            e.printStackTrace(resp.getWriter());
        }
    }
}
