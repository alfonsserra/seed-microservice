package com.systelab.kafka.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerEvent extends Event<Customer> {
}
