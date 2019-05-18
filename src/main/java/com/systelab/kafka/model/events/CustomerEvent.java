package com.systelab.kafka.model.events;

import com.systelab.kafka.model.Customer;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerEvent extends Event<Customer> {
    public CustomerEvent(Action action, Customer customer) {
        super(action, customer);
    }
}
