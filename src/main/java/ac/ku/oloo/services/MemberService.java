package ac.ku.oloo.services;

import ac.ku.oloo.models.Member;
import java.sql.SQLException;
import static ac.ku.oloo.utils.databaseUtil.QueryExecutor.*;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 26/09/2024. 15:29
 * Description:
 **/
public class MemberService {
    public void createMember(Member member) throws SQLException {
        String sql = "INSERT INTO members (honorific, surname, first_name, other_name, date_of_birth, id_number, id_type, tax_id, email, phone_number, address, status, date_created, date_modified) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
        
        Integer generatedMemberId = executeInsert(sql, rs -> {if (rs.next()) {
                    return rs.getInt(1);
                }
                    return null;},
                member.getHonorific(),
                member.getSurname(),
                member.getFirstName(),
                member.getOtherName(),
                member.getDateOfBirth(),
                member.getIdNumber(),
                member.getIdType(),
                member.getTaxId(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getStatus());

        member.setMemberId(generatedMemberId);
    }

}
