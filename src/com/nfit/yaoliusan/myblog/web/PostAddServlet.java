package com.nfit.yaoliusan.myblog.web;

import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.dao.PostDAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

// 添加博客
@WebServlet("/post/add")
@MultipartConfig
public class PostAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String author = req.getParameter("author");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Part cover = req.getPart("cover");
        try {
            // 将图片保存，并获取其路径名
            String coverPath = "/img/" + System.currentTimeMillis() + "-" + cover.getSubmittedFileName();
            cover.write(getServletContext().getRealPath("/") + coverPath);

            // 所有信息入库
            PostDAO postDAO = new PostDAO();

            Post post = postDAO.addPost(new Post(title, content, author, coverPath));

            // 跳转到详情页面更合理
            resp.getWriter().print(post.getId());
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print("-1");
        }
    }
}