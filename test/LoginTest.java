import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JComboBox;

public class LoginTest {
    
    @Test
    public void testDatabaseConnection() {
        Login login = new Login();
        assertNotNull("Database connection should be established", login.getConnection());
    }
    
    @Test
    public void testValidDoctorLogin() {
        Login login = new Login();
        
        // Test with actual doctor credentials
        login.getUsernameField().setText("yasmine");
        login.getPasswordField().setText("1234567890");
        login.getUserTypeCombo().setSelectedItem("Doctor");
        
        login.executeLogin("yasmine", "1234567890", "Doctor");
        
        try {
            assertTrue("Should find doctor user in database", login.getResultSet().next());
            assertEquals("Should match user ID", 1, login.getResultSet().getInt("id"));
        } catch (SQLException ex) {
            fail("SQL Exception: " + ex.getMessage());
        }
    }
    
    @Test
    public void testValidReceptionistLogin() {
        Login login = new Login();
        
        // Test with actual receptionist credentials
        login.getUsernameField().setText("mohamedamine");
        login.getPasswordField().setText("22042002");
        login.getUserTypeCombo().setSelectedItem("Receptionist");
        
        login.executeLogin("mohamedamine", "22042002", "Receptionist");
        
        try {
            assertTrue("Should find receptionist user in database", login.getResultSet().next());
            assertEquals("Should match user ID", 5, login.getResultSet().getInt("id"));
        } catch (SQLException ex) {
            fail("SQL Exception: " + ex.getMessage());
        }
    }
    
    @Test
    public void testInvalidLogin() {
        Login login = new Login();
        
        login.getUsernameField().setText("invaliduser");
        login.getPasswordField().setText("wrongpassword");
        login.getUserTypeCombo().setSelectedItem("Doctor");
        
        login.executeLogin("invaliduser", "wrongpassword", "Doctor");
        
        try {
            assertFalse("Should not find invalid user in database", login.getResultSet().next());
        } catch (SQLException ex) {
            fail("SQL Exception: " + ex.getMessage());
        }
    }
    
    @Test
    public void testUserTypeComboBox() {
        Login login = new Login();
        JComboBox<String> combo = login.getUserTypeCombo();
        assertEquals("Should have 2 items", 2, combo.getItemCount());
        assertEquals("First item should be Doctor", "Doctor", combo.getItemAt(0));
        assertEquals("Second item should be Receptionist", "Receptionist", combo.getItemAt(1));
    }
}