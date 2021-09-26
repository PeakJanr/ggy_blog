package com.peak.web.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.peak.pojo.Type;
import com.peak.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @PROJECT_NAME: ggy_Blog
 * @DESCRIPTION:
 * @USER: Peak_GGY
 * @DATE: 2021/9/6 15:58
 */
@Controller
@RequestMapping("/admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pagenum")int pagenum, Model model){
        PageHelper.startPage(pagenum, 5);
        List<Type> allType = typeService.getAllType();
        //得到分页结果对象
        PageInfo<Type> pageInfo = new PageInfo<>(allType);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    /**
     * 跳转到添加页面
     * @return
     */
    @GetMapping("/types/input")
    public String toAddType(Model model)
    {
        model.addAttribute("type",new Type());
        return "/admin/types-input";
    }

    @GetMapping("/types//input/{id}")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 添加类型信息
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types/insert")
    public String addType(Type type, RedirectAttributes attributes)
    {
        try {
            typeService.saveType(type);
            attributes.addFlashAttribute("msg","添加成功");
        }catch (Exception e)
        {
            attributes.addFlashAttribute("msg","添加失败");
            return "redirect:/admin/types/input";
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除用户信息
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/delete/{id}")
    public String deleteType(@PathVariable("id") Long id,RedirectAttributes attributes) {
        try {
            typeService.deleteType(id);
            attributes.addFlashAttribute("msg", "删除成功");
            return "redirect:/admin/types";
        } catch (Exception e) {
            attributes.addFlashAttribute("msg", "删除失败");
            return "redirect:/admin/types";
        }
    }

    /**
     * 修改类型
     */
    @PostMapping("/types/update/{id}")
    public String updateType(@PathVariable("id") Long id, Type type,RedirectAttributes attributes)
    {
            type.setId(id);
        try {
            typeService.updateType(type);
            attributes.addFlashAttribute("msg","更新成功");
            return "redirect:/admin/types";
        }catch (Exception e)
        {
            attributes.addFlashAttribute("msg","更新失败");
            return "redirect:/admin/types";
        }
    }



}
