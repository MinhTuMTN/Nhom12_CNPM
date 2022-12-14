package com.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class DatTruoc implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maDatTruoc;
    
    @NonNull
    private Date thoiGianDatTruoc;
    
    @NonNull
    private Date thoiGianCheckIn;
    
    @NonNull
    private Integer soLuongNguoi;

    @Nationalized    
    @NonNull
    private String trangThaiDatTruoc;

    @ManyToOne(fetch = FetchType.EAGER)    
    @NonNull
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.EAGER)    
    @NonNull
    private Ban ban;

    @ManyToOne(fetch = FetchType.EAGER)
    private NhanVien nhanVienTiepNhan;

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DatTruoc other = (DatTruoc) obj;
        return Objects.equals(this.maDatTruoc, other.maDatTruoc);
    }

}
