package com.ianhein.immiblog2.web.admin;

import com.ianhein.immiblog2.Service.TypeService;
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

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {

        model.addAttribute("page",typeService.listType(pageable));
        //typeService.listType(pageable);
        return "admin/typeManage";
    }

    @GetMapping("/types/create")
    public String create(Model model){
        model.addAttribute("type",new Type());
        return "admin/typeCreate";
    }

    @GetMapping("/types/{id}/create")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/typeCreate";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            result.rejectValue("name","DuplicatedError","该分类已创建,不支持重复创建");
        }

        if(result.hasErrors()){
            return "admin/typeCreate";
        }

        Type t = typeService.saveType(type);
        if (t==null){
            attributes.addFlashAttribute("message","创建失败");
        }else{
            attributes.addFlashAttribute("message","创建成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,
                           BindingResult result,
                           @PathVariable Long id,
                           RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        if(type1 != null) {
            result.rejectValue("name","DuplicatedError","该分类已创建,不支持重复创建");
        }

        if(result.hasErrors()){
            return "admin/typeCreate";
        }

        Type t = typeService.updateType( id,type);
        if (t==null){
            attributes.addFlashAttribute("message","修改失败");
        }else{
            attributes.addFlashAttribute("message","修改成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

}
