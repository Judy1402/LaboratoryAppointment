package org.example.laboratoryappointment.mapper;

import lombok.extern.slf4j.Slf4j;
import org.example.laboratoryappointment.dto.AppointmentDTO;
import org.example.laboratoryappointment.tool.Temp;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class AppointmentDTORowMapper implements RowMapper<AppointmentDTO> {
    @Override
    public AppointmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return AppointmentDTO.builder()
                .appointmentId(rs.getString("a.id"))
                .teacherId(Temp.toObject(rs.getString("a.teacher")).getId())
                .classroomId(Temp.toObject(rs.getString("a.classroom")).getId())
                .courseId(Temp.toObject(rs.getString("a.course")).getId())
                .teacherName(Temp.toObject(rs.getString("a.teacher")).getName())
                .classroomName(Temp.toObject(rs.getString("a.classroom")).getName())
                .courseName(Temp.toObject(rs.getString("a.course")).getName())
                .year(rs.getString("c.year"))
                .classNumber(rs.getString("c.class_number"))
                .profession(rs.getString("c.profession"))
                .week(rs.getInt("a.week"))
                .dayofweek(rs.getInt("a.dayofweek"))
                .section(rs.getInt("a.section"))
                .build();
    }
}
