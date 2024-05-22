package com.sidh.hotelbooking.util;

public class CustomerQueries {

    private CustomerQueries() {

    }

    public static final String INSERT_CUSTOMER = """
            INSERT INTO t_cust (a_cust_id, a_fst_nm, a_lst_nm, a_em, a_pwd, a_phn, a_role, a_cr_dtm, a_upd_dtm)
            VALUES (gen_random_uuid(), :firstName, :lastName, :email, :password, :phone, :role, :createdDateTime, :updatedDateTime);
            """;

    public static final String FIND_BY_EMAIL = """
            SELECT a_cust_id, a_fst_nm, a_lst_nm, a_em, a_pwd, a_phn, a_role
            FROM t_cust WHERE a_em = :email;
            """;
}
