package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maCoupon;
    
    @NonNull
    private Date ngayBatDau;
    
    @NonNull
    private Date ngayKetThuc;
    
    @NonNull
    private Float phanTramGiam;
    
    @NonNull
    private Float giamToiDa;
    
    @NonNull
    private Float donToiThieu;
    
    @OneToMany(mappedBy = "coupon")
    private List<DonHang> donHang = new ArrayList<DonHang>();    
}
