package org.example.laboratoryappointment.mapper;

import org.example.laboratoryappointment.dto.CourseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDTORowMapper implements RowMapper<CourseDTO> {
    @Override
    public CourseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CourseDTO.builder()
                .id(rs.getString("c.id"))
                .name(rs.getString("c.name"))
                .credit(rs.getFloat("c.credit"))
                .term(rs.getInt("c.term"))
                .year(rs.getString("c.year"))
                .lesson(rs.getInt("c.lesson"))
                .classNumber(rs.getString("c.class_number"))
                .experimentalHours(rs.getInt("c.experimental_hours"))
                .profession(rs.getString("c.profession"))
                .quantity(rs.getInt("c.quantity"))
                .theoreticalHours(rs.getInt("c.theoretical_hours"))
                .teacherId(rs.getString("c.teacher_id"))
                .teacherName(rs.getString("u.name"))
                .build();
    }
}
