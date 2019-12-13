package com.systelab.seed.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Center implements Serializable {

    @Id
    private String id;

    @Size(min = 1, max = 255)
    private String name;


}