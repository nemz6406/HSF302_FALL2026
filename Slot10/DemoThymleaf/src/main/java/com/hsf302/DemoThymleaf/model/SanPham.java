package com.hsf302.DemoThymleaf.model;

public class SanPham {
    private String ten;
    private Double gia;          // Kiểu wrapper: ô trống → null (nếu dùng double/int nguyên thủy sẽ bị lỗi 400)
    private Integer soLuong;

    public SanPham() {}          // BẮT BUỘC: Spring cần constructor rỗng để khởi tạo object rồi gán giá trị từng field

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public Double getGia() { return gia; }
    public void setGia(Double gia) { this.gia = gia; }

    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
}