package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class DonHang implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maDonHang;
    
    @NonNull
    private Date thoiGianCheckIn;
    
    @NonNull
    private Float phuThu;
    
    @NonNull
    private Float soTienThanhToan;
    
    @NonNull
    @Nationalized
    private String trangThaiDonHang;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    private Ban ban;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    private KhachHang khachHang;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    private NhanVien dauBep;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @NonNull
    private NhanVien phucVu;
     
    @ManyToOne(fetch = FetchType.EAGER)
    private NhanVien thuNgan;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private Coupon coupon;
    
    @OneToMany(mappedBy = "donHang", fetch = FetchType.EAGER)
    private List<ChiTietDonHang> chiTietDonHang = new ArrayList<ChiTietDonHang>();
}
