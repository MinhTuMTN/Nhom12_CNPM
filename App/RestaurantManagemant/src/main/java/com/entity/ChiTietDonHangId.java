package com.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ChiTietDonHangId implements Serializable{
    @Column(name = "maDonHang")
    private int maDonHang;
    
    @Column(name = "maMonAn")
    private int maMonAn;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.maDonHang;
        hash = 89 * hash + this.maMonAn;
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
        final ChiTietDonHangId other = (ChiTietDonHangId) obj;
        if (this.maDonHang != other.maDonHang) {
            return false;
        }
        return this.maMonAn == other.maMonAn;
    }    
    
}
