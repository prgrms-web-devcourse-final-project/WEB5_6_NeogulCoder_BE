package grep.neogulcoder.domain.study;

import java.util.List;

public class Studies {

    private final List<Study> studies;

    private Studies(List<Study> studies) {
        this.studies = studies;
    }

    public static Studies of(List<Study> studies){
        return new Studies(studies);
    }

    public List<Long> extractId(){
        return this.studies.stream()
                .map(Study::getId)
                .toList();
    }
}
