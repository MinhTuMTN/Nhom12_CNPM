package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@NamedQuery(name = "MonAn.findAll", query = "SELECT m FROM MonAn m")
public class MonAn implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maMonAn;

    @NonNull
    @Nationalized
    private String tenMonAn;

    @NonNull
    private Float giaTien;

    @NonNull
    private String hinhAnh;

    @OneToMany(mappedBy = "donHang")
    private List<ChiTietDonHang> chiTietDonHang = new ArrayList<ChiTietDonHang>();

    @Override
    public String toString() {
        return this.getTenMonAn();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MonAn monAn = (MonAn) o;
        return maMonAn == monAn.maMonAn;
    }

}
