package org.example.bootsecurity.controller;

import org.example.bootsecurity.model.domain.Memo;
import org.example.bootsecurity.model.domain.MemoForm;
import org.example.bootsecurity.model.mapper.MemoMapper;
import org.example.bootsecurity.service.MemoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class MainController {
    private final MemoService memoService;

    public MainController(MemoMapper memoMapper, MemoService memoService) {
        this.memoService = memoService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("memoList", memoService.findAll());
//        model.addAttribute("memoForm", new MemoForm());
        return "index";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("memoForm", new MemoForm());
        return "add";
    }

    @PostMapping("/add")
    public String save(MemoForm form) throws Exception {
//        Memo memo = new Memo(0L, form.getText(), "");
        Memo memo = Memo.fromText(form.getText());
        memoService.create(memo);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) throws Exception {
        String msg = "%d를 정상적으로 삭제하였습니다.".formatted(id);
        redirectAttributes.addFlashAttribute("msg", msg);
        memoService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/delete-all")
    public String deleteAll(RedirectAttributes redirectAttributes) throws Exception {
        memoService.deleteAll();
        // addAttribute -> 주소창으로 전달해서 controller가 한 번 더 받아줘야함
        redirectAttributes.addFlashAttribute("msg", "전체 삭제");
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        return "update";
    }

}