package org.metaz.test.toxgene;

import toxgene.interfaces.ToXgeneCdataDescriptor;

public class DutchWordsCdataDescriptor extends ToXgeneCdataDescriptor {
    public DutchWordsCdataDescriptor() {
        super();
        this.cdataClass  = "org.metaz.test.toxgene.Tekst";
        this.cdataName = "tekst";
        this.minLength = 1;
        this.maxLength = 200;
    }
}
