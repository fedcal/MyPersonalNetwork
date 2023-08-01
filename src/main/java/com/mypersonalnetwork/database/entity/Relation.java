package com.mypersonalnetwork.database.entity;


public class Relation {
    private Integer idRelation;
    private Integer idPerson1;
    private Integer idPerson2;
    private Integer idRelationType;

    public Relation(Integer idRelation, Integer idPerson1, Integer idPerson2, Integer idRelationType) {
        this.idRelation = idRelation;
        this.idPerson1 = idPerson1;
        this.idPerson2 = idPerson2;
        this.idRelationType = idRelationType;
    }

    public Integer getIdRelation() {
        return idRelation;
    }

    public void setIdRelation(Integer idRelation) {
        this.idRelation = idRelation;
    }

    public Integer getIdPerson1() {
        return idPerson1;
    }

    public void setIdPerson1(Integer idPerson1) {
        this.idPerson1 = idPerson1;
    }

    public Integer getIdPerson2() {
        return idPerson2;
    }

    public void setIdPerson2(Integer idPerson2) {
        this.idPerson2 = idPerson2;
    }

    public Integer getIdRelationType() {
        return idRelationType;
    }

    public void setIdRelationType(Integer idRelationType) {
        this.idRelationType = idRelationType;
    }
}
