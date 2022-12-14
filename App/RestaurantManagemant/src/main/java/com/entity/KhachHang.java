package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.agent.builder.AgentBuilder;
import org.hibernate.annotations.Nationalized;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class KhachHang implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long maKhachHang;

    @Nationalized
    @Column(length = 300)
    @NonNull
    private String hoTen;

    @Column(length = 10)
    @NonNull
    private String soDienThoai;
    
    @NonNull
    private Date ngaySinh;

    @Nationalized
    @Column(length = 5)
    @NonNull
    private Boolean gioiTinh;
    
    @OneToMany(mappedBy = "khachHang")
    private List<DatTruoc> danhSachDatTruoc = new ArrayList<DatTruoc>();
    
    @OneToMany(mappedBy = "khachHang")
    private List<DonHang> donHang = new ArrayList<DonHang>();
}
