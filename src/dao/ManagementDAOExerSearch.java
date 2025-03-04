package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.ManagementExer;

public class ManagementDAOExerSearch {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final  String Search = "select * from record where id = ? order by date DESC;";


    private ManagementDAOExerSearch() {}
    private static ManagementDAOExerSearch instance = new ManagementDAOExerSearch();

    public static ManagementDAOExerSearch getInstance() {
        return instance;
    }



    public List<ManagementExer> searchMember(String id) {
        conn = DataBase.getConnection();
        List<ManagementExer> searchResults = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(Search);
            stmt.setString(1, id);  // ID 매개변수 설정
            rs = stmt.executeQuery();  // 쿼리 실행

            while (rs.next()) {
                ManagementExer mdto = new ManagementExer();
                mdto.setId(rs.getString("id"));
                mdto.setName(rs.getString("name"));
                mdto.setDate(rs.getString("date"));
                mdto.setPart(rs.getString("part"));
                mdto.setStarttime(rs.getString("starttime"));
                mdto.setEndtime(rs.getString("endtime"));
                mdto.setEndtime(rs.getString("Tname"));
                searchResults.add(mdto);  // 검색 결과 리스트에 추가
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;  // 검색된 결과 리스트 반환
    }

    // 전체 회원 리스트 조회 메서드
    public List<ManagementExer> managementListExerSearch() {
        conn = DataBase.getConnection();
        List<ManagementExer> list = new ArrayList<ManagementExer>();

        try {
            stmt = conn.prepareStatement(Search);
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