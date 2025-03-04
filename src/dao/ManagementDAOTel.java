package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import dto.ManagementDTO;



// insert into 하는 부분
public class ManagementDAOTel {

    private Connection conn = null;
    private PreparedStatement stmt = null;
    private ResultSet rs = null;

    private final String LIST = "select * from information order by id;";
    private final String FIND = "select * from information where id = ? and name = ?;";


    private ManagementDAOTel() {}
    private static ManagementDAOTel instance = new ManagementDAOTel();

    public static ManagementDAOTel getInstance() {
        return instance;
    }


    public List<ManagementDTO> managementListTel() {
        conn = DataBase.getConnection();
        List<ManagementDTO> list = new ArrayList<ManagementDTO>();

        try {
            stmt = conn.prepareStatement(LIST);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ManagementDTO mdt = new ManagementDTO();
                mdt.setId(rs.getString("id"));
                mdt.setPasswd(rs.getString("passwd"));
                mdt.setName(rs.getString("name"));
                mdt.setTel(rs.getString("tel"));
                mdt.setAddress(rs.getString("address"));
                mdt.setGender(rs.getString("gender"));
                list.add(mdt);

            }

            return list;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int idName(String id, String name) {

        conn = DataBase.getConnection();

        try {
            stmt = conn.prepareStatement(FIND);

            stmt.setString(1, id);
            stmt.setString(2, name);

            rs = stmt.executeQuery();

            if(rs.next()) {
                return 1;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}