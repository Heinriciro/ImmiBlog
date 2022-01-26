package com.ianhein.immiblog2.web;

import com.ianhein.immiblog2.Service.BlogService;
import com.ianhein.immiblog2.Service.TypeService;
import com.ianhein.immiblog2.domain.Blog;
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
public class TypeIndexController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;


    @GetMapping("/types/{id}")
    public String blogsByType(@PageableDefault(size=8,sort = "updateTime",direction = Sort.Direction.DESC) Pageable pageable,
                              @PathVariable Long id,
                              Model model){
        List<Type> types = typeService.listType();
        if(id == -1){
            id = types.get(0).getId();
        }

        model.addAttribute("types",types);

        BlogQuery bq = new BlogQuery();
        bq.setTypeId(id);
        model.addAttribute("page",blogService.listBlog(pageable,bq));
        model.addAttribute("activeType",id);
        return "classification";
    }

}
