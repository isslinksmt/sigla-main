package it.cnr.contab.web.rest.model;

import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.util.ApplicationMessageFormatException;
import it.cnr.jada.DetailedRuntimeException;

public enum EnumAssMandRevDocGen {
    ASSOCIATO_A_MAND_REV(Documento_genericoBulk.ASSOCIATO_A_MANDATO),
    NO_ASSOCIATO_A_MAND_REV(Documento_genericoBulk.NON_ASSOCIATO_A_MANDATO),
    PARZIALMENTE_ASSOCIATO_A_MANDATO(Documento_genericoBulk.PARZIALMENTE_ASSOCIATO_A_MANDATO);

    String assMandRev;

    public String getAssMandRev() {
        return assMandRev;
    }

    EnumAssMandRevDocGen(String assMandRev) {
        this.assMandRev = assMandRev;
    }
    public static EnumAssMandRevDocGen getValueFrom(String assMandRev) {
        for (EnumAssMandRevDocGen enumAssMandRevDocGen : EnumAssMandRevDocGen.values()) {
            if (enumAssMandRevDocGen.assMandRev.equals(assMandRev))
                return enumAssMandRevDocGen;
        }
        throw new DetailedRuntimeException(
                new ApplicationMessageFormatException("Assciazione Mandato Reversale non trovata {0}!", assMandRev)
        );
    }

}
