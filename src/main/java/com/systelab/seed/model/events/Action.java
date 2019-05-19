package com.systelab.seed.model.events;

import javax.xml.bind.annotation.XmlEnumValue;

public enum Action {
    @XmlEnumValue("CREATE") CREATE,
    @XmlEnumValue("UPDATE") UPDATE,
    @XmlEnumValue("DELETE") DELETE
}

