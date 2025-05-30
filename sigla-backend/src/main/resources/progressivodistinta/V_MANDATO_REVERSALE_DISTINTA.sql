-- SIGLA.V_MANDATO_REVERSALE source

CREATE OR REPLACE FORCE EDITIONABLE VIEW "SIGLA"."V_MANDATO_REVERSALE" ("CD_TIPO_DOCUMENTO_CONT", "CD_CDS", "ESERCIZIO", "PG_DOCUMENTO_CONT", "CD_UNITA_ORGANIZZATIVA", "CD_CDS_ORIGINE", "CD_UO_ORIGINE", "TI_DOCUMENTO_CONT", "DS_DOCUMENTO_CONT", "STATO", "STATO_TRASMISSIONE", "DT_EMISSIONE", "DT_TRASMISSIONE", "DT_RITRASMISSIONE", "DT_PAGAMENTO_INCASSO", "DT_ANNULLAMENTO", "IM_DOCUMENTO_CONT", "IM_RITENUTE", "IM_PAGATO_INCASSATO", "TI_CC_BI", "CD_TERZO", "CD_TIPO_DOCUMENTO_CONT_PADRE", "PG_DOCUMENTO_CONT_PADRE", "TI_DOCUMENTO_CONT_PADRE", "PG_VER_REC", "VERSAMENTO_CORI", "DT_FIRMA", "TIPO_DEBITO_SIOPE", "ESITO_OPERAZIONE", "STATO_VAR_SOS", "SELEZIONE_TESORERIA", "PG_DISTINTA_TESORERIA") AS
SELECT
    a.cd_tipo_documento_cont, a.cd_cds, a.esercizio,
    a.pg_documento_cont, a.cd_unita_organizzativa, a.cd_cds_origine,
    a.cd_uo_origine, a.ti_documento_cont, a.ds_documento_cont, a.stato,
    a.stato_trasmissione, a.dt_emissione, a.dt_trasmissione,
    a.dt_ritrasmissione, a.dt_pagamento_incasso, a.dt_annullamento,
    a.im_documento_cont, a.im_ritenute, a.im_pagato_incassato, 'C',
    a.cd_terzo, a.cd_tipo_documento_cont_padre,
    a.pg_documento_cont_padre, a.ti_documento_cont_padre, a.pg_ver_rec,
    NULL, a.dt_firma, a.tipo_debito_siope,a.esito_operazione, a.stato_var_sos, a.SELEZIONE_TESORERIA, a.PG_DISTINTA_TESORERIA
FROM v_mandato_reversale_pre a
WHERE NOT EXISTS (
    SELECT 1
    FROM sospeso_det_etr b
    WHERE b.cd_cds_reversale = a.cd_cds
      AND b.esercizio = a.esercizio
      AND b.pg_reversale = a.pg_documento_cont
      AND b.stato = 'N'
      AND b.ti_entrata_spesa =
          DECODE (a.cd_tipo_documento_cont,
                  'REV', 'E',
                  'S'
          ))
  AND NOT EXISTS (
    SELECT 1
    FROM sospeso_det_usc b
    WHERE b.cd_cds_mandato = a.cd_cds
      AND b.esercizio = a.esercizio
      AND b.pg_mandato = a.pg_documento_cont
      AND b.stato = 'N'
      AND b.ti_entrata_spesa =
          DECODE (a.cd_tipo_documento_cont,
                  'REV', 'E',
                  'S'
          ))
UNION ALL
SELECT
    DISTINCT a.cd_tipo_documento_cont, a.cd_cds, a.esercizio,
             a.pg_documento_cont, a.cd_unita_organizzativa,
             a.cd_cds_origine, a.cd_uo_origine, a.ti_documento_cont,
             a.ds_documento_cont, a.stato, a.stato_trasmissione,
             a.dt_emissione, a.dt_trasmissione, a.dt_ritrasmissione,
             a.dt_pagamento_incasso, a.dt_annullamento,
             a.im_documento_cont, a.im_ritenute, a.im_pagato_incassato,
             d.ti_cc_bi, a.cd_terzo, a.cd_tipo_documento_cont_padre,
             a.pg_documento_cont_padre, a.ti_documento_cont_padre,
             a.pg_ver_rec, NULL, a.dt_firma, a.tipo_debito_siope,a.esito_operazione,a.stato_var_sos, a.SELEZIONE_TESORERIA, a.PG_DISTINTA_TESORERIA
FROM v_mandato_reversale_pre a, sospeso_det_etr b, sospeso d
WHERE a.cd_tipo_documento_cont = 'REV'
  AND b.cd_cds_reversale = a.cd_cds
  AND b.esercizio = a.esercizio
  AND b.pg_reversale = a.pg_documento_cont
  AND b.stato = 'N'
  AND d.esercizio = b.esercizio
  AND d.cd_cds = b.cd_cds
  AND d.ti_entrata_spesa = b.ti_entrata_spesa
  AND d.ti_sospeso_riscontro = b.ti_sospeso_riscontro
  AND d.cd_sospeso = b.cd_sospeso
UNION ALL
SELECT
    DISTINCT a.cd_tipo_documento_cont, a.cd_cds, a.esercizio,
             a.pg_documento_cont, a.cd_unita_organizzativa,
             a.cd_cds_origine, a.cd_uo_origine, a.ti_documento_cont,
             a.ds_documento_cont, a.stato, a.stato_trasmissione,
             a.dt_emissione, a.dt_trasmissione, a.dt_ritrasmissione,
             a.dt_pagamento_incasso, a.dt_annullamento,
             a.im_documento_cont, a.im_ritenute, a.im_pagato_incassato,
             'C', a.cd_terzo, a.cd_tipo_documento_cont_padre,
             a.pg_documento_cont_padre, a.ti_documento_cont_padre,
             a.pg_ver_rec, NULL, a.dt_firma, a.tipo_debito_siope,a.esito_operazione, a.stato_var_sos, a.SELEZIONE_TESORERIA, a.PG_DISTINTA_TESORERIA
FROM v_mandato_reversale_pre a, sospeso_det_usc b
WHERE a.cd_tipo_documento_cont = 'MAN'
  AND b.cd_cds_mandato = a.cd_cds
  AND b.esercizio = a.esercizio
  AND b.pg_mandato = a.pg_documento_cont
  AND b.stato = 'N';