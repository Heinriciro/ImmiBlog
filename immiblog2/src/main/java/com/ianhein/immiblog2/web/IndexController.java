package com.ianhein.immiblog2.web;


import com.ianhein.immiblog2.Service.BlogService;
import com.ianhein.immiblog2.Service.CommentService;
import com.ianhein.immiblog2.Service.TagService;
import com.ianhein.immiblog2.Service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String index(@PageableDefault(size=8,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listType(6));
        model.addAttribute("tags",tagService.listTag(10));
        model.addAttribute("blogs",blogService.listBlog(8));
        System.out.println("------------index------------");
        return "index";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,
                       Model model){
        model.addAttribute("blog",blogService.getAndRender(id));
        model.addAttribute("comments",commentService.listCommentsByBlogId(id));
        return "blog";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query,
                         @PageableDefault(size=8,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                         Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);

        return "search";
    }

    @GetMapping("/search/{query}")
    public String searchPageExchange(@PathVariable String query,
                                     @PageableDefault (size=8,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                                     Model model){
        model.addAttribute("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";

    }

    @GetMapping("/about")
    public String aboutMe(){
        return "info";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newblogs",blogService.listBlog(3));
        return "_fragments :: newestBlogList";
    }



}
