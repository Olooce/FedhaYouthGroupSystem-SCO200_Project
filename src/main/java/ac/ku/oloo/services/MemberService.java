package ac.ku.oloo.services;

import ac.ku.oloo.models.Member;
import ac.ku.oloo.utils.databaseUtil.QueryExecutor;

import java.sql.SQLException;
import java.util.List;

/**
 * FedhaYouthGroupSystem-SCO200_Project (ac.ku.oloo.services)
 * Created by: oloo
 * On: 26/09/2024. 15:29
 * Description:
 **/
public class MemberService {
    public void createMember(Member member) throws SQLException {
        String sql = "INSERT INTO member_accounts.members (honorific, surname, first_name, other_name, date_of_birth, id_number, id_type, tax_id, email, phone_number, address, status, date_created, date_modified) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        Integer generatedMemberId = QueryExecutor.executeInsert(sql, rs -> {if (rs.next()) {
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

    public List<Member> getMembers(Integer page, Integer size) throws SQLException {

        int offset = (page - 1) * size;

        String sql = "SELECT * FROM member_accounts.members LIMIT ? OFFSET ?";

        return QueryExecutor.executeQuery(sql, rs -> {
            Member member = new Member();

            member.setMemberId(rs.getInt("member_id"));
            member.setHonorific(rs.getString("honorific"));
            member.setSurname(rs.getString("surname"));
            member.setFirstName(rs.getString("first_name"));
            member.setOtherName(rs.getString("other_name"));
            member.setDateOfBirth(rs.getDate("date_of_birth"));
            member.setIdNumber(rs.getString("id_number"));
            member.setIdType(rs.getString("id_type"));
            member.setTaxId(rs.getString("tax_id"));
            member.setEmail(rs.getString("email"));
            member.setPhoneNumber(rs.getString("phone_number"));
            member.setAddress(rs.getString("address"));
            member.setStatus(rs.getString("status"));
            member.setDateCreated(rs.getTimestamp("date_created").toLocalDateTime());
            member.setDateModified(rs.getTimestamp("date_modified").toLocalDateTime());

            return member;
        }, size, offset);
    }

    public int getTotalMembers() {
    return 0;
    }
}
