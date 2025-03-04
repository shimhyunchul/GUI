package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOExerUpDate {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final  String Update = "UPDATE record SET name = ?, part = ?, starttime = ?, endtime = ?, Tname = ? WHERE id = ? AND date = ?;";
    private final String LIST = "SELECT I.id, I.name, R.date, R.part, R.starttime,R. endtime, R.Tname FROM information I INNER JOIN Record R ON I.id = R.id ORDER BY R.date DESC;";

    private ManagementDAOExerUpDate() {}
    private static ManagementDAOExerUpDate instance = new ManagementDAOExerUpDate();

    public static ManagementDAOExerUpDate getInstance() {
        return instance;
    }

    public int updateMember(ManagementExer mdto) {

        conn = DataBase.getConnection();

        try {

            stmt = conn.prepareStatement(Update);


            stmt.setString(1, mdto.getName());
            stmt.setString(2, mdto.getPart());
            stmt.setString(3, mdto.getStarttime());
            stmt.setString(4, mdto.getEndtime());
            stmt.setString(5, mdto.getTname());
            stmt.setString(6, mdto.getId());
            stmt.setString(7, mdto.getDate());


            // 쿼리 실행
            int rowsUpdated = stmt.executeUpdate();  // UPDATE 문 실행
            if (rowsUpdated > 0) {
                return 1; // 성공
            } else {
                return -1; // id, name, date에 해당하는 레코드가 없거나 업데이트 실패
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // 예외 발생시 실패 반환
    }

    public List<ManagementExer> managementListExerUpdate() {
        conn = DataBase.getConnection();
        List<ManagementExer> list = new ArrayList<ManagementExer>();

        try {
            stmt = conn.prepareStatement(LIST);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ManagementExer mdto = new ManagementExer();
                mdto.setId(rs.getString("id"));
                mdto.setName(rs.getString("name"));
                mdto.setDate(rs.getString("date"));
                mdto.setPart(rs.getString("part"));
                mdto.setStarttime(rs.getString("starttime"));
                mdto.setEndtime(rs.getString("endtime"));
                mdto.setTname(rs.getString("tname"));
                list.add(mdto);
            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}