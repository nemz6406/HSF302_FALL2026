package com.hsf302.DemoThymleaf.controller;

import com.hsf302.DemoThymleaf.model.SanPham;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequestMapping("/sanpham")
public class SanPhamController {

    // Lưu tạm trong bộ nhớ: dùng chung cho mọi request, mất khi restart app.
    // CopyOnWriteArrayList đảm bảo an toàn khi nhiều request cùng thêm dữ liệu.
    private final List<SanPham> danhSach = new CopyOnWriteArrayList<>();

    // GET – hiển thị form rỗng
    @GetMapping("/them")
    public String showForm(Model model) {
        model.addAttribute("sanPham", new SanPham());     // Tên "sanPham" khớp với th:object trong form
        return "sanpham/form";
    }

    // POST – nhận dữ liệu, lưu vào list, rồi REDIRECT sang trang kết quả
    @PostMapping("/them")
    public String xuLyForm(@ModelAttribute("sanPham") SanPham sanPham, RedirectAttributes ra) {
        danhSach.add(sanPham);
        ra.addFlashAttribute("thongBao", "Thêm sản phẩm thành công!");   // Sống qua 1 lần redirect
        return "redirect:/sanpham/ket-qua";
    }

    // GET – trang kết quả hiển thị danh sách
    @GetMapping("/ket-qua")
    public String ketQua(Model model) {
        model.addAttribute("danhSach", danhSach);
        return "sanpham/ket-qua";
    }
}