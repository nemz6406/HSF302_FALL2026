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
    // GET – xem chi tiết sản phẩm theo vị trí (index) trong list
    @GetMapping("/chi-tiet")
    public String chiTietSanPham(@RequestParam("id") int id, Model model) {
        if (id >= 0 && id < danhSach.size()) {
            model.addAttribute("sanPham", danhSach.get(id));
            return "sanpham/chi-tiet";
        }
        return "redirect:/sanpham/ket-qua";
    }
    // GET – xóa sản phẩm theo vị trí (index) trong list
    @GetMapping("/xoa")
    public String xoaSanPham(@RequestParam("id") int id, RedirectAttributes ra) {
        if (id >= 0 && id < danhSach.size()) {
            danhSach.remove(id);
            ra.addFlashAttribute("thongBao", "Xóa sản phẩm thành công!");
        } else {
            ra.addFlashAttribute("thongBao", "Không tìm thấy sản phẩm để xóa!");
        }
        return "redirect:/sanpham/ket-qua";
    }
    // GET – hiển thị form sửa sản phẩm theo vị trí (index)
    @GetMapping("/sua")
    public String hienThiFormSua(@RequestParam("id") int id, Model model) {
        if (id >= 0 && id < danhSach.size()) {
            model.addAttribute("sanPham", danhSach.get(id));
            model.addAttribute("index", id); // Truyền thêm index để biết đang sửa phần tử nào ở bước POST
            return "sanpham/form-sua";
        }
        return "redirect:/sanpham/ket-qua";
    }
}