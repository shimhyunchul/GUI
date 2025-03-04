package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.ManagementDTO;

import javax.swing.*;

public class ManagementDAO {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final String LOGIN = "select * from information where id = ? and passwd = ?";
    private final String INSERT = "insert into information(id,passwd,name,tel,address,gender) "
            + "values(?,?,?,?,?,?)";

    private ManagementDAO() {
    }

    private static ManagementDAO instance = new ManagementDAO();

    public static ManagementDAO getInstance() {
        return instance;
    }

    // ID 와 psswd를 받아들이는 창이다.
    public int idPassword(String id, String passwd) {

        conn = DataBase.getConnection();


        try {
            stmt = conn.prepareStatement(LOGIN);

            stmt.setString(1, id);
            stmt.setString(2, passwd);

            rs = stmt.executeQuery();

            if (rs.next()) {
                return 1;
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }



    //ListView에서  아이디와 비밀번호 등  가입정보를 받고 이상 없이 추가가 되면 1리턴 안되면 -1을 리턴
    public int insertMember(ManagementDTO mdto) {


        conn = DataBase.getConnection();

        // 입력한 ID가 빈 공백일 경우
        // trim()은 빈공간은 나타냄, isEmpty()는 빈 공간이 발생하게 되면 true 값을 리턴함
        if (mdto.getId().trim().isEmpty()) {
            // ID가 빈 공백일 경우 JOptionPane.showMessageDialog 오류 메세지를 작동 시켜 다시 화면을 되돌림
            JOptionPane.showMessageDialog(null, "ID를 입력하지 않으셨습니다. \n다시 입력해주세요.", "ID 입력 오류", JOptionPane.ERROR_MESSAGE);
            return -1;  // 빈 공백이면 회원가입이 되지 않도록 하고 여기서 끝냄
        }


        try { // 트라이 캐치로 테이블에서 아이디를 입력받아 검사함
            // 아이디 중복 체크
            PreparedStatement pstmtCheck = conn.prepareStatement("SELECT COUNT(*) FROM information WHERE id = ?");
            pstmtCheck.setString(1, mdto.getId());
            ResultSet rs = pstmtCheck.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            // 중복된 아이디가 있을 경우
            if (count > 0) {
                // 아이디가 잘못되면 0값을 반환하도록 설정
                return 0;
            }


            String information = mdto.getTel();  // 전화번호를 입력받음
            try {
                // 전화번호가 숫자만 포함하는지 확인
                Integer.parseInt(information);  // 숫자로 변환 시도
            } catch (NumberFormatException ex) {
                // 전화번호가 숫자가 아니면 예외 발생
                return 2;  // 전화번호가 올바르지 않으면 진행하지 않음
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            stmt = conn.prepareStatement(INSERT);

            stmt.setString(1, mdto.getId());
            stmt.setString(2, mdto.getPasswd());
            stmt.setString(3, mdto.getName());
            stmt.setString(4, mdto.getTel());
            stmt.setString(5, mdto.getAddress());
            stmt.setString(6, mdto.getGender());
            stmt.executeUpdate();
            return 1;



        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
