package com.systelab.customer.model.events;

import com.systelab.customer.model.CustomerType;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerTypeEvent extends Event<CustomerType> {
}
