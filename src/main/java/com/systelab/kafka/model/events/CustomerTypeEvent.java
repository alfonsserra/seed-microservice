package com.systelab.kafka.model.events;

import com.systelab.kafka.model.CustomerType;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerTypeEvent extends Event<CustomerType> {
}
