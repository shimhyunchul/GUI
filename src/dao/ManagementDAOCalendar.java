package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOCalendar {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;


    private ManagementDAOCalendar() {
    }

    private static ManagementDAOCalendar instance = new ManagementDAOCalendar();

    public static ManagementDAOCalendar getInstance() {
        return instance;
    }


    // 전체 회원 리스트 조회 메서드
    public List<ManagementExer> managementListExerByDate(String date) {
        List<ManagementExer> list = new ArrayList<>();

        // DB에서 해당 날짜에 맞는 운동 기록을 가져오는 로직
        String query = "SELECT * FROM Record WHERE date = ?"; // 'Record_table'을 'Record'로 수정

        try (Connection conn = DataBase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ManagementExer exer = new ManagementExer();
                exer.setId(rs.getString("id"));
                exer.setName(rs.getString("name"));
                exer.setDate(rs.getString("date"));
                exer.setPart(rs.getString("part"));
                exer.setStarttime(rs.getString("starttime"));
                exer.setEndtime(rs.getString("endtime"));
                exer.setTname(rs.getString("tname"));
                list.add(exer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}