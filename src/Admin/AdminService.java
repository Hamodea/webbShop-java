// AdminService.java
package Admin;

import java.sql.SQLException;
import java.util.ArrayList;


public class AdminService {
    private final AdminRepository adminRepository = new AdminRepository();

    public ArrayList<Admin> getAllAdmins() throws SQLException {
        return adminRepository.getAll();
    }

    public Admin getAdmin(String username, String password) throws SQLException {
        return adminRepository.findAdmin(username, password);
    }

}
