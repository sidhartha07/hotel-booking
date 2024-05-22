-- DROP DATABASE IF EXISTS "htl-bkg";

-- CREATE DATABASE "htl-bkg" WITH ENCODING = 'UTF8';

CREATE SCHEMA htl_bkg;

DROP TABLE IF EXISTS htl_bkg.t_bkg_rm;
DROP TABLE IF EXISTS htl_bkg.t_rm;
DROP TABLE IF EXISTS htl_bkg.t_rm_cls_bd_typ;
DROP TABLE IF EXISTS htl_bkg.t_rm_cls_ftre;
DROP TABLE IF EXISTS htl_bkg.t_ftre;
DROP TABLE IF EXISTS htl_bkg.t_rm_cls;
DROP TABLE IF EXISTS htl_bkg.t_bd_typ;
DROP TABLE IF EXISTS htl_bkg.t_bkg_addon;
DROP TABLE IF EXISTS htl_bkg.t_addon;
DROP TABLE IF EXISTS htl_bkg.t_bkg;
DROP TABLE IF EXISTS htl_bkg.t_cust;

CREATE TABLE htl_bkg.t_cust (
	a_cust_id VARCHAR(37) NOT NULL,
	a_fst_nm VARCHAR(50) NOT NULL,
	a_lst_nm VARCHAR(50) NULL,
	a_em VARCHAR(255) NOT NULL,
	a_pwd VARCHAR(255) NOT NULL,
	a_phn VARCHAR(20) NOT NULL,
	a_cr_dtm TIMESTAMP NULL DEFAULT timezone('UTC', now()),
	a_upd_dtm TIMESTAMP NULL DEFAULT timezone('UTC', now()),
	CONSTRAINT PKCust PRIMARY KEY (a_cust_id),
	CONSTRAINT UKCustEm UNIQUE (a_em),
	CONSTRAINT UKCustPhn UNIQUE (a_phn)
);

CREATE TABLE htl_bkg.t_bkg (
	a_bkg_id VARCHAR(37) NOT NULL,
	a_cust_id VARCHAR(37) NOT NULL,
	a_pymnt_st VARCHAR(10) NOT NULL,
	a_chk_in_dt DATE NOT NULL,
	a_chk_out_dt DATE NOT NULL,
	a_no_adlts NUMERIC(20,0) NOT NULL,
	a_no_chldrn NUMERIC(20,0) NOT NULL,
	a_bkg_amnt NUMERIC(20,2) NOT NULL,
	a_cr_dtm TIMESTAMP NULL DEFAULT timezone('UTC', now()),
	a_upd_dtm TIMESTAMP NULL DEFAULT timezone('UTC', now()),
	a_is_deleted VARCHAR(1) NULL DEFAULT 'N',
	CONSTRAINT PKBkg PRIMARY KEY (a_bkg_id)
);

CREATE TABLE htl_bkg.t_addon (
	a_addon_id VARCHAR(37) NOT NULL,
	a_addon_nm VARCHAR(100) NOT NULL,
	a_price NUMERIC(20,2) NOT NULL,
	CONSTRAINT PKAddon PRIMARY KEY (a_addon_id)
);

CREATE TABLE htl_bkg.t_bkg_addon (
	a_bkg_id VARCHAR(37) NOT NULL,
	a_addon_id VARCHAR(37) NOT NULL,
	CONSTRAINT PKBkgAddon PRIMARY KEY (a_bkg_id, a_addon_id)
);

CREATE TABLE htl_bkg.t_bd_typ (
	a_bd_typ_id VARCHAR(37) NOT NULL,
	a_bd_typ_nm VARCHAR(50) NOT NULL,
	CONSTRAINT PKBdTyp PRIMARY KEY (a_bd_typ_id)
);

CREATE TABLE htl_bkg.t_rm_cls (
	a_rm_cls_id VARCHAR(37) NOT NULL,
	a_cls_nm VARCHAR(100) NOT NULL,
	a_base_price NUMERIC(20,2) NOT NULL,
	CONSTRAINT PKRmCls PRIMARY KEY (a_rm_cls_id)
);

CREATE TABLE htl_bkg.t_rm_cls_bd_typ (
	a_rm_typ_id VARCHAR(37) NOT NULL,
	a_rm_cls_id VARCHAR(37) NOT NULL,
	a_bd_typ_id VARCHAR(37) NOT NULL,
	a_no_beds NUMERIC(20,0) NOT NULL,
	CONSTRAINT PKRmClsBdTyp PRIMARY KEY (a_rm_typ_id),
	CONSTRAINT UKRmClsBdTyp UNIQUE (a_rm_cls_id, a_bd_typ_id)
);

CREATE TABLE htl_bkg.t_ftre (
	a_ftre_id VARCHAR(37) NOT NULL,
	a_ftre_nm VARCHAR(100) NOT NULL,
	CONSTRAINT PKFtre PRIMARY KEY (a_ftre_id)
);

CREATE TABLE htl_bkg.t_rm_cls_ftre (
	a_rm_cls_id VARCHAR(37) NOT NULL,
	a_ftre_id VARCHAR(37) NOT NULL,
	CONSTRAINT PKRmClsFtre PRIMARY KEY (a_rm_cls_id, a_ftre_id)
);

CREATE TABLE htl_bkg.t_rm (
	a_rm_id VARCHAR(37) NOT NULL,
	a_flr_no VARCHAR(10) NOT NULL,
	a_rm_cls_id VARCHAR(37) NOT NULL,
	a_st VARCHAR(15) NOT NULL,
	a_rm_no VARCHAR(10) NOT NULL,
	CONSTRAINT PKRm PRIMARY KEY (a_rm_id)
);

CREATE TABLE htl_bkg.t_bkg_rm (
	a_bkg_id VARCHAR(37) NOT NULL,
	a_rm_id VARCHAR(37) NOT NULL,
	CONSTRAINT PKBkgRm PRIMARY KEY (a_bkg_id, a_rm_id)
);

