package com.ianhein.immiblog2.web.admin;


import com.ianhein.immiblog2.Service.BlogService;
import com.ianhein.immiblog2.Service.TagService;
import com.ianhein.immiblog2.Service.TypeService;
import com.ianhein.immiblog2.domain.Blog;
import com.ianhein.immiblog2.domain.Tag;
import com.ianhein.immiblog2.domain.User;
import com.ianhein.immiblog2.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String CREATE = "admin/blogRelease";
    private static final String BLOGS = "admin/blogManage";
    private static final String LIST = "admin/blogs :: blogList";


    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size=8,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery bq,
                        Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,bq));
        return BLOGS;
    }


    public void setTypeAndTag(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
    }


    @GetMapping("/blogs/create")
    public String create(Model model){
        model.addAttribute("blog",new Blog());
        setTypeAndTag(model);
        return CREATE;
    }

    @GetMapping("/blogs/{id}/create")
    public String edit(@PathVariable Long id, Model model){
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        setTypeAndTag(model);
        return CREATE;
    }




    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size=8,sort = "id",direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery bq,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable,bq));
        return LIST;
    }

    @PostMapping("/blogs")
    public String post(@Valid Blog blog, BindingResult result, HttpSession session, RedirectAttributes attributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        //blog.setTagList(tagService.listTag((String)session.getAttribute("tagIds")));
        blog.setTagList(tagService.listTag(blog.getTagIds()));
        Blog b;
        if(result.hasErrors()){
            return "/admin/blogRelease";
        }

        if(blog.getId()==null){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if(b==null){
            attributes.addFlashAttribute("message","创建失败");
        }else{
            attributes.addFlashAttribute("message","创建成功");
        }

        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }



}
