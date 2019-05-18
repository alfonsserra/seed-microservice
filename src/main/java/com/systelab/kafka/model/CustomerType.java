package com.systelab.kafka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement

@Entity
@Data
@NoArgsConstructor
public class CustomerType implements Serializable {

    @Id
    private String id;

    @Size(min = 1, max = 255)
    private String name;


}