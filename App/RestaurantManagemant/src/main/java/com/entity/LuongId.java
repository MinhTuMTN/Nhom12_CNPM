package com.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class LuongId implements Serializable{
    @Column(name = "maNhanVien")
    private int maNhanVien;
    
    @Column(name = "maCaTruc")
    private int maCaTruc;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.maNhanVien;
        hash = 67 * hash + this.maCaTruc;
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
        final LuongId other = (LuongId) obj;
        if (this.maNhanVien != other.maNhanVien) {
            return false;
        }
        return this.maCaTruc == other.maCaTruc;
    }
    
    
}
