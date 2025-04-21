// AdminService.java
package Admin;

import java.sql.SQLException;

public class AdminService {
    private final AdminRepository adminRepository = new AdminRepository();

    public Admin getAdminByEmailAndPassword(String email, String password) throws SQLException {
        return adminRepository.findByEmailAndPassword(email, password);
    }
}
