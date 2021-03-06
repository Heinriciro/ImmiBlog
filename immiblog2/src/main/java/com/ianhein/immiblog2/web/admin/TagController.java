package com.ianhein.immiblog2.web.admin;


import com.ianhein.immiblog2.Service.TagService;
import com.ianhein.immiblog2.domain.Tag;
import com.ianhein.immiblog2.domain.Type;
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

import javax.jws.WebParam;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                   Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "/admin/tagManage";
    }

    @GetMapping("/tags/create")
    public String create(Model model){
        model.addAttribute("tag",new Tag());
        return "/admin/tagCreate";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","DuplicatedError","该标签已创建,不支持重复创建");
        }
        if(result.hasErrors()){
            return "/admin/tagCreate";
        }

        Tag t = tagService.saveTag(tag);
        if(t==null){
            attributes.addFlashAttribute("message","创建失败");
        }else{
            attributes.addFlashAttribute("message","创建成功");
        }

        return "redirect:/admin/tags";

    }

    @GetMapping("/tags/{id}/create")
    public String edit(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "/admin/tagCreate";

    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,
                           BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes
                           ){

        Tag tag1 = tagService.getTagByName(tag.getName());
        if(tag1!=null){
            result.rejectValue("name","DuplicatedError","该标签已创建,不支持重复创建");
        }
        if(result.hasErrors()){
            return "/admin/tagCreate";
        }

        Tag t = tagService.updateTag(id,tag);
        if(t==null){
            attributes.addFlashAttribute("message","修改失败");
        }else{
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
