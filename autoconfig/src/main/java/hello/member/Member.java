package hello.member;

import lombok.Data;

@Data
public class Member {

    private String memberId;
    private String name;

    public Member() {
    }

    public Member(String id, String name) {
        this.memberId = id;
        this.name = name;
    }
}
