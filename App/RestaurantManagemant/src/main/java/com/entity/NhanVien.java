package com.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maNhanVien;

    @Nationalized
    @Column(length = 300)
    @NonNull
    private String hoTen;
    
    @NonNull
    private Date ngaySinh;
    
    @Nationalized
    @Column(length = 5)
    @NonNull
    private Boolean gioiTinh;
    
    @Nationalized
    @NonNull
    private String diaChi;
    
    @Column(length = 10)
    @NonNull
    private String soDienThoai;
    
    @NonNull
    private Float heSoLuong;
    
    @Nationalized
    @Column(length = 20)
    @NonNull
    private String loaiNhanVien;

    @OneToOne(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVienTiepNhan")
    private List<DatTruoc> danhSachDateDatTruoc = new ArrayList<DatTruoc>();
    
    @ManyToMany(mappedBy = "nhanVien")
    private List<CaTruc> caTruc = new ArrayList<CaTruc>();
    
    @OneToMany(mappedBy = "nhanVien")
    private List<Luong> luong = new ArrayList<Luong>();
    
    @OneToMany(mappedBy = "dauBep")
    private List<DonHang> donHangChuanBi = new ArrayList<DonHang>();
    
    @OneToMany(mappedBy = "phucVu")
    private List<DonHang> donHangPhucVu = new ArrayList<DonHang>();
    
    @OneToMany(mappedBy = "thuNgan")
    private List<DonHang> donHangTiepNhan = new ArrayList<DonHang>();
}
