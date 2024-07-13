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

    public static final String INSERT_FEATURE = """
            MERGE INTO t_ftre as t1
            USING (SELECT :featureName as a_ftre_nm) as t2
            ON t1.a_ftre_nm = t2.a_ftre_nm
            WHEN MATCHED THEN
            UPDATE SET
                a_ftre_nm = t1.a_ftre_nm
            WHEN NOT MATCHED THEN
            INSERT
            (
                a_ftre_id, a_ftre_nm
            )
            VALUES
            (
                :featureId, :featureName
            );
            """;

    public static final String INSERT_BED_TYPE = """
            MERGE INTO t_bd_typ as t1
            USING (SELECT :bedTypeName as a_bd_typ_nm) as t2
            ON t1.a_bd_typ_nm = t2.a_bd_typ_nm
            WHEN MATCHED THEN
            UPDATE SET
                a_bd_typ_nm = t1.a_bd_typ_nm
            WHEN NOT MATCHED THEN
            INSERT
            (
                a_bd_typ_id, a_bd_typ_nm
            )
            VALUES
            (
                :bedTypeId, :bedTypeName
            );
            """;

    public static final String INSERT_ROOM_CLASS_FEATURE = """
            INSERT INTO t_rm_cls_ftre (a_rm_cls_id, a_ftre_id)
            VALUES (:roomClassId, :featureId);
            """;

    public static final String INSERT_ROOM_CLASS_BED_TYPE = """
            INSERT INTO t_rm_cls_bd_typ (a_rm_typ_id, a_rm_cls_id, a_bd_typ_id, a_no_beds)
            VALUES (gen_random_uuid(), :roomClassId, :bedTypeId, :noOfBeds);
            """;

    public static final String FIND_FEATURE_BY_NAME = """
            SELECT * FROM t_ftre
            WHERE a_ftre_nm = :featureName;
            """;

    public static final String FIND_BED_TYPE_BY_NAME = """
            SELECT * FROM t_bd_typ
            WHERE a_bd_typ_nm = :bedTypeName;
            """;

    public static final String FIND_ROOM_BY_ID = """
            SELECT t1.a_rm_id, t1.a_flr_no, t1.a_st, t1.a_rm_no, t2.a_rm_cls_id, t2.a_cls_nm, t2.a_base_price
            FROM t_rm t1 INNER JOIN t_rm_cls t2 ON t1.a_rm_cls_id = t2.a_rm_cls_id
            WHERE t1.a_rm_id = :roomId;
            """;

    public static final String FIND_FEATURE_BY_ROOM_CLASS_ID = """
            SELECT t2.a_ftre_id, t2.a_ftre_nm FROM t_rm_cls_ftre t1
            INNER JOIN t_ftre t2 ON t1.a_ftre_id = t2.a_ftre_id WHERE t1.a_rm_cls_id = :roomClassId;
            """;

    public static final String FIND_BED_TYPE_BY_ROOM_CLASS_ID = """
            SELECT t2.a_bd_typ_nm, t1.a_no_beds FROM t_rm_cls_bd_typ t1
            INNER JOIN t_bd_typ t2 ON t1.a_bd_typ_id = t2.a_bd_typ_id WHERE t1.a_rm_cls_id = :roomClassId;
            """;
}
