package com.pcce.mithya.mithya2017;

/**
 * Created by Aldrich on 28-Mar-17.
 */


import java.util.HashMap;
import java.util.Map;

public class Scores {

    private Integer COMP;
    private Integer ETC;
    private Integer IT;
    private Integer MECH;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Integer getCOMP() {
        return COMP;
    }

    public void setCOMP(Integer COMP) {
        this.COMP = COMP;
    }

    public Integer getETC() {
        return ETC;
    }

    public void setETC(Integer ETC) {
        this.ETC = ETC;
    }

    public Integer getIT() {
        return IT;
    }

    public void setIT(Integer IT) {
        this.IT = IT;
    }

    public Integer getMECH() {
        return MECH;
    }

    public void setMECH(Integer MECH) {
        this.MECH = MECH;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}