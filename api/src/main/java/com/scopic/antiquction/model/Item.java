package com.scopic.antiquction.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.scopic.antiquction.event.BidListener;

import lombok.Data;

@EntityListeners(BidListener.class)
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bid> bids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
