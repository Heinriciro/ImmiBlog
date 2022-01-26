package com.ianhein.immiblog2.web;

import com.ianhein.immiblog2.Service.BlogService;
import com.ianhein.immiblog2.Service.TagService;
import com.ianhein.immiblog2.Service.TypeService;
import com.ianhein.immiblog2.domain.Tag;
import com.ianhein.immiblog2.domain.Type;
import com.ianhein.immiblog2.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagIndexController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/tags/{id}")
    public String blogsByType(@PageableDefault(size=8,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                              @PathVariable Long id,
                              Model model){
        List<Tag> tags = tagService.listTag();
        if(id == -1){
            id = tags.get(0).getId();
        }

        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(pageable,id));
        model.addAttribute("activeTag",id);
        return "tags";
    }
}
