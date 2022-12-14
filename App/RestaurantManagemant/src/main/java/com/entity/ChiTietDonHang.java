package com.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChiTietDonHang implements Serializable{
    @EmbeddedId
    private ChiTietDonHangId chiTietDonHangId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maDonHang")
    @NonNull
    private DonHang donHang;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("maMonAn")
    @NonNull
    private MonAn monAn;
    
    private int soLuong;
}
