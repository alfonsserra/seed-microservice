package com.systelab.kafka.model;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Action {
    @XmlEnumValue("CREATE") CREATE,
    @XmlEnumValue("UPDATE") UPDATE,
    @XmlEnumValue("DELETE") DELETE
}

