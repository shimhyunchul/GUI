package dao;

import dto.ManagementExer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagementDAOTrainers {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final String INSERT = "insert into trainers(tname) values(?)";
    private final String DELETE = "DELETE FROM trainers WHERE tname = ?";  // id를 사용해서 삭제
    private final String CHECK_DUPLICATE = "SELECT COUNT(*) FROM trainers WHERE tname = ?";  // 중복 체크 쿼리

    public ManagementDAOTrainers() {}
    private static ManagementDAOTrainers instance = new ManagementDAOTrainers();

    public static ManagementDAOTrainers getInstance() {
        return instance;
    }

    // 1. 중복된 tname이 있는지 확인하는 메서드
    public int checkDuplicate(String Tname) {
        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(CHECK_DUPLICATE);
            stmt.setString(1, Tname);
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return -1;  // 중복된 tname이 존재하면 -1 반환
            }
            return 1;  // 중복된 tname이 없으면 1 반환

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }

    // 2. tname이 중복되지 않으면 trainers 테이블에 추가하는 메서드
    public int insertMember(String Tname) {
        int check = checkDuplicate(Tname);  // 중복 체크
        if (check == -1) {
            return -1;  // 이미 존재하는 tname일 경우 -1 반환
        }

        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(INSERT);
            stmt.setString(1, Tname);
            stmt.executeUpdate();
            return 1;  // 정상적으로 추가됨
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }

    // 3. tname을 DB에서 삭제하는 메서드
    public int deleteMember(String Tname) {
        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement("SELECT * FROM trainers WHERE tname = ?");
            stmt.setString(1, Tname);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return -1;  // tname이 존재하지 않으면 -1 반환
            }

            // tname이 존재하면 삭제
            stmt = conn.prepareStatement(DELETE);
            stmt.setString(1, Tname);
            stmt.executeUpdate();
            return 1;  // 정상적으로 삭제됨

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }

    // DB 리소스 닫는 메서드
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 트레이너 삭제
    public int DelcheckDuplicate(String Tname) {
        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(CHECK_DUPLICATE);
            stmt.setString(1, Tname);
            rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return 1;  //  tname이 존재하면 +1 반환
            }
            return -1;  // 선택된 tname이 없으면 -1 반환

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return -1;
    }
}
