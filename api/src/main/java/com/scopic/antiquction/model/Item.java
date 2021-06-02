package com.scopic.antiquction.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 15)
    @Column(nullable = false)
    private String name;

    private String description;

    @NotNull
    private Integer startPrice;

    @NotNull
    private Date dateEnd;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AutoBid> autoBids;

    private Boolean awarded = false;

    public Bid getLastBid() {
        if(bids.size() == 0)
            return null;
        else
            return bids.get(bids.size() - 1);
    }
}
