<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd">
<sqlMap namespace="${entitySimple}">

    <insert id="insert">
        INSERT INTO ${entitySimple}
        (
        AddTime,
    <#list entityFields as field>
        ${field.name}<#if field_has_next>,</#if>
    </#list>
        )
        VALUES
        (
        now(),
    <#list entityFields as field>
        #e.${field.name}#<#if field_has_next>,</#if>
    </#list>
        );
        <selectKey resultClass="java.lang.Integer" keyProperty="id">
            SELECT LAST_INSERT_ID()
        </selectKey>

    </insert>

    <delete id="delete">
        DELETE FROM ${entitySimple}
        WHERE Id = #id#;
    </delete>


    <select id="get" resultClass="${entity}">
        SELECT * FROM ${entitySimple}
        WHERE Id = #id#;
    </select>

    <update id="update">
        UPDATE  ${entitySimple}
        SET
    <#list entityFields as field>
        ${field.name} = #e.${field.name}#<#if field_has_next>,</#if>
    </#list>
        WHERE Id = #id#;
    </update>

</sqlMap>