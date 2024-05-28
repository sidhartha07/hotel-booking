package com.sidh.hotelbooking.util;

public class RoomQueries {

    private RoomQueries() {

    }

    public static final String INSERT_ROOM = """
            MERGE INTO t_rm as t1
            USING (SELECT :roomNo as a_rm_no, :floorNo as a_flr_no) as t2
            ON t1.a_rm_no = t2.a_rm_no AND t1.a_flr_no = t2.a_flr_no
            WHEN MATCHED THEN
            UPDATE SET
                a_rm_cls_id = :roomClassId,
                a_st = :status
            WHEN NOT MATCHED THEN
            INSERT
            (
                a_rm_id, a_rm_cls_id, a_flr_no, a_st, a_rm_no
            )
            VALUES
            (
                gen_random_uuid(), :roomClassId, :floorNo, :status, :roomNo
            );
            """;

    public static final String INSERT_ROOM_CLASS = """
            MERGE INTO t_rm_cls as t1
            USING (SELECT :roomClassName as a_cls_nm, :basePrice as a_base_price) as t2
            ON t1.a_cls_nm = t2.a_cls_nm
            WHEN MATCHED THEN
            UPDATE SET
                a_base_price = t2.a_base_price
            WHEN NOT MATCHED THEN
            INSERT
            (
                a_rm_cls_id, a_cls_nm, a_base_price
            )
            VALUES
            (
                gen_random_uuid(), :roomClassName, :basePrice
            );
            """;

    public static final String FIND_ROOM_CLASS_BY_ID = """
            SELECT * FROM t_rm_cls
            WHERE a_rm_cls_id = :roomClassId;
            """;

    public static final String FIND_ROOM_CLASS_BY_CLASS_NAME = """
            SELECT * FROM t_rm_cls
            WHERE a_cls_nm = :roomClassName;
            """;
}
