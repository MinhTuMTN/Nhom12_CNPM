package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class CaTruc implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int maCaTruc;
    
    @NonNull
    private Date ngayBatDau;
    @NonNull
    private Date ngayKetThuc;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "DangKyCaTruc",
                joinColumns = {@JoinColumn(name = "maCaTruc")},
                inverseJoinColumns = {@JoinColumn(name = "maNhanVien")})
    private Set<NhanVien> nhanVien = new HashSet<NhanVien>();

    @OneToMany(mappedBy = "caTruc", fetch = FetchType.EAGER)
    private List<Luong> luong = new ArrayList<Luong>();
}
