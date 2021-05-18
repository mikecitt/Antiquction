package com.scopic.antiquction.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Item extends Model {
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
