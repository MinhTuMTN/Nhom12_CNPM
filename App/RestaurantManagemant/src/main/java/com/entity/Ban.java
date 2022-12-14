package com.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Ban {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maBan;
    
    @Nationalized
    @Column(length = 50)
    @NonNull
    private String trangThaiBan;
    
    @Nationalized
    @Column(length = 20)
    @NonNull
    private String loaiBan;
    
    @NonNull
    private Integer soLuongGheToiDa;
    
    @OneToMany(mappedBy = "ban")
    private List<DatTruoc> danhSachDatTruoc = new ArrayList<DatTruoc>();
    
    @OneToMany(mappedBy = "ban")
    private List<DonHang> donHang = new ArrayList<DonHang>();

    @Override
    public String toString() {
        return "Bàn " + this.getLoaiBan() + " " + String.valueOf(this.getMaBan());
    }
    
    
}
