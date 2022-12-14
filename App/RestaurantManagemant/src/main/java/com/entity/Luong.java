package com.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
public class Luong {
    @EmbeddedId
    private LuongId luongId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maNhanVien")
    private NhanVien nhanVien;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maCaTruc")
    private CaTruc caTruc;
    
    @NonNull
    private Integer soNgayNghi;
    @NonNull
    private Float tongTien;    
}
