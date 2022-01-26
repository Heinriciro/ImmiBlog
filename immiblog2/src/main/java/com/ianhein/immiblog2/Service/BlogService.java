package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.domain.Blog;
import com.ianhein.immiblog2.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndRender(Long id);

    Blog getBlogByTitle(String title);

    Page<Blog> listBlog(Pageable pageable,BlogQuery blogQuery);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable, Long tagId);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> listBlog(Integer size);

    Map<String,List<Blog>> blogArchives();

    Long countBlog();

    Blog saveBlog(Blog blog);


    Blog updateBlog(Long id,Blog blog );

    void deleteBlog(Long id);
}
