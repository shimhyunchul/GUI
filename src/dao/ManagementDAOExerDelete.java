package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOExerDelete {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final String DELETE = "DELETE FROM record WHERE id = ? AND date = ?";  // id를 사용해서 삭제
    private final String LIST = "SELECT I.id, I.name, R.date, R.part, R.starttime,R. endtime, R.Tname FROM information I INNER JOIN Record R ON I.id = R.id ORDER BY R.date DESC;";
    // 레코드에서 Tname을 인어조인을 해서 tran
    private ManagementDAOExerDelete() {}
    private static ManagementDAOExerDelete instance = new ManagementDAOExerDelete();

    public static ManagementDAOExerDelete getInstance() {
        return instance;
    }


    // 회원 운동 정보 삭제 메서드 (id로 삭제)
    public int deleteMember(String id, String date) {

        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(DELETE);  // DELETE 쿼리 준비

            stmt.setString(1, id);  // id 값만 설정
            stmt.setString(2,date);
            stmt.executeUpdate();  // 쿼리 실행
            return 1;  // 삭제 성공

        } catch(Exception e) {
            e.printStackTrace();
        }
        return -1;  // 삭제 실패
    }

    // 전체 회원 리스트 조회 메서드
    public List<ManagementExer> managementListExerDelete() {
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
