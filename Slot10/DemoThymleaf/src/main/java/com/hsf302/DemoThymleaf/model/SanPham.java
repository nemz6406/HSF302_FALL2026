package com.hsf302.DemoThymleaf.model;

import jakarta.validation.constraints.*;

public class SanPham {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 3, max = 100, message = "Tên sản phẩm phải từ 3 đến 100 ký tự")
    private String ten;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private Double gia;

    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng tối thiểu phải là 1")
    private Integer soLuong;

    public SanPham() {}

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public Double getGia() { return gia; }
    public void setGia(Double gia) { this.gia = gia; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
}