package com.scopic.antiquction.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Bid extends Model {
    @NotNull
    @OneToOne(targetEntity = User.class)
    private User user;
    @NotNull
    private Integer bidPrice;
}
