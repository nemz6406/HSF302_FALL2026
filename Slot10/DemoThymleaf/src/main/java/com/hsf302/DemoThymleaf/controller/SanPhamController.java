package com.hsf302.DemoThymleaf.controller;

import com.hsf302.DemoThymleaf.model.SanPham;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("/sanpham")
public class SanPhamController {

    private final List<SanPham> danhSach = new CopyOnWriteArrayList<>();

    @GetMapping("/them")
    public String showForm(Model model) {
        model.addAttribute("sanPham", new SanPham());
        return "sanpham/form";
    }

    @PostMapping("/them")
    public String xuLyForm(@Valid @ModelAttribute("sanPham") SanPham sanPham,
                           BindingResult bindingResult,
                           RedirectAttributes ra) {
        // Nếu có lỗi validation, trả về lại form nhập liệu
        if (bindingResult.hasErrors()) {
            return "sanpham/form";
        }

        danhSach.add(sanPham);
        ra.addFlashAttribute("thongBao", "Thêm sản phẩm thành công!");
        return "redirect:/sanpham/ket-qua";
    }

    @GetMapping("/ket-qua")
    public String ketQua(Model model) {
        model.addAttribute("danhSach", danhSach);
        return "sanpham/ket-qua";
    }
}