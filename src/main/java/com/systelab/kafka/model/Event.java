package com.systelab.kafka.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Event<T> {
    private Action action;
    private T payload;
}
