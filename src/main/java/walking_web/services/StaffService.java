package walking_web.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import walking_web.models.Staff;
import walking_web.repositories.StaffRepository;

@Service
public class StaffService {

    @Autowired
    StaffRepository staffRepo;

    Staff staff;
    boolean isLoggedIn;

    Logger logger = LoggerFactory.getLogger(ShoeService.class);

    public boolean login(String username) {
        List<Staff> staffLoggedInList = staffRepo.findByUsername(username);
        if (staffLoggedInList.size() > 0) {
            staff = staffLoggedInList.get(0);
            isLoggedIn = true;
            return true;
        } else {
            return false;
        }

    }

    public List<Staff> listAllStaff() {
        return staffRepo.findAll();
    }

}
