package com.ianhein.immiblog2.Service;

import com.ianhein.immiblog2.dao.BlogRepository;
import com.ianhein.immiblog2.domain.Blog;
import com.ianhein.immiblog2.exception.NotFoundException;
import com.ianhein.immiblog2.util.MDResolvleUtils;
import com.ianhein.immiblog2.util.MyBeanUtils;
import com.ianhein.immiblog2.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Override
    public Map<String, List<Blog>> blogArchives() {
        List<String> years =  blogRepository.findGroupYear();
        Map<String,List<Blog>> map = new TreeMap<>((o1,o2)->Integer.parseInt(o2)-Integer.parseInt(o1));
        for (String year:years) {
            map.put(year,blogRepository.findByYear(year));
        }

        return map;
    }

    @Override
    public Long countBlog() {
        return blogRepository.count();
    }

    @Autowired
    BlogRepository blogRepository;


    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getById(id);
    }

    @Transactional
    @Override
    public Blog getAndRender(Long id) {
        Blog blog = blogRepository.getById(id);
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();

        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MDResolvleUtils.markdownToHtmlExtensions(content));
        blogRepository.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tagList");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Blog getBlogByTitle(String name) {
        return blogRepository.findByTitle(name);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blogQuery) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blogQuery.getTitle()) && blogQuery.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blogQuery.getTitle()+"%"));
                }
                if(blogQuery.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.get("type").get("id"),blogQuery.getTypeId()));
                }
                if(blogQuery.isRecommended()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("is_recommended"),blogQuery.isRecommended()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public List<Blog> listBlog(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {

        return blogRepository.findByQuery(query,pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {

        if (blog.getId() == null) {
            blog.setInitialTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }


        return blogRepository.save(blog);
    }

    @Transactional
    @Override
    public Blog updateBlog(Long id,
                           Blog blog) {
        Blog b = blogRepository.getById(id);

        if(b==null){
            throw new NotFoundException("未查询到相关博客,无法更新");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }
}
